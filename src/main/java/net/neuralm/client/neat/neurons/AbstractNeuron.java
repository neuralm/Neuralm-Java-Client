package net.neuralm.client.neat.neurons;

import java.util.ArrayList;
import java.util.List;
import net.neuralm.client.neat.ConnectionGene;

public abstract class AbstractNeuron {

    private List<ConnectionGene> dependencies = new ArrayList<ConnectionGene>();

    public double getValue() {
        if (dependencies.size() == 0) {
            return activationFunction(0);
        }

        int count = 0;
        double total = 0;

        for (ConnectionGene gene : dependencies) {
            total += gene.getValue();
            count++;
        }

        return activationFunction(total / count);
    }

    private double activationFunction(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public void addDependency(ConnectionGene connectionGene) {
        dependencies.add(connectionGene);
    }
}
