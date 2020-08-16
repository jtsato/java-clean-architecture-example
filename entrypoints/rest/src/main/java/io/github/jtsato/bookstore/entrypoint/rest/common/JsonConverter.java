package io.github.jtsato.bookstore.entrypoint.rest.common;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@Generated
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonConverter {

    public static String of(final Serializable serializable) {

        try {
            return new ObjectMapper().writeValueAsString(serializable);
        } catch (final JsonProcessingException jsonProcessingException) {
            return StringUtils.EMPTY;
        }
    }
}
