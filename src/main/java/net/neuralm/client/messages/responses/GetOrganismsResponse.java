package net.neuralm.client.messages.responses;

import java.util.List;
import net.neuralm.client.neat.Organism;

public class GetOrganismsResponse extends Response {

    private List<Organism> organisms;

    public List<Organism> getOrganisms() {
        return organisms;
    }

}
