package io.github.jtsato.bookstore.dataprovider.book.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;

import com.querydsl.core.annotations.QueryEntity;

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
public class BookEntity implements Serializable {

    private static final long serialVersionUID = -8837692706666523585L;

    @Transient
    public static final String SEQUENCE_NAME = "books_sequence";    

    @Indexed(direction = IndexDirection.ASCENDING)
    private Long bookId;
    
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
