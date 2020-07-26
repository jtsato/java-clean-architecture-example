package io.github.jtsato.bookstore.dataprovider.rabbitmq.author.domain.message;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Jorge Takeshi Sato  
 */

@Getter
@AllArgsConstructor
public class UpdateAuthorByIdMessage implements Serializable {

    private static final long serialVersionUID = -3128994081332681868L;
    
    private Long id;

    private String name;

    private String gender;
    
    private String birthday;    
}
