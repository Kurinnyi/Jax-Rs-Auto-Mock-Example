package example.mocks

import example.removeit.Dto
import example.removeit.DtoRestResourceInterface
import example.removeit.HelloRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.kotlin.StubDefinitionContext
import ua.kurinnyi.jaxrs.auto.mock.kotlin.StubsDefinition
import java.util.*

/**
 * This is example of usage of kotlin dsl
 * This class is auto discovered by its interface
 * Order of mock definitions in different files are not guarantied
 */
class MockExample : StubsDefinition {
    //copy paste this line
    override fun getStubs(context: StubDefinitionContext) = context.createStubs {

        //Define class of jax-rs interface you want to mock
        forClass(HelloRestResourceInterface::class) {
            //Define mock
            whenRequest {
                //specify method to mock. And matching requirements for mock to be invoked
                getHello(any(), any())
            } with {
                //specify header
                header("Auth", isNull())
            } thenResponse {
                //response code. Should be never set if it is 200
                code(401)
            }

            whenRequest {
                //You can use same method few times in same and different 'whenRequest' sections
                //Mock with first matching 'whenRequest' section will be used
                getHello(notNull(), eq(12))
                getHello(notNull(), eq(14))
            } thenResponse {
                //response body
                body("Hello 12 or 14")
            }

            whenRequest {
                getHello(any(), any())
            } thenResponse {
                body("Hello any other")
                //Response header
                header("Some header", "header value")
            }
        }

        //Different classes can be mocked in one file
        forClass(DtoRestResourceInterface::class){
            whenRequest {
                getDto()
            } with {
                header("Auth", eq("123"))
            } thenResponse {
                body(Dto("some field", 42))
            }

            whenRequest {
                getDto()
            } with {
                header("Auth", eq("123"))
            } thenResponse {
                //You can specify response with its JSON representation.
                //However it will be first deserialized to your Dto class
                // and then serialised back to JSON by Jersey mechanisms.
                //This is done to enforce contract of your resource interface.
                //Method 'bodyJson' always uses Jackson for deserialization
                bodyJson("""
                    {
                       "field" : "json field",
                       "otherField" : 33
                    }
                """)
            }

            whenRequest {
                getDto()
            } thenResponse {
                //You can specify response with lambda expression that provides response
                //In this case each call to this mock returns different values
                val random = Random()
                bodyProvider { Dto("Random", random.nextInt()) }
            }
        }
    }
}
