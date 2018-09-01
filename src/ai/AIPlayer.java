package ai;

import game.Environment;

import java.util.*;

public class AIPlayer<S, A> {
    private static AIPlayer instance;
    private static Double exploreRatio = 0.1;

    public static AIPlayer getInstance() {
        if (instance == null)
            instance = new AIPlayer<Integer[], Integer>();
        return instance;
    }

    private HashSet<Decision<S, A>> decisions = new HashSet<>();
    private ArrayList<Decision<S, A>> decisionsMade = new ArrayList<>();

    public Integer[] makeMove(S s, A[] availableActions) {
        System.out.println("Available Action => " + Arrays.toString(availableActions));
        Random r = new Random();
        for (A a : availableActions) {
            decisions.add(new Decision<>(s, a));
        }
        int toSkip = 0;
        if (r.nextDouble() < exploreRatio) {
            toSkip = r.nextInt(availableActions.length) - 1;
        }
        Decision<S, A> decisionMade = decisions.stream()
                .filter(d -> d.getSituation().equals(s) && Arrays.asList(availableActions).contains(d.getAction()))
                .skip(toSkip)
                .sorted()
                .findFirst()
                .get();
        decisionsMade.add(decisionMade);
        Integer[] ret = Environment.getInstance().play((Integer) decisionMade.getAction(), false);
        ret[1] = (Integer) decisionMade.getAction();
        return ret;
    }

    public void won(){
        decisionsMade.forEach(d -> d.updateEstimation(100.0));
        decisionsMade.clear();
    }

    public void lost() {
        decisionsMade.forEach(d -> d.updateEstimation(-100.0));
        decisionsMade.clear();
    }

    public void draw() {
        decisionsMade.forEach(d -> d.updateEstimation(20.0));
        decisionsMade.clear();
    }

}
