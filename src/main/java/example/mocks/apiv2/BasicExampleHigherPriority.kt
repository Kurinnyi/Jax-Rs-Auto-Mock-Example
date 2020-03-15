package example.mocks.apiv2

import example.Main.serialisationUtils
import example.removeit.Dto
import example.removeit.apiv2.HelloRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.apiv2.Mock


class BasicExampleHigherPriority : Mock<HelloRestResourceInterface>({ mock ->

    //Since it has higher priority, mocks here are evaluated earlier then in [BasicExample]
    priority(1)

    mock.getHello(eq("TestSerializationUtils"), notNull()).respond {
        //Resolve template and deserialize into Dto object
        val dto: Dto = serialisationUtils.load("""
            {
               "field" : "template {{ field1 }}"
            }
        """, "field1" to "test serialization utils")
        //Serialize object back into string and return
        serialisationUtils.toString(dto)
    }

    mock.getHello(eq("TestSerializationUtilsList"), notNull()).respond {
        //Explicitly say to deserialize into list. Will fail otherwice
        val dto: List<Dto> = serialisationUtils.loadList("""
            [{
               "field" : "template {{ field1 }}"
            }]
        """, "field1" to "test serialization utils list")
        //Serialize object back into string and return
        serialisationUtils.toString(dto)
    }
})