package ai;

import java.io.Serializable;
import java.util.HashSet;

public abstract class Environment<S, A> implements Serializable {
    private HashSet<AIPlayer<S, A>> existingAIs = new HashSet<>();

    public abstract void playAI(A action, AIPlayer ai);

    public HashSet<AIPlayer<S, A>> getAIs() {
        return existingAIs;
    }

    public void addAI(AIPlayer<S, A> a) {
        existingAIs.add(a);
    }

}
