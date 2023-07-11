package de.tramotech.demo.kafkachatdemo;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Arrays;
import java.util.Properties;

import static java.lang.System.getProperty;

public class KafkaClient {
    private static final String BOOSTRAP_SERVERS = getProperty("server", "localhost:9092");
    private static final String TOPIC = getProperty("topic", "testtuan2");
    private static final String GROUP_ID = getProperty("user", "my-group");
    private static KafkaClient INSTANCE = null;
    private final Consumer<String, String> consumer;
    private final KafkaProducer<String, String> producer;

    private KafkaClient() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOSTRAP_SERVERS);
        props.put("group.id", GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(TOPIC));
        producer = new KafkaProducer<>(props);
    }

    public void sendMessage(String msg) {
        ProducerRecord<String, String> prducerRecord = new ProducerRecord<>(TOPIC, GROUP_ID, msg);
        producer.send(prducerRecord);
        producer.flush();
    }

    public ConsumerRecords<String, String> pollRecords() {
        return consumer.poll(100);
    }

    public void commitAsynch() {
        consumer.commitAsync();
    }

    static KafkaClient getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new KafkaClient();
        }
        return INSTANCE;
    }






}