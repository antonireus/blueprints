package org.fundaciobit.blueprint.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Path("test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestResource {

    public class Test1Bean {
        public String name;
        public Date date;
        public Test1Bean() {}
    }

    @XmlRootElement
    public class Test2Bean {
        public String name;
        public Date date;
        public Test2Bean() {}
    }

    @GET
    @Path("bean1")
    public Response bean1() {
        Test1Bean testBean = new Test1Bean();
        testBean.name = "test 1 bean";
        testBean.date = new Date();
        return Response.ok(testBean).build();
    }

    @GET
    @Path("bean2")
    public Response bean2() {
        Test2Bean testBean = new Test2Bean();
        testBean.name = "test 2 bean";
        testBean.date = new Date();
        return Response.ok(testBean).build();
    }
}
