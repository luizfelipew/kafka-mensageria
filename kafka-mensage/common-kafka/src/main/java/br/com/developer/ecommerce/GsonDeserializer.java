package br.com.developer.ecommerce;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GsonDeserializer<T> implements Deserializer<T>{

    public static final String TYPE_CONFIG = "br.com.developer.ecommerce.type_config";
    private final Gson gson = new GsonBuilder().create();
    private Class<T> type;

    @Override
    public void configure(final Map<String, ?> configs, final boolean isKey) {
        String typeName = String.valueOf(configs.get(TYPE_CONFIG));
        try {
            this.type = (Class<T>) Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Type for deserialiation does not exist in the classpath", e);
        }
    }

    @Override
    public T deserialize(final String s, final byte[] bytes) {
        return gson.fromJson(new String(bytes), type);
    }

}
