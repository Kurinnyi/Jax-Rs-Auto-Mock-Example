package example.mocks

import example.removeit.DtoRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.kotlin.StubDefinitionContext
import ua.kurinnyi.jaxrs.auto.mock.kotlin.StubsDefinition

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
                bodyJson("""
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
                // In case of usage 'bodyJsonJersey' deserialization is also done by Jersey mechanisms
                bodyJsonJersey("""
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
                bodyJsonJersey("""
                    {
                       "field" : " > 10"
                    }
                """)
            }

            whenRequest {
                addDto(match { it.otherField < 10 })
            } thenResponse {
                bodyJsonJersey("""
                    {
                       "field" : " < 10"
                    }
                """)
            }

            whenRequest {
                addDto(any())
            } thenResponse {
                bodyJsonJersey("""
                    {
                       "field" : "any other"
                    }
                """)
            }

        }
    }
}
