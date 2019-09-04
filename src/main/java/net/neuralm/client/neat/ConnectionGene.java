package net.neuralm.client.neat;

import net.neuralm.client.neat.nodes.AbstractNode;

import java.util.UUID;

public class ConnectionGene {

    public UUID id;
    public UUID organismId;
    public final int inNodeIdentifier;
    public final int outNodeIdentifier;
    public int innovationNumber;
    public final double weight;
    public final boolean enabled;

    private AbstractNode in;
    private AbstractNode out;

    public ConnectionGene(int inNodeIdentifier, int outNodeIdentifier, double weight, boolean enabled) {
        this.inNodeIdentifier = inNodeIdentifier;
        this.outNodeIdentifier = outNodeIdentifier;
        this.weight = weight;
        this.enabled = enabled;
    }

    public void buildStructure(Organism organism) {
        if (!enabled) return;

        this.in = organism.getNodeFromIdentifier(inNodeIdentifier);
        this.out = organism.getNodeFromIdentifier(outNodeIdentifier);
        this.out.addDependency(this);
    }

    public double getValue() {
        return in.getValue() * weight;
    }
}
