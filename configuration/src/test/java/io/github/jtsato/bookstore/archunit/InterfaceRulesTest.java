package io.github.jtsato.bookstore.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

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
public class InterfaceRulesTest {

    @ArchTest
    static final ArchRule interfaces_should_not_have_names_ending_with_the_word_interface = noClasses().that()
                                                                                                       .areInterfaces()
                                                                                                       .should()
                                                                                                       .haveNameMatching(".*Interface");

    @ArchTest
    static final ArchRule interfaces_should_not_have_simple_class_names_containing_the_word_interface = noClasses().that()
                                                                                                                   .areInterfaces()
                                                                                                                   .should()
                                                                                                                   .haveSimpleNameContaining("Interface");

    @ArchTest
    static final ArchRule interfaces_must_not_be_placed_in_implementation_packages = noClasses().that().resideInAPackage("..impl..").should().beInterfaces();
}
