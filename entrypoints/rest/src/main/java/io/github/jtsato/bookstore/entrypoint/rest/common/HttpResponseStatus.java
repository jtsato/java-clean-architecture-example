package io.github.jtsato.bookstore.entrypoint.rest.common;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jorge Takeshi Sato
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponseStatus implements Serializable {

    private static final long serialVersionUID = -3181059020804074734L;

    private LocalDateTime timestamp;

    private long status;

    private String error;

    private String message;

    private String path;
}
