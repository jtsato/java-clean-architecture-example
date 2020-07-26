package io.github.jtsato.bookstore.dataprovider.rabbitmq.book.domain.message;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
public class RegisterBookMessage implements Serializable {

    private static final long serialVersionUID = -1401793357376446399L;

    private String title;
    
    private Long authorId;
}
