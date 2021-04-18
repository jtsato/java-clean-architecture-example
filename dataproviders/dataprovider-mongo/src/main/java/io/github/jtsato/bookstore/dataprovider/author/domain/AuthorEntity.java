package io.github.jtsato.bookstore.dataprovider.author.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

import io.github.jtsato.bookstore.dataprovider.book.domain.BookEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Jorge Takeshi Sato
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@QueryEntity
@Document(collection = "authors")
public class AuthorEntity implements Serializable {

    private static final long serialVersionUID = 1989818798210228136L;
    
    @Transient
    public static final String SEQUENCE_NAME = "authors_sequence";    

    @Id
    private String objectId;
    
    @Indexed(direction = IndexDirection.ASCENDING)
    private Long authorId;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String name;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String gender;

    @Indexed(direction = IndexDirection.ASCENDING)
    private LocalDate birthdate;
    
    private List<BookEntity> books = new ArrayList<>(0);
}
