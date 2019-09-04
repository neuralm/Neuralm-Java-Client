package net.neuralm.client.neat;

import java.util.Random;
import java.util.UUID;

public class TrainingRoomSettings {

    static final Random random = new Random();

    private UUID id = UUID.randomUUID();
    private int organismCount = 100;
    private int inputCount = 3;
    private int outputCount = 1;
    private double speciesExcessGeneWeight = 1;
    private double speciesDisjointGeneWeight = 0.4;
    private double speciesAverageWeightDiffWeight = 0.4;
    private double threshold = 3;
    private double addConnectionChance = 0.05;
    private double addNodeChance = 0.03;
    private double crossOverChance = 0.75;
    private double interSpeciesChance = 0.001;
    private double mutationChance = 1;
    private double mutateWeightChance = 0.8;
    private double weightReassignChance = 0.1;
    private double topAmountToSurvive = 0.2;
    private double enableConnectionChance = 0.25;
    private int seed = random.nextInt();

    public UUID getId() {
        return id;
    }

    public TrainingRoomSettings setId(UUID id) {
        this.id = id;
        return this;
    }

    public int getOrganismCount() {
        return organismCount;
    }

    public TrainingRoomSettings setOrganismCount(int organismCount) {
        this.organismCount = organismCount;
        return this;
    }

    public int getInputCount() {
        return inputCount;
    }

    public TrainingRoomSettings setInputCount(int inputCount) {
        this.inputCount = inputCount;
        return this;
    }

    public int getOutputCount() {
        return outputCount;
    }

    public TrainingRoomSettings setOutputCount(int outputCount) {
        this.outputCount = outputCount;
        return this;
    }

    public double getSpeciesExcessGeneWeight() {
        return speciesExcessGeneWeight;
    }

    public TrainingRoomSettings setSpeciesExcessGeneWeight(double speciesExcessGeneWeight) {
        this.speciesExcessGeneWeight = speciesExcessGeneWeight;
        return this;
    }

    public double getSpeciesDisjointGeneWeight() {
        return speciesDisjointGeneWeight;
    }

    public TrainingRoomSettings setSpeciesDisjointGeneWeight(double speciesDisjointGeneWeight) {
        this.speciesDisjointGeneWeight = speciesDisjointGeneWeight;
        return this;
    }

    public double getSpeciesAverageWeightDiffWeight() {
        return speciesAverageWeightDiffWeight;
    }

    public TrainingRoomSettings setSpeciesAverageWeightDiffWeight(double speciesAverageWeightDiffWeight) {
        this.speciesAverageWeightDiffWeight = speciesAverageWeightDiffWeight;
        return this;
    }

    public double getThreshold() {
        return threshold;
    }

    public TrainingRoomSettings setThreshold(double threshold) {
        this.threshold = threshold;
        return this;
    }

    public double getAddConnectionChance() {
        return addConnectionChance;
    }

    public TrainingRoomSettings setAddConnectionChance(double addConnectionChance) {
        this.addConnectionChance = addConnectionChance;
        return this;
    }

    public double getAddNodeChance() {
        return addNodeChance;
    }

    public TrainingRoomSettings setAddNodeChance(double addNodeChance) {
        this.addNodeChance = addNodeChance;
        return this;
    }

    public double getCrossOverChance() {
        return crossOverChance;
    }

    public TrainingRoomSettings setCrossOverChance(double crossOverChance) {
        this.crossOverChance = crossOverChance;
        return this;
    }

    public double getInterSpeciesChance() {
        return interSpeciesChance;
    }

    public TrainingRoomSettings setInterSpeciesChance(double interSpeciesChance) {
        this.interSpeciesChance = interSpeciesChance;
        return this;
    }

    public double getMutationChance() {
        return mutationChance;
    }

    public TrainingRoomSettings setMutationChance(double mutationChance) {
        this.mutationChance = mutationChance;
        return this;
    }

    public double getMutateWeightChance() {
        return mutateWeightChance;
    }

    public TrainingRoomSettings setMutateWeightChance(double mutateWeightChance) {
        this.mutateWeightChance = mutateWeightChance;
        return this;
    }

    public double getWeightReassignChance() {
        return weightReassignChance;
    }

    public TrainingRoomSettings setWeightReassignChance(double weightReassignChance) {
        this.weightReassignChance = weightReassignChance;
        return this;
    }

    public double getTopAmountToSurvive() {
        return topAmountToSurvive;
    }

    public TrainingRoomSettings setTopAmountToSurvive(double topAmountToSurvive) {
        this.topAmountToSurvive = topAmountToSurvive;
        return this;
    }

    public double getEnableConnectionChance() {
        return enableConnectionChance;
    }

    public TrainingRoomSettings setEnableConnectionChance(double enableConnectionChance) {
        this.enableConnectionChance = enableConnectionChance;
        return this;
    }

    public int getSeed() {
        return seed;
    }

    public TrainingRoomSettings setSeed(int seed) {
        this.seed = seed;
        return this;
    }
}
