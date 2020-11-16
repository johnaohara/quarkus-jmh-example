cd ./target/
mkdir scratch
cp first-benchmark-1.0-runner.jar  ./scratch/
cd scratch/
jar xvf first-benchmark-1.0-runner.jar 
rm first-benchmark-1.0-runner.jar 
rm META-INF/MANIFEST.MF 
jar uf ../benchmarks.jar ./*
cd ..
