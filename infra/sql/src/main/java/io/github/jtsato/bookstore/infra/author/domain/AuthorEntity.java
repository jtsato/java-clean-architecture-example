package io.github.jtsato.bookstore.infra.author.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Entity
@Table(name = "AUTHORS", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"}, name = "UN_AUTHORS_NAME"),})
public class AuthorEntity implements Serializable {

    private static final long serialVersionUID = 1989818798210228136L;

    @Access(AccessType.PROPERTY)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUTHOR_ID", updatable = false, insertable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "GENDER", nullable = false)
    private String gender;

    @Column(name = "BIRTHDATE", nullable = false)
    private LocalDate birthdate;
}
