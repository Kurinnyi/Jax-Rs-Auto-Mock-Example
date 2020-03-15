package example.mocks.apiv1

import example.Main.serialisationUtils
import example.removeit.Dto
import example.removeit.apiv1.Apiv1HelloRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.apiv1.StubDefinitionContext
import ua.kurinnyi.jaxrs.auto.mock.mocks.StubsDefinition


@Deprecated("Please prefer apiv2")
class BasicExampleHigherPriorityApiv1 : StubsDefinition {

    //Since it has higher priority, mocks here are evaluated earlier then in [BasicExampleApiv1]
    override fun getPriority() = 1

    override fun getStubs() = StubDefinitionContext().createStubs {

        forClass(Apiv1HelloRestResourceInterface::class) {

            case {
                getHello(eq("TestSerializationUtils"), anyLong())
            } then {
                //Resolve template and deserialize into Dto object
                val dto: Dto = serialisationUtils.load("""
                    {
                       "field" : "template {{ field1 }}"
                    }
                """, "field1" to "test serialization utils")
                //Serialize object back into string and return
                serialisationUtils.toString(dto)
            }

            case {
                getHello(eq("TestSerializationUtilsList"), anyLong())
            } then {
                //Explicitly say to deserialize into list. Will fail otherwice
                val dto: List<Dto> = serialisationUtils.loadList("""
                    [{
                       "field" : "template {{ field1 }}"
                    }]
                """, "field1" to "test serialization utils list")
                //Serialize object back into string and return
                serialisationUtils.toString(dto)
            }
        }
    }
}