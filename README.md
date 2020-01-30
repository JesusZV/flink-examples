# flink-examples
A repository with some flink examples


Para probar los ejemplos de Kafka se necesita hacer estos post a kafka connect

curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '
{
    "name": "source-connector",
    "config": {
        "tasks.max": "1",
        "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
        "database.hostname": "testdbserver.cce1s9vpxcba.us-east-1.rds.amazonaws.com",
        "database.port": "5432",
        "database.user": "dbuser",
        "database.password": "Test2017",
        "database.dbname": "testdb",
        "plugin.name": "wal2json",
        "slot.name": "wal2json_rds",
        "snapshot.mode": "always",
        "database.server.name": "conekta",
        "database.history.kafka.bootstrap.servers": "broker:29092",
        "database.history.kafka.topic": "codes",
        "table.whitelist": "public.codes",
        "key.converter": "org.apache.kafka.connect.json.JsonConverter",
        "value.converter": "org.apache.kafka.connect.json.JsonConverter"
    }
}'



curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '
{
    "name": "source-connector",
    "config": {
        "tasks.max": "1",
        "connector.class": "io.debezium.connector.postgresql.PostgresConnector",
        "database.hostname": "testdbserver.cce1s9vpxcba.us-east-1.rds.amazonaws.com",
        "database.port": "5432",
        "database.user": "dbuser",
        "database.password": "Test2017",
        "database.dbname": "testdb",
        "plugin.name": "wal2json",
        "slot.name": "wal2json_rds",
        "snapshot.mode": "always",
        "database.server.name": "conekta",
        "database.history.kafka.bootstrap.servers": "broker:29092",
        "database.history.kafka.topic": "person",
        "table.whitelist": "public.person"
    }
}'


Los ejemplos de twitter corren solo iniciando
