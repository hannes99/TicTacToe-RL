package ai;

import java.util.ArrayList;

public abstract class Decision<S, A> implements Comparable {
    private A action;
    private S situation;
    private Double estimatedValue = 0.0;
    private ArrayList<Double> previouseRewards;

    public Decision(S situation, A action) {
        this.previouseRewards = new ArrayList<>();
        this.situation = situation;
        this.action = action;
    }

    public A getAction() {
        return action;
    }

    public S getSituation() {
        return situation;
    }

    void updateEstimation(Double r) {
        previouseRewards.add(r);
        estimatedValue = previouseRewards.stream().mapToDouble(a -> a).sum() / previouseRewards.size();
    }

    private double getValueEstimation() {
        return estimatedValue;
    }

    @Override
    public boolean equals(Object obj) {
        Decision<A, S> o = (Decision<A, S>) obj;
        return action.equals(o.getAction()) && situation.equals(o.getSituation());
    }

    @Override
    public int hashCode() {
        return (situation.hashCode() + action.hashCode() + "").hashCode();
    }

    @Override
    public int compareTo(Object obj) {
        Decision<A, S> o = (Decision<A, S>) obj;
        int ret = 0;
        if (estimatedValue > o.getValueEstimation()){
            ret = -1;
        }
        if (estimatedValue < o.getValueEstimation()){
            ret = 1;
        }
        return ret;
    }

    @Override
    public String toString() {
        return "S => " + situation +"   A => " + action + "    EV => " + estimatedValue;
    }
}
