package example.mocks.apiv2

import example.removeit.Dto
import example.removeit.apiv2.DtoRestResourceInterface
import ua.kurinnyi.jaxrs.auto.mock.apiv2.Mock


class BasicExamplesMore : Mock<DtoRestResourceInterface>({ mock ->
    //There is a matcher the allows you to match against JSON body of the request
    mock.addDto(bodySame("""
                            {
                               "field" : "json field",
                               "otherField" : 33
                            }
                        """)).respond {
        //You can specify response with its JSON representation.
        //However it is first deserialized to your Dto class,
        // and serialised back to JSON by Jersey mechanisms.
        //This is done to enforce contract of your resource interface.
        body("""
            {
               "field" : "json field",
               "otherField" : 34
            }
        """)
    }

    //You can use any predicate on incoming parameters with "match" matcher
    mock.addDto(match { it.otherField > 10 }).respond {
        //You can put response json into separate file. For example when it is too big
        body("/json/response.json")
    }

    //Create captor to access incoming dtos in response sections.
    val dto = capture<Dto>()
    //Use captor to wrap required parameter
    mock.addDto(dto(match { it.field == "template" })).respond {
        //You can use JSON as a template and pass some arguments to it
        //Here {{ f1 }} is substituted by otherField value of incoming dto.
        body("""
            {
               "field" : "template {{ f1 }}"
            }
        """, "f1" to dto().otherField)
    }

    mock.addDto(dto(match { it.field == "postProcess" })).respond {
        //bodyJson method return an actual object. So you can use it afterwards
        val jsonDto = body("""
                                {
                                   "field" : "postProcessResp",
                                   "otherField" : 12
                                }
                            """)
        //Change the field of the deserialized dto
        jsonDto?.apply { otherField += dto().otherField }
    }

    mock.addDto(match { it.otherField < 10 }).respond {
        code(500)
        //You can bypass serialization/deserialization mechanisms by calling bodyRaw
        //However you lose the type safety enforcement by doing so.
        bodyRaw("""Field is less then 10""")
    }

    mock.addDto(dto(notNull())).respond {
        Dto("Echo " + dto().field, dto().otherField)
    }

})
