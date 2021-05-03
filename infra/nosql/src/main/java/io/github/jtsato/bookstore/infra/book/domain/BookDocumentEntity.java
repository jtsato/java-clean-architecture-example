package io.github.jtsato.bookstore.infra.book.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document(collection = "book-document-entities")
public class BookDocumentEntity implements Serializable {

    private static final long serialVersionUID = -8837692706666523585L;

    @Id
    private Long id;

    @Indexed(direction = IndexDirection.ASCENDING)
    private Long bookId;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String contentType;

    @Indexed(direction = IndexDirection.ASCENDING)    
    private String extension;

    @Indexed(direction = IndexDirection.ASCENDING)    
    private String name;

    @Indexed(direction = IndexDirection.ASCENDING)
    private Long size;

    @Indexed(direction = IndexDirection.ASCENDING)
    private String content;

    @Indexed(direction = IndexDirection.DESCENDING)    
    private LocalDateTime creationDate;

    @Indexed(direction = IndexDirection.DESCENDING)
    private LocalDateTime updateDate;
}
