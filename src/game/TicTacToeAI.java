package game;

import ai.AIPlayer;

import java.util.Arrays;
import java.util.Random;

public class TicTacToeAI extends AIPlayer<Integer[], Integer> {

    public TicTacToeAI(TicTacToe env) {
        super(env);
    }

    @Override
    public void makeMove(Integer[] s, Integer[] availableActions) {
        System.out.println("situation: " + Arrays.toString(s));
        System.out.println("actions: " + Arrays.toString(availableActions));
        Random r = new Random();
        for (Integer a : availableActions) {
            decisions.add(new TicTacToeDecision(s, a));
        }
        System.out.println("size of decs.: " + decisions.size());
        int toSkip = 0;
        if (r.nextDouble() < exploreRatio) {
            toSkip = r.nextInt(availableActions.length) - 1;
        }
        TicTacToeDecision decisionMade = (TicTacToeDecision) decisions.stream()
                .filter(d -> d.getSituation().equals(s) && Arrays.asList(availableActions).contains(d.getAction()))
                .skip(toSkip)
                .sorted()
                .findFirst()
                .get();
        decisionsMade.add(decisionMade);
        getEnv().playAI(decisionMade.getAction(), this);
    }
}
