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
    private boolean playerStart = true;

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
        getAIs().stream().findFirst().get().saveToFile("ai");
        playerStart = !playerStart;
        if (!playerStart)
            aisTurn();
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
                getReward(field, (TicTacToeAI) getAIs().stream().findFirst().get());
                getAIs().stream().findFirst().get().reset();
                return 1;
            }
        }
        if (!Arrays.asList(field).contains(E)) {
            getReward(field, (TicTacToeAI) getAIs().stream().findFirst().get());
            getAIs().stream().findFirst().get().reset();
            return 2;
        }
        return 0;
    }

    @Override
    public void playAI(Integer action, AIPlayer aiUncast) {
        TicTacToeAI ai = (TicTacToeAI) aiUncast;
        if (field[action] != 0) {
            return;
        }

        Integer[] before = field.clone();
        field[action] = O;

        gui.aiPlays(action);
        getReward(before, ai);
        if (checkWon()) {
            System.out.println("AI WON");
            gui.won("O");
            ai.reset();
        } else if (checkDraw()) {
            gui.draw();
            ai.reset();
        }

    }

    public void getReward(Integer[] before, TicTacToeAI ai) {
        double retAll = 0.0;
        double retLast = 0.0;
        if (checkDraw())
            retAll = 150.0;
        for (Integer[] c : winConditions) {
            Integer[] sNow = new Integer[]{field[c[0]], field[c[1]], field[c[2]]};
            Integer[] sBefore = new Integer[]{before[c[0]], before[c[1]], before[c[2]]};
            long freeNow = Arrays.stream(sNow).filter(n -> n.equals(0)).count();
            long player = Arrays.stream(sNow).filter(n -> n.equals(X)).count();
            long aiBefore = Arrays.stream(sBefore).filter(n -> n.equals(O)).count();
            long aiNow = Arrays.stream(sNow).filter(n -> n.equals(O)).count();

            System.out.println("CALCULATING REWARD(" + Arrays.toString(c) + "): FN: " + freeNow + ", P: " + player + ", AB: " + aiBefore + ", AN: " + aiNow);

            if (freeNow == 1 && aiBefore == 1 && aiNow == 2) {
                retLast += 100.0;
            }
            if (player == 2 && aiBefore == 0 && aiNow == 1) {
                retLast += 150.0;
            }
            if (player == 2 && aiBefore == 0 && aiNow == 0) {
                retLast -= 150.0;
            }
            if (freeNow == 1 && aiBefore == 2 && aiNow == 2) {
                retLast += 160.0;
            }
            if (player == 3) {
                retAll = -200.0;
            }
            if (aiNow == 3) {
                retAll = 200.0;
            }
        }
        if (retAll == 0 && false) {
            ai.rewardExplicitLastAction(retLast);
            gui.setLastActionValue("LAST: " + retLast);
        } else {
            gui.setLastActionValue("LAST: 0.0");

        }
        ai.giveReward(retAll);
        gui.setActionsValue("ALL: " + retAll);
    }
}
