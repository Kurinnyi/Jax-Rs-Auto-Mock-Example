package example.mocks

import example.removeit.GropExampleResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.kotlin.*
import java.util.concurrent.atomic.AtomicLong

class MockExampleGroups : StubsDefinition {

    private val counter = AtomicLong(0)

    override fun getStubs(context: StubDefinitionContext) = context.createStubs {

        forClass(GropExampleResourceInterface::class){

            group("group1"){
                case {
                    getSomeInfo(eq("group"))
                } then {
                    """"This endpoint invoked ${counter.incrementAndGet()} times.""""
                }
            }

            group("group2"){
                case {
                    getSomeInfo(eq("group"))
                } then {
                    """"This is decrementing group ${counter.decrementAndGet()}""""
                }
            }

        }

    }

    override fun getGroupsCallbacks(context: GroupsCallbacksContext) = context.addGroupCallbacks {

        onGroupEnabled("group1"){
            counter.set(0)
        }

        onGroupDisabled("group1"){
            counter.set(0)
        }

        onGroupEnabled("group2"){
            counter.getAndUpdate { it + 10 }
        }
    }
}