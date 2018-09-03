package ai;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Decision<S, A> implements Comparable, Serializable {
    private A action;
    private S situation;
    private Double estimatedValue = 0.0;
    private ArrayList<Double> previouseRewards;

    public Decision(S situation, A action) {
        this.previouseRewards = new ArrayList<>();
        this.situation = situation;
        this.action = action;
    }

    public Double getEstimatedValue() {
        return estimatedValue;
    }

    public A getAction() {
        return action;
    }

    public S getSituation() {
        return situation;
    }

    public void updateEstimation(Double r) {
        previouseRewards.add(r);
        estimatedValue = previouseRewards.stream().mapToDouble(a -> a).sum() / previouseRewards.size();
    }

    private double getValueEstimation() {
        return estimatedValue;
    }

    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public int compareTo(Object obj) {
        Decision<A, S> o = (Decision<A, S>) obj;
        int ret = 0;
        if (estimatedValue > o.getValueEstimation()) {
            ret = -1;
        }
        if (estimatedValue < o.getValueEstimation()) {
            ret = 1;
        }
        return ret;
    }

    @Override
    public String toString() {
        return "S => " + situation + "   A => " + action + "    EV => " + estimatedValue;
    }
}
