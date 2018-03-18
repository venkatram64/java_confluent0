first run mvn clean
and run mvn package


docker installation on windows 10

your IP will be 192.168.99.100
The docker machine that ships with Docker Toolbox comes with only 2GB of RAM, which is not enough for Kafka. You need to increase it to 4GB (at least) by running the following commands:
docker-machine rm default docker-machine create -d virtualbox --virtualbox-memory=4096 --virtualbox-cpu-count=2 default

Run the command
docker-machine env default --shell cmd

or

docker-machine env default --shell powershell

In a new command prompt, paste the output from the command above into the terminal
!!!! MAKE SURE TO COPY YOUR OUTPUT, NOT THE CODE BELOW !!!! set DOCKER_TLS_VERIFY=1 set DOCKER_HOST=tcp://192.168.99.100:2376 set DOCKER_CERT_PATH=... set DOCKER_MACHINE_NAME=default

Docker for Mac >= 1.12, Linux, Docker for Windows 10
docker run --rm -it \
-p 2181:2181 -p 3030:3030 -p 8081:8081 \
-p 8082:8082 -p 8083:8083 -p 9092:9092 \
-e ADV_HOST=127.0.0.1 \
landoop/fast-data-dev

Docker toolbox
docker run --rm -it \
-p 2181:2181 -p 3030:3030 -p 8081:8081 \
-p 8082:8082 -p 8083:8083 -p 9092:9092 \
-e ADV_HOST=192.168.99.100 \
landoop/fast-data-dev

Kafka command lines tools(run following command to create the topics)
docker run --rm -it --net=host landoop/fast-data-dev bash

to see the UI http://192.168.99.100:3030/

Create topic

kafka-topics --zookeeper 192.168.99.100:2181 --create --topic first_topic --partitions 3 --replication-factor 1

kafka-topics --zookeeper 192.168.99.100:2181 --create --topic test_topic --partitions 3 --replication -factor 1 --config cleanup.policy=compact

To list topic

kafka-topics --zookeeper 192.168.99.100:2181 --list

To delete topic

kafka-topics --zookeeper 192.168.99.100:2181 --delete --topic first_topic

To describe topic

kafka-topics --zookeeper 192.168.99.100:2181 --describe --topic first_topic To send messages

kafka-console-producer --broker-list 192.168.99.100:9092 --topic first_topic

To receive messages

kafka-console-consumer --bootstrap-server 192.168.99.100:9092 --topic first_topic

To receive messages

kafka-console-consumer --bootstrap-server 192.168.99.100:9092 --topic first_topic --from-begining

To receive messages from commiit

kafka-console-consumer --bootstrap-server 192.168.99.100:9092 --topic first_topic --consumer-property group.id=mygroup --from-begining

docker ps

docker exec -it 515a7b91e6a4 tail -f /var/log/broker.log

log compaction: first run the : docker run --rm -it --net=host landoop/fast-data-dev bash case 1: create topic kafka-topics --zookeeper 192.168.99.100:2181 --create
--topic employee-salary-compact
--partitions 1 --replication-factor 1
--config cleanup.policy=compact
--config min.cleanable.dirty.ratio=0.005
--config segment.ms=100000

case 2 : run the following command kafka-console-consumer --bootstrap-server 192.168.99.100:9092
--topic employee-salary-compact
--from-beginning
--property print.key=true
--property key.separator=, case 3 : run the following command kafka-console-producer --broker-list 192.168.99.100:9092
--topic employee-salary-compact
--property parse.key=true
--property key.separator=,

paste below messages in producer console:

123,{"Venkatram":"1000000"} 124,{"Veerareddy":"12121566"} 125,{"Srijan":"90987666"}

1231,{"Lisa":"123123123"} 1242,{"Feema":"12121566"} 1253,{"Srijan":"90987666009"}

123,{"Venkatram":"100000234"} 124,{"Veerareddy":"1212001566"}



avro tools how to use it

wget http://central.maven.org/maven2/org/apache/avro/avro-tools/1.8.2/avro-tools-1.8.2.jar

java -jar avro-tools-1.8.2.jar

java -jar ~/avro-tools-1.7.4.jar fromjson

java -jar ~/avro-tools-1.7.4.jar tojson --pretty customer-generic.avro
java -jar ~/avro-tools-1.7.4.jar tojson --pretty customer-specific.avro

#getting the schema
java -jar ~/avro-tools-1.7.4.jar getschema  customer-specific.avro

Without compression
java -jar ~/avro-tools-1.7.4.jar fromjson --schema-file twitter.avsc twitter.json > twitter.avro

With Snappy compression
java -jar ~/avro-tools-1.7.4.jar fromjson --codec snappy --schema-file twitter.avsc twitter.json > twitter.snappy.avro

The same command will work on both uncompressed and compressed data.

java -jar ~/avro-tools-1.7.4.jar tojson twitter.avro > twitter.json
java -jar ~/avro-tools-1.7.4.jar tojson twitter.snappy.avro > twitter.json

The same command will work on both uncompressed and compressed data.

java -jar ~/avro-tools-1.7.4.jar getschema twitter.avro > twitter.avsc
java -jar ~/avro-tools-1.7.4.jar getschema twitter.snappy.avro > twitter.avsc

avro evolution schema
1. backward compatible
    write with new schema(v1), read with new schema(v2)

2. forward compatible
    write with old schema(v2), read with old schema(v1)

3. backward and forward comptible

4. none

**********************************
docker-compose.yml
version: '2'

services:
  kafka-cluster:
    image: landoop/fast-data-dev:cp3.3.0
    environment:
      ADV_HOST: 192.168.99.100
      RUNTESTS: 0
      FORWARDLOGS: 0
      SAMPLEDATA: 0
    ports:
      - 2181:2181
      - 3030:3030
      - 8081-8083:8081-8083
      - 9581-9585:9581-9585
      - 9092:9092

run following commands
docker-compose up
docker run -it --rm --net=host confluentinc/cp-schema-registry:3.3.1 bash

kafka-avro-console-consumer

# produce a record with one field

kafka-avro-console-producer \
    --broker-list 192.168.99.100:9092 \
    --topic test-avro \
    --property schema.registry.url=http://192.168.99.100:8081 \
    --property value.schema='
    {"type":"record","name":"myrecord",
    "fields":[{"name":"f1","type":"string"}]}'

{"f1": "value1"}
{"f1": "value2"}
{"f1": "value3"}
# let's trigger an error
{"f2": "value4"}
# let's trigger another error
{"f1": 1}

# consume the records from the beginning of the topic

kafka-avro-console-consumer \
    --topic test-avro \
    --bootstrap-server 192.168.99.100:9092 \
    --property schema.registry.url=http://192.168.99.100:8081 \
    --from-beginning

#produce some errors with incompatible schema (we changed to int)
# - should produce a 409

#in another terminal run following commands
docker run -it --rm --net=host confluentinc/cp-schema-registry:3.3.1 bash

kafka-avro-console-producer \
    --broker-list 192.168.99.100:9092 \
    --topic test-avro \
    --property schema.registry.url=http://192.168.99.100:8081 \
    --property value.schema='{"type":"int"}'

#some schema evolution (we add a field f2 as an int with a default)
kafka-avro-console-producer \
    --broker-list 192.168.99.100:9092 \
    --topic test-avro \
    --property schema.registry.url=http://192.168.99.100:8081 \
    --property value.schema='
    {"type":"record","name":"myrecord",
     "fields":[{"name":"f1","type":"string"},
     {"name":"f2","type":"int","default":0}]}'


{"f1":"evolution","f2":1}

#Consume the records again from the beginning of the topic

kafka-avro-console-consumer \
    --topic test-avro \
    --bootstrap-server 192.168.99.100:9092 \
    --property schema.registry.url=http://192.168.99.100:8081 \
    --from-beginning
