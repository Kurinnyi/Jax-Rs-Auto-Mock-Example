package example.mocks.apiv1

import example.removeit.apiv1.Apiv1GropExampleResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.apiv1.GroupsCallbacksContext
import ua.kurinnyi.jaxrs.auto.mock.apiv1.StubDefinitionContext
import ua.kurinnyi.jaxrs.auto.mock.mocks.StubsDefinition
import java.util.concurrent.atomic.AtomicLong

@Deprecated("Please prefer apiv2")
class GroupsExampleApiv1 : StubsDefinition {

    private val counter = AtomicLong(0)

    override fun getStubs() = StubDefinitionContext().createStubs {

        forClass(Apiv1GropExampleResourceInterface::class) {

            //By wrapping mock into group you can switch them on and off by sending requests to "/groups"
            group("group1-v1", activeByDefault = false) {
                case {
                    getSomeInfo(eq("group"))
                } then {
                    """"This endpoint invoked ${counter.incrementAndGet()} times.""""
                }
            }

            group("group2-v1") {
                case {
                    getSomeInfo(eq("group"))
                } then {
                    """"This is decrementing group ${counter.decrementAndGet()}""""
                }
            }

        }

    }

    //You can specify to do some actions on switching group events
    override fun getGroupsCallbacks() = GroupsCallbacksContext().addGroupCallbacks {

        onGroupEnabled("group1-v1") {
            counter.set(0)
        }

        onGroupDisabled("group1-v1") {
            counter.set(0)
        }

        onGroupEnabled("group2-v1") {
            counter.getAndUpdate { it + 10 }
        }
    }
}