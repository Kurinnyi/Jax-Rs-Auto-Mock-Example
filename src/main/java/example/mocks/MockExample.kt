package example.mocks

import example.removeit.HelloRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.StubServer
import ua.kurinnyi.jaxrs.auto.mock.kotlin.StubDefinitionContext
import ua.kurinnyi.jaxrs.auto.mock.kotlin.StubsDefinition

object Main {
    @JvmStatic
    fun main(args: Array<String>) = StubServer().setPort(8080).start()
}

class MockExample : StubsDefinition {
    //copy paste this line
    override fun getStubs(context: StubDefinitionContext) = context.createStubs {

        //Define class of jax-rs interface you want to mock
        forClass(HelloRestResourceInterface::class) {
            //do the mocking
            whenRequest {
                getHello(eq("Ivan"), anyLong())
            } thenResponse {
                body("Hello Ivan")
                //Response header
                header("Some header", "header value")
            }

            whenRequest {
                getHello(any(), anyLong())
            } thenResponse {
                body("Hello Guest")
                //Response header
                header("Some header", "header value")
            }
            whenRequest {
                echo(anyLong())
            } thenResponse {
                bodyProvider { (id) -> id as Long }
            }
        }
    }
}
