

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Pruebas de arquitectura para asegurar la separación de capas:
 *   UI → Service → DAO
 *   No UI → DAO directo
 *   Ni DAO → Service/UI inverso
 */
@AnalyzeClasses(packages = {"dao", "service", "ui"})
public class ArquitecturaTest {

    /** Las clases en UI solo pueden depender de UI, Service y JDK */
    @ArchTest
    static final ArchRule uiSoloAccedeAService =
        classes().that().resideInAnyPackage("..ui..")
                 .should().onlyDependOnClassesThat()
                 .resideInAnyPackage("..ui..", "..service..", "java..");

    /** Las clases en Service solo pueden depender de Service, DAO y JDK */
    @ArchTest
    static final ArchRule serviceSoloAccedeADao =
        classes().that().resideInAnyPackage("..service..")
                 .should().onlyDependOnClassesThat()
                 .resideInAnyPackage("..service..", "..dao..", "java..");

    /** Prohíbe que UI importe clases de DAO directamente */
    @ArchTest
    static final ArchRule noUiAccedaADao =
        noClasses().that().resideInAnyPackage("..ui..")
                   .should().dependOnClassesThat()
                   .resideInAnyPackage("..dao..");

    /** Prohíbe que DAO dependa de Service o UI */
    @ArchTest
    static final ArchRule daoNoAccedeAServiceNiUi =
        noClasses().that().resideInAnyPackage("..dao..")
                   .should().dependOnClassesThat()
                   .resideInAnyPackage("..service..", "..ui..");

    /** Prohíbe que Service dependa de UI */
    @ArchTest
    static final ArchRule serviceNoAccedeAUi =
        noClasses().that().resideInAnyPackage("..service..")
                   .should().dependOnClassesThat()
                   .resideInAnyPackage("..ui..");
}

