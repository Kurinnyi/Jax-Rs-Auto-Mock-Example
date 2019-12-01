package example.mocks

import example.removeit.Dto
import example.removeit.ProxyOtherRestResourceInterface
import example.removeit.ProxyRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.kotlin.StubDefinitionContext
import ua.kurinnyi.jaxrs.auto.mock.kotlin.StubsDefinition

/**
 * This is example of usage of kotlin dsl
 * This class is auto discovered by its interface
 * Order of mock definitions in different files are not guarantied
 */
class MockProxyExample : StubsDefinition {

    override fun getPriority(): Int  = 1
    override fun getStubs(context: StubDefinitionContext) = context.createStubs {

        forClass(ProxyRestResourceInterface::class) {
            case {
                getDto(anyInRecord())
                addDto(any())
            } then {
                record()
                proxyTo("http://localhost:8080/other")
            }
        }

        forClass(ProxyOtherRestResourceInterface::class) {
            case {
                getDto(any())
            } with {
                header("Auth", isNull())
            } then {
                code(401)
            }

            case {
                getDto(eq("hi"))
            } then {
                header("HeaderIs", "Proxied")
                Dto("ProxiedHi", 1234)
            }

            case {
                getDto(any())
            } then {
                header("HeaderIs", "Proxied")
                Dto("Proxied", 1234)
            }

            case {
                addDto(any())
            } with {
                header("Auth", isNull())
            } then {
                header("HeaderIs", "Proxied401")
                code(500)
            }

            case {
                addDto(match { it.otherField < 10 })
            } then {
                header("HeaderIs", "Proxied")
                Dto("Proxied < 10", 1234)
            }

            case {
                addDto(match { it.otherField > 10 })
            } then {
                header("HeaderIs", "Proxied401")
                Dto("Proxied > 10", 1234)
            }
        }
    }
}
