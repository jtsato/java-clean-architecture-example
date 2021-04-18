package io.github.jtsato.bookstore.dataprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
// import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Jorge Takeshi Sato
 */

@EnableMongoRepositories(basePackageClasses = BookstoreApplication.class)
@SpringBootApplication
public class BookstoreApplication {

    public static void main(final String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }
}
