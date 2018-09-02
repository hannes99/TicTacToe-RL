package game;

import ai.Decision;

import java.util.Arrays;

public class TicTacToeDecision extends Decision<Integer[], Integer> {
    public TicTacToeDecision(Integer[] situation, Integer action) {
        super(situation, action);
    }

    @Override
    public int hashCode() {
        StringBuilder s = new StringBuilder();
        for (Integer n : getSituation()) {
            s.append(n);
        }
        s.append(getAction());
        return s.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        TicTacToeDecision o = (TicTacToeDecision) obj;
        return Arrays.equals(getSituation(), o.getSituation()) && getAction().equals(o.getAction());
    }

    @Override
    public String toString() {
        return "S => " + Arrays.toString(getSituation()) + "   A => " + getAction();
    }
}
