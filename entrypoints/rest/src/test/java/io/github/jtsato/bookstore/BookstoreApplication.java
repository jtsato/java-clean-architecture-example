package io.github.jtsato.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jorge Takeshi Sato  
 */

@SpringBootApplication(scanBasePackageClasses = {BookstoreApplication.class})
public class BookstoreApplication {

    public static void main(final String[] args) {
        SpringApplication.run(BookstoreApplication.class);
    }
}
