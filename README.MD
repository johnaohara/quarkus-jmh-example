POC: Quarkus JMH Example
========================

Example JMH benchmark for bootstrapping quarkus and executing a http request benchmark against a rest endpoint

> :warning: *This is probably not a great idea, as the server and load generator are running in the same JVM*  

> :information_source: The Maven shade plugin does not shade the runner jar produced by the Quarkus plugin.  There is a `shade_benchmark.sh` script to shade the `-runner.jar` but the Maven shade plugin should be configured correctly  

Build
-----

```shell script
$ mvn clean package; ./shade_benchmark.sh
```

Run Benchmark
-------------

```shell script
$ java -jar ./target/benchmarks.jar
```

or with config options

```shell script
$ java -jar ./target/benchmarks.jar -wi 5 -i 5 -t 30
```

Output
------

Quarkus should bootstrap at the start of every fork, e.g.

```shell script
# Run progress: 0.00% complete, ETA 00:08:20
# Fork: 1 of 5
# Warmup Iteration   1: Starting Quarkus app
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
2020-11-16 18:57:12,873 INFO  [io.quarkus] (pool-2-thread-1) first-benchmark 1.0 on JVM (powered by Quarkus 1.9.2.Final) started in 0.401s. Listening on: http://0.0.0.0:8080
2020-11-16 18:57:12,886 INFO  [io.quarkus] (pool-2-thread-1) Profile prod activated. 
2020-11-16 18:57:12,886 INFO  [io.quarkus] (pool-2-thread-1) Installed features: [cdi, resteasy]
99497.412 ops/s
# Warmup Iteration   2: 117251.125 ops/s

``` 