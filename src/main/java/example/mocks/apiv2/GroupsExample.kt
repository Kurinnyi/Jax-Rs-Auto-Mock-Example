package example.mocks.apiv2

import example.removeit.apiv2.GropExampleResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.apiv2.Mock
import java.util.concurrent.atomic.AtomicLong

class GroupsExample : Mock<GropExampleResourceInterface>({ mock ->

    val counter = AtomicLong(0)

    //By wrapping mock into group you can switch them on and off by sending requests to "/groups"
    group("group1-v1", activeByDefault = false) {
        mock.getSomeInfo(eq("group")).respond {
            """"This endpoint invoked ${counter.incrementAndGet()} times.""""
        }
    }

    group("group2-v1") {
        mock.getSomeInfo(eq("group")).respond {
            """"This is decrementing group ${counter.decrementAndGet()}""""
        }
    }

    //You can specify to do some actions on switching group events
    onGroupEnabled("group1-v1") {
        counter.set(0)
    }

    onGroupDisabled("group1-v1") {
        counter.set(0)
    }

    onGroupEnabled("group2-v1") {
        counter.getAndUpdate { it + 10 }
    }
})