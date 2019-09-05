package net.neuralm.client.neat.nodes;

import net.neuralm.client.neat.ConnectionGene;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNode {

    public final int nodeIdentifier;
    private List<ConnectionGene> dependencies;

    public AbstractNode() {
        this(-1);
    }

    AbstractNode(int nodeIdentifier) {
        this.dependencies = new ArrayList<>();
        this.nodeIdentifier = nodeIdentifier;
    }

    public double getValue() {
        if(dependencies == null) {
            dependencies = new ArrayList<>();
        }

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
        if(dependencies == null) {
            dependencies = new ArrayList<>();
        }
      
        dependencies.add(connectionGene);
    }
}
