package net.neuralm.client.neat;

import net.neuralm.client.messages.serializer.JsonSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class OrganismTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/evaluateTest.csv")
    void getValue(double inA, double inB, double inC, double expected) {
        Organism organism = new Organism(Arrays.asList(

            new ConnectionGene(0, 3, 1, true),
            new ConnectionGene(1, 3, 1, false),
            new ConnectionGene(2, 3, 1, true),
            new ConnectionGene(1, 4, 1, true),
            new ConnectionGene(4, 3, 1, true),
            new ConnectionGene(0, 4, 1, true)

        ), 3, 1);

        Assertions.assertEquals(expected, organism.evaluate(new double[]{inA, inB, inC})[0], 0.0000000001);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/loadOrganismTest.csv")
    void parseFromJsonEvaluate(String json, double inA, double inB, double inC, double expected) {
        Organism organism = new JsonSerializer().deserialize(json.getBytes(StandardCharsets.UTF_8), Organism.class);
        organism.initialize();

        Assertions.assertEquals(expected, organism.evaluate(new double[]{inA, inB, inC})[0], 0.0000000001);
    }
}