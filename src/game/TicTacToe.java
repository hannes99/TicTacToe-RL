package game;

import ai.AIPlayer;
import ai.Environment;
import game.gui.GUIController;

import java.util.ArrayList;
import java.util.Arrays;

public class TicTacToe extends Environment<Integer[], Integer> {

    public static final Integer X = 1;
    public static final Integer O = 2;
    public static final Integer E = 0;
    private static final ArrayList<Integer[]> winConditions = new ArrayList<>() {{
        add(new Integer[]{0, 1, 2});
        add(new Integer[]{2, 5, 8});
        add(new Integer[]{8, 7, 6});
        add(new Integer[]{6, 3, 0});
        add(new Integer[]{0, 4, 8});
        add(new Integer[]{2, 4, 6});
        add(new Integer[]{1, 4, 7});
        add(new Integer[]{3, 4, 5});
    }};

    private Integer[] field = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private Integer startSymbol = X;
    private GUIController gui;

    public TicTacToe(GUIController gui) {
        this.gui = gui;
    }

    public void aisTurn() {
        ArrayList<Integer> avActions = new ArrayList<>();
        for (int i = 0; i < field.length; i++) {
            if (field[i].equals(0))
                avActions.add(i);
        }
        getAIs().stream().findFirst().get().makeMove(field.clone(), avActions.toArray(new Integer[]{}));
    }

    public void reset() {
        field = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        getAIs().stream().findFirst().get().reset();
    }

    private boolean checkWon() {
        for (Integer[] condition : winConditions) {
            if (field[condition[0]].equals(field[condition[1]]) && field[condition[0]].equals(field[condition[2]]) && !field[condition[0]].equals(E)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDraw() {
        return !Arrays.asList(field).contains(E);
    }

    /**
     * @param position
     * @return -2 => lost; -1 => move not possible; 0 => ok; 1 => win; 2 => draw
     */
    public int play(Integer position) {
        if (field[position] != 0) {
            return -1;
        }
        field[position] = X;
        for (Integer[] condition : winConditions) {
            if (field[condition[0]].equals(field[condition[1]]) && field[condition[0]].equals(field[condition[2]]) && !field[condition[0]].equals(E)) {
                getAIs().stream().findFirst().get().giveReward(-500.0);
                getAIs().stream().findFirst().get().reset();
                return 1;
            }
        }
        if (!Arrays.asList(field).contains(E)) {
            getAIs().stream().findFirst().get().giveReward(250);
            getAIs().stream().findFirst().get().reset();
            return 2;
        }
        return 0;
    }

    @Override
    public void playAI(Integer action, AIPlayer ai) {
        if (field[action] != 0) {
            return;
        }
        field[action] = O;
        if (checkWon()) {
            System.out.println("AI WON");
            gui.won("O");
            ai.giveReward(500.0);
            ai.reset();
        }
        if (checkDraw()) {
            gui.draw();
            ai.giveReward(250.0);
            ai.reset();
        }
        gui.aiPlays(action);
    }
}
