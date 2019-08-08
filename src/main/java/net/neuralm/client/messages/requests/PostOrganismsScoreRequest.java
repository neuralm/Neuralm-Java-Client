package net.neuralm.client.messages.requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.neuralm.client.neat.Organism;

public class PostOrganismsScoreRequest extends Request {

    public UUID trainingSessionId;

    public Map<UUID, Double> organismScores;

    /**
     * Create a PostOrganismsScoreRequest to send the organisms' score to the server
     *
     * @param trainingSessionId The trainingsession id
     * @param organismScores A map with the organism's id as key and its score as value
     */
    public PostOrganismsScoreRequest(UUID trainingSessionId, Map<UUID, Double> organismScores) {
        this.trainingSessionId = trainingSessionId;
        this.organismScores = organismScores;
    }

    /**
     * Create a PostOrganismsScoreRequest to send the organisms' score to the server
     *
     * @param trainingSessionId The trainingsession id
     * @param organisms The list of organisms to send
     */
    public PostOrganismsScoreRequest(UUID trainingSessionId, List<Organism> organisms) {
        this(trainingSessionId, new HashMap<>(organisms.size()));

        for (Organism organism : organisms) {
            organismScores.put(organism.id, organism.score);
        }
    }
}
