package io.github.jtsato.bookstore.dataprovider.database.book.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.github.jtsato.bookstore.dataprovider.database.author.domain.AuthorEntity;
import lombok.AccessLevel;
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
@Entity
@Table(name = "BOOKS", uniqueConstraints = {@UniqueConstraint(columnNames = {"TITLE"}, name = "UN_BOOKS_TITLE"),})
public class BookEntity implements Serializable {

    private static final long serialVersionUID = -8837692706666523585L;

    @Access(AccessType.PROPERTY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_ID", updatable = false, insertable = false)
    private Long id;

    @JoinColumn(name = "AUTHOR_ID", foreignKey = @ForeignKey(name = "FK_BOOK_AUTHOR_ID"))
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private AuthorEntity author;    

    @Column(name = "TITLE", nullable = false)
    private String title;
    
    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "AVAILABLE", nullable = false)
    private Boolean available;

    @Setter(value = AccessLevel.NONE)
    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;
    
    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime updateDate;
    
    @Column(name = "CONTENT", nullable = false)
    private String content;
}
