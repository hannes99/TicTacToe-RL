package ai;

import java.util.ArrayList;

public class Decision<S, A> implements Comparable {
    private A action;
    private S situation;
    private Double estimatedValue = 0.0;
    private ArrayList<Double> previouseRewards;

    Decision(S situation, A action){
        this.previouseRewards = new ArrayList<>();
        this.situation = situation;
        this.action = action;
    }

    A getAction(){
        return action;
    }

    S getSituation(){
        return situation;
    }

    void updateEstimation(Double r) {
        previouseRewards.add(r);
        estimatedValue = previouseRewards.stream().mapToDouble(a -> a).sum() / previouseRewards.size();
    }

    double getValueEstimation(){
        return estimatedValue;
    }

    @Override
    public boolean equals(Object obj) {
        Decision<A, S> o = (Decision<A, S>) obj;
        return action.equals(o.getAction()) && situation.equals(o.getSituation());
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(situation.hashCode()+action.hashCode()+"");
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
