package io.quarkus;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class TestApplication implements QuarkusApplication {

    @Override
    public int run(String... args) throws Exception {
        final String name = args.length > 0 ? String.join(" ", args) : "commando";
        Quarkus.waitForExit();
        return 0;
    }
}
