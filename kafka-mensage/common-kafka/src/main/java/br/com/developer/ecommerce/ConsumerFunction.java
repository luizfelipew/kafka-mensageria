package br.com.developer.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.ExecutionException;

public interface ConsumerFunction<T> {
    void consumer(ConsumerRecord<String, Message<T>> record) throws Exception;
}
