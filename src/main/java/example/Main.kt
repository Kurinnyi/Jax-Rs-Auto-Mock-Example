package example

import ua.kurinnyi.jaxrs.auto.mock.StubServer

/**
 * This class is used to start up the mock server.
 * It can be started from your IDE or will be used as entry point for a packed jar
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) = StubServer().setPort(8080).start()
}