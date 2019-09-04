package net.neuralm.client.neat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;

class OrganismTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/evaluateTest.csv")
    void getValue(double inA, double inB, double inC, double expected) {
//        TrainingRoom room = new TrainingRoom();
//        room.trainingRoomSettings = new TrainingRoomSettings().setInputCount(3).setOutputCount(1);
        Organism brain = new Organism(Arrays.asList(

            new ConnectionGene(0, 3, 1, true),
            new ConnectionGene(1, 3, 1, false),
            new ConnectionGene(2, 3, 1, true),
            new ConnectionGene(1, 4, 1, true),
            new ConnectionGene(4, 3, 1, true),
            new ConnectionGene(0, 4, 1, true)

        ), 3, 1);

        Assertions.assertEquals(expected, brain.evaluate(new double[]{inA, inB, inC})[0], 0.0000000001);
    }
}