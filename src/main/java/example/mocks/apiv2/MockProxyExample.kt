package example.mocks.apiv2

import example.removeit.Dto
import example.removeit.apiv2.ProxyOtherRestResourceInterface
import example.removeit.apiv2.ProxyRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.apiv2.Mock

class MockProxyExample : Mock<ProxyRestResourceInterface>({ mock ->

    //First parameter is not take into account in record, because of anyInRecord matcher.
    mock.getDto(anyInRecord())
    mock.addDto(any()).respond {
        //Request/Response pairs are recorder as mocks.
        //By default it is written in the console. So you need to change default [RecordSaver] to be able to use records as mocks.
        record()
        //Every matching call is forwarded to other url
        proxyTo("http://localhost:8080/other")
    }
})

//Here is the proxy destination with some set up.
class MockDestinationResource : Mock<ProxyOtherRestResourceInterface>({ mock ->

    mock.getDto(any())
            .header("Auth", isNull())
            .respond {
                code(401)
            }

    mock.getDto(eq("hi")).respond {
        header("HeaderIs", "Proxied")
        Dto("ProxiedHi", 1234)
    }

    mock.getDto(any()).respond {
        header("HeaderIs", "Proxied")
        Dto("Proxied", 1234)
    }

    mock.addDto(any())
            .header("Auth", isNull())
            .respond {
                header("HeaderIs", "Proxied401")
                code(500)
            }

    mock.addDto(match { it.otherField < 10 }).respond {
        header("HeaderIs", "Proxied")
        Dto("Proxied < 10", 1234)
    }

    mock.addDto(match { it.otherField > 10 }).respond {
        header("HeaderIs", "Proxied401")
        Dto("Proxied > 10", 1234)
    }

})
