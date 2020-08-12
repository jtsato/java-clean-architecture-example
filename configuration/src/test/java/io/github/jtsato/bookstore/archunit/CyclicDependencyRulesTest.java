package io.github.jtsato.bookstore.archunit;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;

import io.github.jtsato.bookstore.BookstoreApplication;

/**
 * @author Jorge Takeshi Sato
 *
 * This class was based on the example available in the archunit repository in
 * https://github.com/TNG/ArchUnit-Examples/tree/master/example-junit5
 */

@AnalyzeClasses(packagesOf = BookstoreApplication.class)
public class CyclicDependencyRulesTest {

    @ArchTest
    static final ArchRule no_cycles_by_method_calls_between_slices = slices().matching("..(simplecycle).(*)..")
                                                                             .namingSlices("$2 of $1")
                                                                             .should()
                                                                             .beFreeOfCycles();

    @ArchTest
    static final ArchRule no_cycles_by_constructor_calls_between_slices = slices().matching("..(constructorcycle).(*)..")
                                                                                  .namingSlices("$2 of $1")
                                                                                  .should()
                                                                                  .beFreeOfCycles();

    @ArchTest
    static final ArchRule no_cycles_by_inheritance_between_slices = slices().matching("..(inheritancecycle).(*)..")
                                                                            .namingSlices("$2 of $1")
                                                                            .should()
                                                                            .beFreeOfCycles();

    @ArchTest
    static final ArchRule no_cycles_by_field_access_between_slices = slices().matching("..(fieldaccesscycle).(*)..")
                                                                             .namingSlices("$2 of $1")
                                                                             .should()
                                                                             .beFreeOfCycles();

    @ArchTest
    static final ArchRule no_cycles_by_member_dependencies_between_slices = slices().matching("..(membercycle).(*)..")
                                                                                    .namingSlices("$2 of $1")
                                                                                    .should()
                                                                                    .beFreeOfCycles();

    @ArchTest
    static final ArchRule no_cycles_in_simple_scenario = slices().matching("..simplescenario.(*)..").namingSlices("$1").should().beFreeOfCycles();

    @ArchTest
    static final ArchRule no_cycles_in_complex_scenario = slices().matching("..(complexcycles).(*)..").namingSlices("$2 of $1").should().beFreeOfCycles();

    @ArchTest
    static final ArchRule no_cycles_in_freely_customized_slices = slices().assignedFrom(inComplexSliceOneOrTwo())
                                                                          .namingSlices("$1[$2]")
                                                                          .should()
                                                                          .beFreeOfCycles();

    private static SliceAssignment inComplexSliceOneOrTwo() {
        return new SliceAssignment() {

            @Override
            public String getDescription() {
                return "complex slice one or two";
            }

            @Override
            public SliceIdentifier getIdentifierOf(final JavaClass javaClass) {
                if (javaClass.getPackageName().contains("complexcycles.slice1")) {
                    return SliceIdentifier.of("Complex-Cycle", "One");
                }
                if (javaClass.getPackageName().contains("complexcycles.slice2")) {
                    return SliceIdentifier.of("Complex-Cycle", "Two");
                }
                return SliceIdentifier.ignore();
            }
        };
    }
}
