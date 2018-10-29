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
            whenRequest {
                //There is a matcher the allows you to match against JSON body of the request
                addDto(bodyJson("""
                    {
                       "field" : "json field",
                       "otherField" : 33
                    }
                """))
            } thenResponse {
                bodyJson(BY_JACKSON, """
                    {
                       "field" : "json field",
                       "otherField" : 34
                    }
                """)
            }

            whenRequest {
                addDto(bodyJson("""
                    {
                       "field" : "json field",
                       "otherField" : 35
                    }
                """))
            } thenResponse {
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

            whenRequest {
                //You can specify lambda expression to match
                addDto(match { it.otherField > 10 })
            } thenResponse {
                //You can put response json into separate file. For example when it is too big
                bodyJson(FROM_FILE(BY_JERSEY), "/json/response.json")
            }

            whenRequest {
                addDto(match { it.otherField < 10 })
            } thenResponse {
                bodyJson(BY_JERSEY, """
                    {
                       "field" : " < 10"
                    }
                """)
            }

            whenRequest {
                addDto(notNull())
            } thenResponse {
                bodyProvider { args ->
                    val inputDto = args[0] as Dto
                    Dto("Echo " + inputDto.field, inputDto.otherField)
                }
            }

        }
    }
}
