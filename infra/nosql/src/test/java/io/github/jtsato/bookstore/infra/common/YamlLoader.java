package io.github.jtsato.bookstore.infra.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jorge Takeshi Sato
 */

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class YamlLoader {

    public static <T> List<T> loadAll(final Class<T> clazz, final String filePath) {
        try {
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.registerModule(new JavaTimeModule());
            return mapper.readValue(filePath, mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
        } catch (final IOException exception) {
            log.error("The file {} was not loaded. Cause: {}", filePath, exception);
        }
        return Collections.emptyList();
    }

    public static <T> List<T> loadAll(final Class<T> clazz, final Resource resource) {
        try {
            final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            mapper.registerModule(new JavaTimeModule());
            return mapper.readValue(resource.getInputStream(), mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
        } catch (final IOException exception) {
            log.error("The file {} was not loaded. Cause: {}", resource.getFilename(), exception);
        }
        return Collections.emptyList();
    }
}
