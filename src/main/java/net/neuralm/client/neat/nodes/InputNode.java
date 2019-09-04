package net.neuralm.client.neat.nodes;

import net.neuralm.client.neat.ConnectionGene;

public class InputNode extends AbstractNode {

    private double value;

    public InputNode(int nodeIdentifier) {
        super(nodeIdentifier);
    }

    @Override
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public void addDependency(ConnectionGene connectionGene) {
        System.err.println("Trying to add a dependency to an input!");
    }
}
