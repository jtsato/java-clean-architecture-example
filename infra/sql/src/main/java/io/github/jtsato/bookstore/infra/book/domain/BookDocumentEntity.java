package io.github.jtsato.bookstore.infra.book.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(name = "BOOK_DOCUMENTS", uniqueConstraints = {@UniqueConstraint(columnNames = {"BOOK_ID"}, name = "UN_BOOK_DOCUMENTS_BOOK_ID"),})
public class BookDocumentEntity implements Serializable {

    private static final long serialVersionUID = -8837692706666523585L;

    @Access(AccessType.PROPERTY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_DOCUMENT_ID", updatable = false, insertable = false)
    private Long id;

    @Column(name = "BOOK_ID", updatable = false, insertable = false)
    private Long bookId;

    @Column(name = "CONTENT_TYPE", nullable = false)
    private String contentType;

    @Column(name = "EXTENSION", nullable = false)
    private String extension;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SIZE", nullable = false)
    private Long size;

    @Lob
    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATION_DATE", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "UPDATE_DATE", nullable = false)
    private LocalDateTime updateDate;
}
