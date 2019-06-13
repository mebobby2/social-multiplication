brew services start rabbitmq

pushd service-registry
nohup ./mvnw spring-boot:run &
popd

pushd gateway
nohup ./mvnw spring-boot:run -Drun.profiles=test &
popd

pushd multiplication
nohup ./mvnw spring-boot:run -Drun.arguments="--server.port=8080" -Drun.profiles=test &
popd

pushd gamification
nohup ./mvnw spring-boot:run -Drun.profiles=test &
popd

pushd ui
nohup java -jar ~/Documents/development/jetty/start.jar &
popd
