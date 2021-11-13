package org.shanzhaozhen.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToStringListSerialize extends JsonSerializer<List<Object>> {

    @Override
    public void serialize(List<Object> values, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (CollectionUtils.isEmpty(values)) {
            gen.writeObject(null);
        }

        List<String> strings = new ArrayList<>();
        for (Object value : values) {
            strings.add(value.toString());
        }
        gen.writeObject(strings);
    }
}
