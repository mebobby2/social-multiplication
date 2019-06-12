brew services start rabbitmq

pushd service-registry
nohup ./mvnw spring-boot:run &
popd

pushd gateway
nohup ./mvnw spring-boot:run &
popd

pushd multiplication
nohup ./mvnw spring-boot:run &
popd

pushd gamification
nohup ./mvnw spring-boot:run &
popd

pushd ui
nohup java -jar ~/Documents/development/jetty/start.jar &
popd
