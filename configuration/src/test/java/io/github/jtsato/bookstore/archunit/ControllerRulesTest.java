package io.github.jtsato.bookstore.archunit;

import static com.tngtech.archunit.core.domain.JavaClass.Functions.GET_PACKAGE_NAME;
import static com.tngtech.archunit.core.domain.JavaMember.Predicates.declaredIn;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.base.PackageMatchers;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMember;
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
public class ControllerRulesTest {

    @ArchTest
    static final ArchRule controllers_should_only_call_secured_methods = classes().that()
                                                                                  .resideInAPackage("..controller")
                                                                                  .should()
                                                                                  .onlyCallMethodsThat(allowedPackages());

    @ArchTest
    static final ArchRule controllers_should_only_call_secured_constructors = classes().that()
                                                                                       .resideInAPackage("..controller")
                                                                                       .should()
                                                                                       .onlyCallConstructorsThat(allowedPackages());

    @ArchTest
    static final ArchRule controllers_should_only_call_secured_code_units = classes().that()
                                                                                     .resideInAPackage("..controller")
                                                                                     .should()
                                                                                     .onlyCallCodeUnitsThat(allowedPackages());

    @ArchTest
    static final ArchRule controllers_should_only_access_secured_fields = classes().that()
                                                                                   .resideInAPackage("..controller")
                                                                                   .should()
                                                                                   .onlyAccessFieldsThat(allowedPackages());

    @ArchTest
    static final ArchRule controllers_should_only_access_secured_members = classes().that()
                                                                                    .resideInAPackage("..controller")
                                                                                    .should()
                                                                                    .onlyAccessMembersThat(allowedPackages());

    private static DescribedPredicate<JavaMember> allowedPackages() {
        return areDeclaredInController().or(areDeclaredInUseCase())
                                        .or(areDeclaredInMapper())
                                        .or(areDeclaredInRestDomain())
                                        .or(areDeclaredInRestCommon())
                                        .or(areDeclaredInSpringFramework())
                                        .or(areDeclaredInApacheCommons())
                                        .or(areLogger());
    }

    private static DescribedPredicate<JavaMember> areDeclaredInController() {
        final DescribedPredicate<JavaClass> aPackageController = GET_PACKAGE_NAME.is(PackageMatchers.of("..controller", "java.."))
                                                                                 .as("a package '..controller'");
        return are(declaredIn(aPackageController));
    }

    private static DescribedPredicate<JavaMember> areDeclaredInUseCase() {
        final DescribedPredicate<JavaClass> aPackageController = GET_PACKAGE_NAME.is(PackageMatchers.of("..usecase..", "java..")).as("a package '..usecase..'");
        return are(declaredIn(aPackageController));
    }

    private static DescribedPredicate<JavaMember> areDeclaredInMapper() {
        final DescribedPredicate<JavaClass> aPackageController = GET_PACKAGE_NAME.is(PackageMatchers.of("..mapper..", "java..")).as("a package '..mapper..'");
        return are(declaredIn(aPackageController));
    }

    private static DescribedPredicate<JavaMember> areDeclaredInRestDomain() {
        final DescribedPredicate<JavaClass> aPackageController = GET_PACKAGE_NAME.is(PackageMatchers.of("..rest..domain..", "java.."))
                                                                                 .as("a package '..domain..'");
        return are(declaredIn(aPackageController));
    }

    private static DescribedPredicate<JavaMember> areDeclaredInRestCommon() {
        final DescribedPredicate<JavaClass> aPackageController = GET_PACKAGE_NAME.is(PackageMatchers.of("..rest..common..", "java.."))
                                                                                 .as("a package '..common..'");
        return are(declaredIn(aPackageController));
    }

    private static DescribedPredicate<JavaMember> areDeclaredInSpringFramework() {
        final DescribedPredicate<JavaClass> aPackageController = GET_PACKAGE_NAME.is(PackageMatchers.of("..org.springframework..", "java.."))
                                                                                 .as("a package '..springframework..'");
        return are(declaredIn(aPackageController));
    }

    private static DescribedPredicate<JavaMember> areDeclaredInApacheCommons() {
        final DescribedPredicate<JavaClass> aPackageController = GET_PACKAGE_NAME.is(PackageMatchers.of("..apache.commons..", "java.."))
                                                                                 .as("a package '..apache.commons..'");
        return are(declaredIn(aPackageController));
    }

    private static DescribedPredicate<JavaMember> areLogger() {
        return are(declaredIn(org.slf4j.Logger.class)).or(declaredIn(org.slf4j.LoggerFactory.class));
    }
}
