package io.github.jtsato.bookstore.dataprovider.book.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

import io.github.jtsato.bookstore.dataprovider.author.domain.AuthorEntity;
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
@Document(collection = "book-entities")
public class BookEntity implements Serializable {

    private static final long serialVersionUID = -8837692706666523585L;

    @Id
    private Long id;

    private AuthorEntity author;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String title;

    @Indexed(direction = IndexDirection.ASCENDING)
    private BigDecimal price;

    @Indexed(direction = IndexDirection.ASCENDING)
    private Boolean available;

    @Indexed(direction = IndexDirection.DESCENDING)    
    private LocalDateTime creationDate;

    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime updateDate;
}
