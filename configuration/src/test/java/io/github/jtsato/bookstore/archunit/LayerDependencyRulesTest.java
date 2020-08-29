package io.github.jtsato.bookstore.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

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
public class LayerDependencyRulesTest {

    // 'should only access'
    // catches only violations by real accesses, i.e. accessing a field, calling a method

    @ArchTest
    static final ArchRule controllers_should_only_access_specific_packages = classes().that()
                                                                                      .resideInAPackage("..controller..")
                                                                                      .should()
                                                                                      .onlyAccessClassesThat()
                                                                                      .resideInAnyPackage("..controller..",
                                                                                                          "..usecase..",
                                                                                                          "..core.exception..",
                                                                                                          "..mapper..",
                                                                                                          "..rest..",
                                                                                                          "org.springframework..",
                                                                                                          "org.slf4j",
                                                                                                          "org.apache.commons..",
                                                                                                          "java..");

    @ArchTest
    static final ArchRule usecases_should_only_access_specific_packages = classes().that()
                                                                                   .resideInAPackage("..usecase..")
                                                                                   .should()
                                                                                   .onlyAccessClassesThat()
                                                                                   .resideInAnyPackage("..usecase..",
                                                                                                       "..gateway..",
                                                                                                       "..domain..",
                                                                                                       "..core..exception..",
                                                                                                       "..core..common..",
                                                                                                       "org.apache.commons..",
                                                                                                       "java..");

    @ArchTest
    static final ArchRule dataproviders_should_only_access_specific_packages = classes().that()
                                                                                        .resideInAPackage("..dataprovider..")
                                                                                        .should()
                                                                                        .onlyAccessClassesThat()
                                                                                        .resideInAnyPackage("..dataprovider..",
                                                                                                            "..usecase.parameter..",
                                                                                                            "..core..gateway..",
                                                                                                            "..core..domain..",
                                                                                                            "..core.common..",
                                                                                                            "org.springframework..",
                                                                                                            "com.querydsl..",
                                                                                                            "com.cosium.spring..",
                                                                                                            "org.apache.commons..",
                                                                                                            "com.p6spy.engine.logging..",
                                                                                                            "org.hibernate.engine..",
                                                                                                            "org.mapstruct..",
                                                                                                            "java..");

    @ArchTest
    static final ArchRule mappers_should_only_access_specific_packages = classes().that()
                                                                                  .resideInAPackage("..mapper..")
                                                                                  .should()
                                                                                  .onlyAccessClassesThat()
                                                                                  .resideInAnyPackage("..mapper..",
                                                                                                      "..domain..",
                                                                                                      "..response..",
                                                                                                      "..core.common..",
                                                                                                      "org.mapstruct..",
                                                                                                      "org.springframework..",
                                                                                                      "java..");

    @ArchTest
    static final ArchRule core_domains_should_only_access_themselves_ = classes().that()
                                                                                 .resideInAPackage("..core..domain..")
                                                                                 .should()
                                                                                 .onlyAccessClassesThat()
                                                                                 .resideInAnyPackage("..core..domain..", "java..");

    // 'should only be access by'
    // catches only violations by real accesses, i.e. accessing a field, calling a method

    @ArchTest
    static final ArchRule usecases_should_only_be_accessed_by_controllers = classes().that()
                                                                                     .resideInAPackage("..usecase.impl..")
                                                                                     .should()
                                                                                     .onlyBeAccessed()
                                                                                     .byAnyPackage("..usecase..",
                                                                                                   "..configuration..",
                                                                                                   "..controller..",
                                                                                                   "..gateway..");

    @ArchTest
    static final ArchRule gateways_should_only_be_accessed_by_usecases = classes().that()
                                                                                  .resideInAPackage("..gateway..")
                                                                                  .should()
                                                                                  .onlyBeAccessed()
                                                                                  .byAnyPackage("..usecase..");

    @ArchTest
    static final ArchRule dataproviders_should_only_be_accessed_by_usecases_or_other_dataproviders = classes().that()
                                                                                                              .resideInAPackage("..dataprovider..")
                                                                                                              .should()
                                                                                                              .onlyBeAccessed()
                                                                                                              .byAnyPackage("..dataprovider..", "..usecase..");

    // 'dependOn' catches a wider variety of violations, e.g. having fields of type, having method parameters of type, extending type ...

    @ArchTest
    static final ArchRule core_should_only_depend_on_specific_packages = classes().that()
                                                                                  .resideInAPackage("..core..")
                                                                                  .should()
                                                                                  .onlyDependOnClassesThat()
                                                                                  .resideInAnyPackage("..core..",
                                                                                                      "org.apache.commons..",
                                                                                                      "lombok..",
                                                                                                      "javax..",
                                                                                                      "java..");

    @ArchTest
    static final ArchRule core_domains_should_only_depend_on_specific_packages = classes().that()
                                                                                          .resideInAPackage("..core..domain..")
                                                                                          .should()
                                                                                          .onlyDependOnClassesThat()
                                                                                          .resideInAnyPackage("..core..domain..", "lombok..", "java..");

    @ArchTest
    static final ArchRule controllers_should_only_depend_on_specific_packages = classes().that()
                                                                                         .resideInAPackage("..controller..")
                                                                                         .should()
                                                                                         .onlyDependOnClassesThat()
                                                                                         .resideInAnyPackage("..controller..",
                                                                                                             "..rest..",
                                                                                                             "..usecase..",
                                                                                                             "..core.exception..",
                                                                                                             "io.swagger.v3.oas.annotations..",
                                                                                                             "org.springdoc..",
                                                                                                             "org.springframework..",
                                                                                                             "org.slf4j",
                                                                                                             "org.apache.commons..",
                                                                                                             "lombok..",
                                                                                                             "..java..");

    @ArchTest
    static final ArchRule gateways_should_only_have_dependents_on_specific_packages = classes().that()
                                                                                               .resideInAPackage("..gateway..")
                                                                                               .should()
                                                                                               .onlyHaveDependentClassesThat()
                                                                                               .resideInAnyPackage("..configuration..",
                                                                                                                   "..usecase..",
                                                                                                                   "..dataprovider..");

    @ArchTest
    static final ArchRule usecases_should_only_depend_on_specific_packages = classes().that()
                                                                                      .resideInAPackage("..usecase..")
                                                                                      .should()
                                                                                      .onlyDependOnClassesThat()
                                                                                      .resideInAnyPackage("..core..",
                                                                                                          "org.apache.commons..",
                                                                                                          "lombok..",
                                                                                                          "javax..",
                                                                                                          "java..");
}
