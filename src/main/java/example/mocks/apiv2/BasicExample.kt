package example.mocks.apiv1

import example.removeit.apiv2.HelloRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.apiv2.Mock


/**
 * This is example of usage of kotlin dsl
 * This class is auto discovered by interface
 */
//Define class of jax-rs interface you want to mock
class BasicExample : Mock<HelloRestResourceInterface>({ mock ->


    //Simple mock example. Every call with first parameter equal to "Ivan"
    // is responded with "Hello Ivan" text and HTTP header
    mock.getHello(eq("Ivan"), notNull()).respond {
        header("Some header", "header value")
        "Hello Ivan"
    }

    //Any incoming parameter is returned back
    val id = capture<Long>()
    mock.echo(id(notNull())).respond {
        id()
    }

    mock.getHello(eq("Error"), notNull()).respond {
        //Explicitly specify response code.
        code(514)
        //Explicitly specify response header.
        header("CustomHeader", "this is an error")
        """{"message":"this is error"}"""
    }

    //Any request yet not matched falls under this case
    mock.getHello(any(), notNull()).respond {
        header("Some header", "header value")
        "Hello Guest"
    }

})