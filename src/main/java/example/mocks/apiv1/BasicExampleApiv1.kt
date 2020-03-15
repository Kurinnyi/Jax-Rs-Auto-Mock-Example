package example.mocks.apiv1

import example.removeit.apiv1.Apiv1HelloRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.apiv1.StubDefinitionContext
import ua.kurinnyi.jaxrs.auto.mock.mocks.StubsDefinition


/**
 * This is example of usage of kotlin dsl
 * This class is auto discovered by interface
 */
@Deprecated("Please prefer apiv2")
class BasicExampleApiv1 : StubsDefinition {
    //copy paste this line
    override fun getStubs() = StubDefinitionContext().createStubs {

        //Define class of jax-rs interface you want to mock
        forClass(Apiv1HelloRestResourceInterface::class) {
            //Simple mock example. Every call with first parameter equal to "Ivan"
            // is responded with "Hello Ivan" text and HTTP header
            case {
                getHello(eq("Ivan"), anyLong())
            } then {
                header("Some header", "header value")
                "Hello Ivan"
            }

            //Any incoming parameter is returned back
            case {
                echo(anyLong())
            } then1 { id: Long ->
                id
            }

            case {
                getHello(eq("Error"), anyLong())
            } then {
                //Explicitly specify response code.
                code(514)
                //Explicitly specify response header.
                header("CustomHeader", "this is an error")
                bodyRaw("""{"message":"this is error"}""")
            }

            //Any request yet not matched falls under this case
            case {
                getHello(any(), anyLong())
            } then {
                header("Some header", "header value")
                "Hello Guest"
            }

        }
    }
}