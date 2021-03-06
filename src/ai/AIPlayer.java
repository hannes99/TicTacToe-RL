package ai;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public abstract class AIPlayer<S, A> implements Serializable {
    public final Double exploreRatio = 0.0;
    public HashSet<Decision<S, A>> decisions;
    public ArrayList<Decision<S, A>> decisionsMade;
    private Environment<S, A> env;

    public AIPlayer(Environment<S, A> env) {
        decisions = new HashSet<>();
        decisionsMade = new ArrayList<>();
        this.env = env;
        env.addAI(this);
    }

    public Environment getEnv() {
        return env;
    }

    public abstract void makeMove(S s, A[] availableActions);

    public void reset() {
        System.out.println("AI reseted!");
        decisionsMade.clear();
    }

    public void giveReward(double r) {
        final double finalReward = r / decisionsMade.size();
        if (Double.isInfinite(finalReward) || finalReward == 0) {
            System.out.println("AI reward per action would be infi.");
            return;
        }
        System.out.println("AI reward per action: " + finalReward);
        decisionsMade.forEach(d -> d.updateEstimation(finalReward));
    }

    public abstract void saveToFile(String fname);

    public abstract void loadFromFile(String fname);
}
