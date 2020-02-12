package br.com.developer.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface ConsumerFunction<T> {

    void consumer(ConsumerRecord<String, T> record);

}
