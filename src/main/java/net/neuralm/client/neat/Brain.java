package net.neuralm.client.neat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import net.neuralm.client.neat.neurons.AbstractNeuron;
import net.neuralm.client.neat.neurons.HiddenNeuron;
import net.neuralm.client.neat.neurons.InputNeuron;
import net.neuralm.client.neat.neurons.OutputNeuron;

public class Brain {

    UUID id;
    UUID trainingRoomId;
    UUID organismId;
    private List<ConnectionGene> connectionGenes = new ArrayList<>();

    private final List<OutputNeuron> outputNeurons = new ArrayList<>();
    private final List<InputNeuron> inputNeurons = new ArrayList<>();
    private final HashMap<Integer, AbstractNeuron> neurons = new HashMap<>();
    private int inputCount;
    private int outputCount;


    private boolean buildStructure = false;

    public Brain() {

    }

    /**
     * Create a brain with the given input and output count and the given genes.
     *
     * @param inputCount Amount of input neurons
     * @param outputCount Amount of output neurons
     * @param genes Amount of genes
     */
    public Brain(int inputCount, int outputCount, List<ConnectionGene> genes) {
        this.inputCount = inputCount;
        this.outputCount = outputCount;
        this.connectionGenes = genes;
    }

    /**
     * Evaluate the brain with the given inputs
     *
     * @param inputs A double array with the size {@link Brain#inputCount}
     * @return A double array with size {@link Brain#outputCount} where index i has the output value of the output neuron with id {@link Brain#inputCount}+i
     */
    public double[] evaluate(double[] inputs) {
        if (!buildStructure) {
            for (int i = 0; i < inputCount; i++) {
                InputNeuron neuron = new InputNeuron();
                inputNeurons.add(neuron);
                neurons.put(i, neuron);
            }

            for (int i = 0; i < outputCount; i++) {
                OutputNeuron neuron = new OutputNeuron();
                outputNeurons.add(neuron);
                neurons.put(inputCount + i, neuron);
            }

            for (ConnectionGene connectionGene : connectionGenes) {
                connectionGene.initializeStructure(this);
            }
            buildStructure = true;
        }

        if (inputs.length != inputNeurons.size()) {
            throw new IllegalArgumentException(String.format("Input size %s does not match number of input neurons %s", inputs.length, inputNeurons.size()));
        }

        for (int i = 0; i < inputNeurons.size(); i++) {
            inputNeurons.get(i).setValue(inputs[i]);
        }

        double[] outputs = new double[outputNeurons.size()];
        for (int i = 0; i < outputNeurons.size(); i++) {
            outputs[i] = outputNeurons.get(i).getValue();
        }

        return outputs;
    }

    /**
     * Get a neuron with the given id or generate a new one if none exist.
     *
     * @param neuronId The neuron's id
     * @return A neuron
     */
    AbstractNeuron getNeuronOrCreate(int neuronId) {
        if (!neurons.containsKey(neuronId)) {
            neurons.put(neuronId, new HiddenNeuron());
        }

        return neurons.get(neuronId);
    }
}
