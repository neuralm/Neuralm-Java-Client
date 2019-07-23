package net.neuralm.client.neat;

import net.neuralm.client.neat.neurons.AbstractNeuron;

public class ConnectionGene {

    private int inID;
    private int outID;
    private double weight;
    private boolean enabled;

    private AbstractNeuron inputNeuron;
    private AbstractNeuron outputNeuron;

    public ConnectionGene() {

    }

    public ConnectionGene(int inId, int outId, double weight, boolean enabled) {
        this.inID = inId;
        this.outID = outId;
        this.weight = weight;
        this.enabled = enabled;
    }

    public double getValue() {
        return inputNeuron.getValue() * weight;
    }

    void initializeStructure(Brain brain) {
        if (enabled) {
            inputNeuron = brain.getNeuronOrCreate(inID);
            outputNeuron = brain.getNeuronOrCreate(outID);

            outputNeuron.addDependency(this);
        }
    }
}
