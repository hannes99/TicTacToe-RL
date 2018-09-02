package ai;

import java.util.ArrayList;
import java.util.HashSet;

public abstract class AIPlayer<S, A> {
    public final Double exploreRatio = 0.1;
    public HashSet<Decision<S, A>> decisions = new HashSet<>();
    public ArrayList<Decision<S, A>> decisionsMade = new ArrayList<>();
    private Environment<S, A> env;

    public AIPlayer(Environment<S, A> env) {
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
        System.out.println("AI reward per action: " + finalReward);
        decisionsMade.forEach(d -> d.updateEstimation(finalReward));
    }
}
