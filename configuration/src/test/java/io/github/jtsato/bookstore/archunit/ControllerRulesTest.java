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
        .onlyCallMethodsThat(areDeclaredInController().or(areDeclaredInUseCase()).or(areDeclaredInMapper()).or(areLogger()));

    @ArchTest
    static final ArchRule controllers_should_only_call_secured_constructors = classes().that()
       .resideInAPackage("..controller")
       .should()
       .onlyCallConstructorsThat(areDeclaredInController().or(areDeclaredInUseCase()).or(areDeclaredInMapper()).or(areLogger()));

    @ArchTest
    static final ArchRule controllers_should_only_call_secured_code_units = classes().that()
        .resideInAPackage("..controller")
        .should()
        .onlyCallCodeUnitsThat(areDeclaredInController().or(areDeclaredInUseCase()).or(areDeclaredInMapper()).or(areLogger()));

    @ArchTest
    static final ArchRule controllers_should_only_access_secured_fields = classes().that()
       .resideInAPackage("..controller")
       .should()
       .onlyAccessFieldsThat(areDeclaredInController().or(areDeclaredInUseCase()).or(areDeclaredInMapper()).or(areLogger()));

    @ArchTest
    static final ArchRule controllers_should_only_access_secured_members = classes().that()
        .resideInAPackage("..controller")
        .should()
        .onlyAccessMembersThat(areDeclaredInController().or(areDeclaredInUseCase()).or(areDeclaredInMapper()).or(areLogger()));

    private static DescribedPredicate<JavaMember> areDeclaredInController() {
        final DescribedPredicate<JavaClass> aPackageController = GET_PACKAGE_NAME.is(PackageMatchers.of("..controller", "java..")).as("a package '..controller'");
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

    private static DescribedPredicate<JavaMember> areLogger() {
        return are(declaredIn(org.slf4j.Logger.class));
    }
}
