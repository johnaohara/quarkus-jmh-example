package io.quarkus;

import io.quarkus.runtime.Quarkus;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyBenchmark {

    @Benchmark
    @Threads(20)
    public void testMethod(Blackhole blackhole, QuarkusBenchmarkScope quarkusBenchmarkScope, QuarkusBenchmarkThreadScope quarkusBenchmarkThreadScope)  {

        try {
            blackhole.consume(quarkusBenchmarkThreadScope.sendHttpRequest());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @State(Scope.Thread)
    public static class QuarkusBenchmarkThreadScope {
        private HttpRequest request;
        private HttpClient client;

        public String sendHttpRequest() throws IOException, InterruptedException {
            return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        }

        @Setup(Level.Trial)
        public void setUp() {


            client = HttpClient.newHttpClient();

            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api"))
                    .build();
        }
    }

    @State(Scope.Benchmark)
    public static class QuarkusBenchmarkScope {

        public static volatile CountDownLatch startLatch = new CountDownLatch(1);

        private ExecutorService executor;

        @Setup(Level.Trial)
        public void setUp() throws InterruptedException {

            System.err.println("Starting Quarkus app");
            executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> Quarkus.run(TestApplication.class, "Test", "Another"));

            startLatch.await();

            //There is a delay between StartEvent fires and the HTTP layer is available to service requests
            //there must be a better way to synchronize bootstrapping
            Thread.currentThread().sleep(100);

        }

        @TearDown
        public void tearDown(){
            System.err.println("Tearing down Quarkus app");
            Quarkus.asyncExit();
            executor.shutdown();

        }
    }

}
