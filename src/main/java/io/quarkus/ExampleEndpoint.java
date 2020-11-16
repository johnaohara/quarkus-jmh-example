package io.quarkus;

import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class ExampleEndpoint {

    public void startUp(@Observes StartupEvent startupEvent){
        //Signal application has stared
        MyBenchmark.QuarkusBenchmarkScope.startLatch.countDown();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld(){
        return "Hello";
    }
}
