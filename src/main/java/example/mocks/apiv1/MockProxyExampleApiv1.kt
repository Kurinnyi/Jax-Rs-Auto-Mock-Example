package example.mocks.apiv1

import example.removeit.apiv1.Apiv1ProxyRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.apiv1.StubDefinitionContext
import ua.kurinnyi.jaxrs.auto.mock.mocks.StubsDefinition

@Deprecated("Please prefer apiv2")
class MockProxyExampleApiv1 : StubsDefinition {

    override fun getStubs() = StubDefinitionContext().createStubs {

        forClass(Apiv1ProxyRestResourceInterface::class) {
            case {
                //First parameter is not take into account in record, because of anyInRecord matcher.
                getDto(anyInRecord())
                addDto(any())
            } then {
                //Request/Response pairs are recorder as mocks.
                //By default it is written in the console. So you need to change default [RecordSaver] to be able to use records as mocks.
                record()
                //Every matching call is forwarded to other url
                proxyTo("http://localhost:8080/other")
            }
        }
    }
}
