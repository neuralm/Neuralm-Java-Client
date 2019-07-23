package net.neuralm.client.neat.neurons;

import net.neuralm.client.neat.ConnectionGene;

public class InputNeuron extends AbstractNeuron {

    private double value;

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
