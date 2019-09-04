package net.neuralm.client.neat.nodes;

import net.neuralm.client.neat.ConnectionGene;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNode {

    public final int nodeIdentifier;
    private final List<ConnectionGene> dependencies = new ArrayList<>();

    AbstractNode(int nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

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
