/*package de.fhkiel.advancedjava;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchIgnore;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "de.fhkiel.advancedjava")
public class ArchitectureTest {

    @ArchIgnore
    @ArchTest
    public static final ArchRule layeredRule
            = layeredArchitecture()
            .layer("Controller").definedBy("..controller..")
            .layer("Service").definedBy("..service..")
            .layer("Converter").definedBy("..converter..")
            .layer("Repository").definedBy("..repository..")
            .layer("Model").definedBy("..model..")

            .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Converter")
            .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
            .whereLayer("Model").mayOnlyBeAccessedByLayers("Repository", "Controller", "Converter", "Service", "Model");

}*/
