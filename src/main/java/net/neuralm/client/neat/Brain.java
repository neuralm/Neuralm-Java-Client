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
    private UUID id;
    private UUID trainingRoomId;
    private UUID organismId;
    private List<ConnectionGene> connectionGenes = new ArrayList<>();
  
    private boolean buildStructure = false;
    private TrainingRoom trainingRoom;
    private Organism organism;

    public Brain() {

    }

    /**
     * Create a brain with the given trainingRoom and the given genes.
     *
     * @param trainingRoom The trainingRoom this brain is part of
     * @param genes Amount of genes
     */
    public Brain(TrainingRoom trainingRoom, List<ConnectionGene> genes) {
        this.trainingRoom = trainingRoom;
        this.connectionGenes = genes;
    }

    /**
     * Evaluate the brain with the given inputs
     *
     * @param inputs A double array with the size {@link TrainingRoomSettings#getInputCount()}
     * @return A double array with size {@link TrainingRoomSettings#getOutputCount()} where index i has the output value of the output neuron with id {@link TrainingRoomSettings#getInputCount}+i
     */
    public double[] evaluate(double[] inputs) {
        if (trainingRoom == null) {
            throw new NullPointerException("trainingRoom is null, make sure Organism#initialze is called!");
        }

        if (!buildStructure) {
            for (int i = 0; i < trainingRoom.trainingRoomSettings.getInputCount(); i++) {
                InputNeuron neuron = new InputNeuron();
                inputNeurons.add(neuron);
                neurons.put(i, neuron);
            }

            for (int i = 0; i < trainingRoom.trainingRoomSettings.getOutputCount(); i++) {
                OutputNeuron neuron = new OutputNeuron();
                outputNeurons.add(neuron);
                neurons.put(trainingRoom.trainingRoomSettings.getInputCount() + i, neuron);
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

    /**
     * Initialize the brain, this will make sure it is ready to be used.
     *
     * @param trainingRoom The trainingroom this brain is in.
     * @param organism The organism whose brain this is
     */
    public void initialize(TrainingRoom trainingRoom, Organism organism) {
        if (!trainingRoom.id.equals(trainingRoomId)) {
            throw new IllegalArgumentException(String.format("The given trainingRoomID %s does not match the expected trainingRoomID %s", trainingRoom.id, id));
        }
        if (!organism.id.equals(organismId)) {
            throw new IllegalArgumentException(String.format("The given organismId %s does not match the expected organismId %s", organism.id, organismId));
        }

        this.trainingRoom = trainingRoom;
        this.organism = organism;
    }
}
