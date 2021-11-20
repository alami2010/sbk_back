package com.ydahar.sbk;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.ydahar.sbk");

        noClasses()
            .that()
            .resideInAnyPackage("com.ydahar.sbk.service..")
            .or()
            .resideInAnyPackage("com.ydahar.sbk.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.ydahar.sbk.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
