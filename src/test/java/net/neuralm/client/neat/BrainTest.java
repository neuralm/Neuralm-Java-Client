package net.neuralm.client.neat;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

class BrainTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/evaluateTest.csv")
    void evaluate(double inA, double inB, double inC, double expected) {
        Brain brain = new Brain(3, 1, Arrays.asList(

            new ConnectionGene(0, 3, 1, true),
            new ConnectionGene(1, 3, 1, false),
            new ConnectionGene(2, 3, 1, true),
            new ConnectionGene(1, 4, 1, true),
            new ConnectionGene(4, 3, 1, true),
            new ConnectionGene(0, 4, 1, true)

        ));

        Assertions.assertEquals(expected, brain.evaluate(new double[]{inA, inB, inC})[0], 0.0000000001);
    }
}