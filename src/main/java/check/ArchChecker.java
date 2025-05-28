
package check;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.EvaluationResult;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;


public class ArchChecker {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("USO: java -jar arch-checker.jar <ruta_a_clases>");
            System.exit(1);
        }
        String pathToClasses = args[0];
        JavaClasses classes = new ClassFileImporter().importPaths(pathToClasses);

        ArchRule uiOnlyToService = classes()
            .that().resideInAPackage("..ui..")
            .should().onlyDependOnClassesThat()
                .resideInAnyPackage("..ui..", "java..")
            .because("UI no debe acceder a DAO directo");

        ArchRule serviceOnlyToDao = classes()
            .that().resideInAPackage("..service..")
            .should().onlyDependOnClassesThat()
                .resideInAnyPackage("..service..", "java..")
            .because("Service no debe acceder a UI directo");

        ArchRule noDaoToUiOrService = classes()
            .that().resideInAPackage("..dao..")
            .should().onlyDependOnClassesThat()
                .resideInAnyPackage( "java..","..ui..", "..service..")
            .because("DAO no debe acceder a UI ni Service");

        // Ejecuta cada regla e imprime las violaciones
        runRule("UI→Service", uiOnlyToService, classes);
        runRule("Service→DAO", serviceOnlyToDao, classes);
        runRule("DAO→(UI|Service)", noDaoToUiOrService, classes);
    }

    private static void runRule(String name, ArchRule rule, JavaClasses classes) {
    System.out.println("Regla: " + name);

    EvaluationResult result = rule.evaluate(classes);
    if (result.hasViolation()) {
        System.out.println("  ✗ Violaciones encontradas:");
        result.getFailureReport().getDetails().forEach(detail -> {
            System.out.println("    - " + detail);
        });
    } else {
        System.out.println("  ✓ OK");
    }
    System.out.println();
}
}
