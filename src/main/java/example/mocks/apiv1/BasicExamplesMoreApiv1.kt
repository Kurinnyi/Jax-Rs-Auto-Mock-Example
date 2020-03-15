package example.mocks.apiv1

import example.removeit.Dto
import example.removeit.apiv1.Apiv1DtoRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.apiv1.StubDefinitionContext
import ua.kurinnyi.jaxrs.auto.mock.mocks.StubsDefinition


@Deprecated("Please prefer apiv2")
class BasicExamplesMoreApiv1 : StubsDefinition {

    override fun getStubs() = StubDefinitionContext().createStubs {

        forClass(Apiv1DtoRestResourceInterface::class) {
            case {
                //There is a matcher the allows you to match against JSON body of the request
                addDto(bodySameJson("""
                    {
                       "field" : "json field",
                       "otherField" : 33
                    }
                """))
            } then {
                //You can specify response with its JSON representation.
                //However it is first deserialized to your Dto class,
                // and serialised back to JSON by Jersey mechanisms.
                //This is done to enforce contract of your resource interface.
                bodyJson("""
                    {
                       "field" : "json field",
                       "otherField" : 34
                    }
                """)
            }

            case {
                //You can use any predicate on incoming parameters with "match" matcher
                addDto(match { it.field == "template" })
            } then1 { dto: Dto ->
                //You can use JSON as a template and pass some arguments to it
                //Here {{ f1 }} is substituted by otherField value of incoming dto.
                bodyJson("""
                    {
                       "field" : "template {{ f1 }}"
                    }
                """, "f1" to dto.otherField)
            }

            case {
                addDto(match { it.field == "postProcess" })
            } then1 { requestDto: Dto ->
                //bodyJson method return an actual object. So you can use it afterwards
                val jsonDto = bodyJson("""
                    {
                       "field" : "postProcessResp",
                       "otherField" : 12
                    }
                """)
                //Change the field of the deserialized dto
                jsonDto?.apply { otherField += requestDto.otherField }
            }

            case {
                addDto(match { it.otherField > 10 })
            } then {
                //You can put response json into separate file. For example when it is too big
                bodyJson("/json/response.json")
            }

            case {
                addDto(match { it.otherField < 10 })
            } then {
                code(500)
                //You can bypass serialization/deserialization mechanisms by calling bodyRaw
                //However you lose the type safety enforcement by doing so.
                bodyRaw("""Field is less then 10""")
            }

            case {
                addDto(notNull())
            } then1 { inputDto: Dto ->
                Dto("Echo " + inputDto.field, inputDto.otherField)
            }

        }
    }
}
