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
            case {
                getHello(eq("Ivan"), anyLong())
            } then {
                //Response header
                header("Some header", "header value")
                "Hello Ivan"
            }

            case {
                echo(anyLong())
            } then1 { id:Long ->
                id
            }

            case {
                getHello(eq("Error"), anyLong())
            } then {
                bodyRaw("""{"message":"this is error"}""")
                code(514)
                header("Content-Type", "application/json")
            }

            case {
                getHello(any(), anyLong())
            } then {
                //Response header
                header("Some header", "header value")
                "Hello Guest"
            }
        }
    }
}
