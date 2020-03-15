package example

import ua.kurinnyi.jaxrs.auto.mock.extensions.defaul.ByPackageContextPathConfiguration
import ua.kurinnyi.jaxrs.auto.mock.extensions.defaul.ByPackageContextPathConfiguration.Companion.on
import ua.kurinnyi.jaxrs.auto.mock.extensions.defaul.ResourceFolderFilesResponseBodyProvider
import ua.kurinnyi.jaxrs.auto.mock.jersey.JerseyDependenciesRegistry
import ua.kurinnyi.jaxrs.auto.mock.jersey.StubServer
import ua.kurinnyi.jaxrs.auto.mock.mocks.SerialisationUtils

/**
 * This class is used to start up the mock server.
 * It can be started from your IDE or will be used as entry point for a packed jar
 */
object Main {
    lateinit var serialisationUtils: SerialisationUtils

    @JvmStatic
    fun main(args: Array<String>) {
        //Start building StubServer with required configuration
        StubServer()
                //Set TCP port
                .onPort(8080)
                //Store serialization utils to use later
                .onDependenciesRegistryReady {
                    serialisationUtils = it.serialisationUtils()
                }
                //Specify the way how to deserialize objects extracted by "body" method or serialisationUtils
                .withDefaultResponseBodyProvider(
                        //Allow reading JSON files from resource folder
                        ResourceFolderFilesResponseBodyProvider(
                                //Use internal jersey mechanisms to deserialize objects
                                JerseyDependenciesRegistry.jerseyInternalBodyProvider))
                //Configure context paths
                .withContextPathConfiguration(ByPackageContextPathConfiguration(
                        //All resources from apiv1 package are available by apiv1 path
                        "example.removeit.apiv1" on "/apiv1"
                ))
                //Launch the server and block on it
                .start()
    }
}