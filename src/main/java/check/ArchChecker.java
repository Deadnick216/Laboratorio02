
package check;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
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
                .resideInAnyPackage("..ui..", "..service..", "java..")
            .because("UI no debe acceder a DAO directo");

        ArchRule serviceOnlyToDao = classes()
            .that().resideInAPackage("..service..")
            .should().onlyDependOnClassesThat()
                .resideInAnyPackage("..service..", "..dao..", "java..")
            .because("Service no debe acceder a UI directo");

        ArchRule noDaoToUiOrService = classes()
            .that().resideInAPackage("..dao..")
            .should().onlyDependOnClassesThat()
                .resideInAnyPackage("..dao..", "java..")
            .because("DAO no debe acceder a UI ni Service");

        // Ejecuta cada regla e imprime las violaciones
        runRule("UI→Service", uiOnlyToService, classes);
        runRule("Service→DAO", serviceOnlyToDao, classes);
        runRule("DAO→(UI|Service)", noDaoToUiOrService, classes);
    }

    private static void runRule(String name, ArchRule rule, JavaClasses classes) {
        System.out.println("Regla: " + name);
        try {
            rule.check(classes);
            System.out.println("  ✓ OK");
        } catch (AssertionError err) {
            System.out.println("  ✗ Violaciones encontradas:");
            // ArchUnit lanza AssertionError con detalle en el mensaje
            System.out.println(err.getMessage());
        }
        System.out.println();
    }
}
