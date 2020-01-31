package net.neuralm.client.neat;

import net.neuralm.client.neat.nodes.AbstractNode;
import net.neuralm.client.neat.nodes.HiddenNode;
import net.neuralm.client.neat.nodes.InputNode;
import net.neuralm.client.neat.nodes.OutputNode;

import java.util.*;

public class Organism {

    private final List<ConnectionGene> connectionGenes;
    private final List<InputNode> inputNodes;
    private final List<OutputNode> outputNodes;
    private List<HiddenNode> hiddenNodes;
    private UUID id;
    private double score;
    private String name;
    private int generation;

    public Organism(List<ConnectionGene> connectionGenes, int inputCount, int outputCount, UUID id, double score, String name, int generation) {
        this(connectionGenes, inputCount, outputCount);
        this.id = id;
        this.score = score;
        this.name = name;
        this.generation = generation;
    }

    public Organism(List<ConnectionGene> connectionGenes, int inputCount, int outputCount) {
        this.connectionGenes = connectionGenes;

        this.inputNodes = new ArrayList<>();
        this.outputNodes = new ArrayList<>();

        for (int i = 0; i < inputCount; i++) {
            InputNode a = new InputNode(i);
            this.inputNodes.add(a);
        }

        for (int i = 0; i < outputCount; i++) {
            OutputNode a = new OutputNode(i + inputCount);
            this.outputNodes.add(a);
        }

        this.initialize();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGeneration() {
        return generation;
    }

    public double getScore() {
        return score;
    }

    public int getInputCount() {
        return inputNodes.size();
    }

    public int getOutputCount() {
        return outputNodes.size();
    }

    public List<ConnectionGene> getConnectionGenes() {
        return Collections.unmodifiableList(connectionGenes);
    }

    /**
     * Initialize the organism so it can be used.
     * This will build the internal brain structure.
     */
    public void initialize() {
        for (ConnectionGene connectionGene : connectionGenes) {
            connectionGene.buildStructure(this);
        }
    }

    /**
     * Evaluate the organism, this will give the internal brain the given inputs and calculate the outputs.
     *
     * @param inputs The inputs for the brain.
     * @return A double array with size of {@link #outputNodes}
     */
    public double[] evaluate(double[] inputs) {
        if (inputs.length != inputNodes.size()) {
            throw new IllegalArgumentException(String.format("inputs length ( %s ) should match inputnodes length ( %s )", inputs.length, inputNodes.size()));
        }

        for (int i = 0; i < inputs.length; i++) {
            inputNodes.get(i).setValue(inputs[i]);
        }

        double[] outputs = new double[outputNodes.size()];

        for (int i = 0; i < outputNodes.size(); i++) {
            outputs[i] = outputNodes.get(i).getValue();
        }

        return outputs;
    }

    AbstractNode getNodeFromIdentifier(int nodeIdentifier) {
        Optional<InputNode> optional = inputNodes.stream().filter(n -> n.nodeIdentifier == nodeIdentifier).findAny();

        if (optional.isPresent()) {
            return optional.get();
        }

        Optional<OutputNode> outputNode = outputNodes.stream().filter(n -> n.nodeIdentifier == nodeIdentifier).findAny();

        if (outputNode.isPresent()) {
            return outputNode.get();
        }

        if(hiddenNodes == null) {
            hiddenNodes  = new ArrayList<>();
        }

        return hiddenNodes.stream().filter(n -> n.nodeIdentifier == nodeIdentifier).findAny().orElseGet(
                () -> createAndAddNode(nodeIdentifier)
        );
    }

    private HiddenNode createAndAddNode(int nodeIdentifier) {
        HiddenNode node = new HiddenNode(nodeIdentifier);

        hiddenNodes.add(node);

        return node;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
