package example.mocks

import example.removeit.Dto
import example.removeit.DtoRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.kotlin.*

/**
 * This is example of usage of kotlin dsl
 * This class is auto discovered by its interface
 * Order of mock definitions in different files are not guarantied
 */
class MockExampleOtherFile : StubsDefinition {
    override fun getStubs(context: StubDefinitionContext) = context.createStubs {

        forClass(DtoRestResourceInterface::class) {
            case {
                //There is a matcher the allows you to match against JSON body of the request
                addDto(bodySameJson("""
                    {
                       "field" : "json field",
                       "otherField" : 33
                    }
                """))
            } then {
                bodyJson("""
                    {
                       "field" : "json field",
                       "otherField" : 34
                    }
                """)
            }

            case {
                addDto(bodySameJson("""
                    {
                       "field" : "json field",
                       "otherField" : 35
                    }
                """))
            } then {
                //You can specify response with its JSON representation.
                //However it will be first deserialized to your Dto class
                // and then serialised back to JSON by Jersey mechanisms.
                //This is done to enforce contract of your resource interface.
                //Deserialization mechanism is specified by first argument
                bodyJson(BY_JERSEY, """
                    {
                       "field" : "json field",
                       "otherField" : 36
                    }
                """)
            }

            case {
                addDto(match { it.field == "template" })
            } then1 { dto:Dto ->
                bodyJson(BY_JERSEY, """
                    {
                       "field" : "template {{ f1 }}"
                    }
                """, "f1" to dto.otherField)
            }

            case {
                addDto(match { it.field == "postProcess" })
            } then1 { requestDto:Dto ->
                val jsonDto = bodyJson(BY_JERSEY, """
                    {
                       "field" : "postProcessResp",
                       "otherField" : 12
                    }
                """)
                jsonDto?.apply { otherField += requestDto.otherField }
            }

            case {
                //You can specify lambda expression to match
                addDto(match { it.otherField > 10 })
            } then {
                //You can put response json into separate file. For example when it is too big
                bodyJson("/json/response.json")
            }

            case {
                addDto(match { it.otherField < 10 })
            } then {
                bodyJson(BY_JERSEY, """
                    {
                       "field" : " < 10"
                    }
                """)
            }

            case {
                addDto(notNull())
            } then1 { inputDto:Dto ->
                Dto("Echo " + inputDto.field, inputDto.otherField)
            }

        }
    }
}
