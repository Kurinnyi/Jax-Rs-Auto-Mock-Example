package example.mocks

import example.removeit.Dto
import example.removeit.DtoRestResourceInterface
import example.removeit.ProxyOtherRestResourceInterface
import example.removeit.ProxyRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.kotlin.*

/**
 * This is example of usage of kotlin dsl
 * This class is auto discovered by its interface
 * Order of mock definitions in different files are not guarantied
 */
class MockProxyExample : StubsDefinition {
    override fun getStubs(context: StubDefinitionContext) = context.createStubs {

        forClass(ProxyRestResourceInterface::class) {
            whenRequest {
                getDto(any())
                addDto(any())
            } thenResponse {
                proxyTo("http://localhost:8080/other")
            }
        }

        forClass(ProxyOtherRestResourceInterface::class) {
            whenRequest {
                getDto(any())
            } with {
                header("Auth", isNull())
            } thenResponse {
                code(401)
            }

            whenRequest {
                getDto(any())
            } thenResponse {
                header("HeaderIs", "Proxied")
                body(Dto("Proxied", 1234))
            }

            whenRequest {
                addDto(any())
            } with {
                header("Auth", isNull())
            } thenResponse {
                code(401)
            }

            whenRequest {
                addDto(match { it.otherField < 10 })
            } thenResponse {
                header("HeaderIs", "Proxied")
                body(Dto("Proxied < 10", 1234))
            }

            whenRequest {
                addDto(match { it.otherField > 10 })
            } thenResponse {
                header("HeaderIs", "Proxied")
                body(Dto("Proxied > 10", 1234))
            }
        }
    }
}
