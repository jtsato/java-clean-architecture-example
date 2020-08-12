package io.github.jtsato.bookstore.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import io.github.jtsato.bookstore.BookstoreApplication;

/**
 * @author Jorge Takeshi Sato
 *
 * This class was based on the example available in the archunit repository in
 * https://github.com/TNG/ArchUnit-Examples/tree/master/example-junit5
 */

@AnalyzeClasses(packagesOf = BookstoreApplication.class)
public class MethodsTest {

    @ArchTest
    static ArchRule all_public_methods_in_the_controller_layer_should_return_ResponseStatus = methods().that()
                                                                                                       .areDeclaredInClassesThat()
                                                                                                       .resideInAPackage("..controller..")
                                                                                                       .and()
                                                                                                       .arePublic()
                                                                                                       .should()
                                                                                                       .beAnnotatedWith(ResponseStatus.class);

    @ArchTest
    static ArchRule code_units_in_DataProvider_layer_should_be_Transactional = classes().that()
                                                                                        .resideInAPackage("..dataprovider")
                                                                                        .should()
                                                                                        .beAnnotatedWith(Transactional.class);
}
