package br.com.developer.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

class KafkaService<T> implements Closeable {

    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction parse;

    KafkaService(final String groupId, final String topic, final ConsumerFunction parse,
        final Class<T> type) {
        this(groupId, parse, type);
        consumer.subscribe(Collections.singletonList(topic));
    }

    KafkaService(final String groupId, final Pattern topic, final ConsumerFunction parse,
        final Class<T> type) {
        this(groupId, parse, type);
        consumer.subscribe(topic);
    }

    private KafkaService(final String groupId, final ConsumerFunction parse,
        final Class<T> type) {
        this.parse = parse;
        this.consumer = new KafkaConsumer<>(properties(type, groupId));
    }

    void run() {
        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                System.out.println("found " + records.count() + " records");
                for (var record : records) {
                    parse.consumer(record);
                }
            }
        }
    }

    private Properties properties(final Class<T> type, final String groupId) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
        return properties;
    }

    @Override
    public void close() {
        consumer.close();
    }

}
