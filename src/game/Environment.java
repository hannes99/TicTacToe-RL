package game;

import ai.AIPlayer;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Environment {

    public static final boolean AI = true;
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
    }};
    private static Environment instance = null;
    private Integer[] field = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private Integer startSymbol = X;
    private Integer current;
    private boolean start = true;

    public static Environment getInstance() {
        if (instance == null) {
            instance = new Environment();
        }
        return instance;
    }

    public Integer getCurrentPlayer() {
        return start ? startSymbol : current;
    }

    public Integer[] getField() {
        return field.clone();
    }

    public void reset() {
        startSymbol = startSymbol.equals(X) ? O : X;
        field = new Integer[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        start = true;
    }

    /**
     * @param position
     * @return -2 => lost; -1 => move not possible; 0 => ok; 1 => win; 2 => draw
     */
    public Integer[] play(Integer position, boolean human) {
        if (start) {
            current = startSymbol;
            start = false;
        }
        Integer[] ret = {-2, -1};
        if (field[position] != 0) {
            ret[0] = -2;
            return ret;
        }
        field[position] = current;
        current = current.equals(X) ? O : X;
        for (Integer[] condition : winConditions) {
            if (field[condition[0]].equals(field[condition[1]]) && field[condition[0]].equals(field[condition[2]]) && !field[condition[0]].equals(E)) {
                ret[0] = 1;
                if (human)
                    AIPlayer.getInstance().lost();
                else
                    AIPlayer.getInstance().won();
                return ret;
            }
        }
        if (!Arrays.asList(field).contains(E)) {
            System.out.println("draw weil " + Arrays.toString(field));
            ret[0] = 2;
            if (human)
                AIPlayer.getInstance().draw();
            else
                AIPlayer.getInstance().draw();
            return ret;
        }
        if (AI && human) {
            ArrayList<Integer> avActions = new ArrayList<>();
            for (int i = 0; i < field.length; i++) {
                if (field[i].equals(0)) {
                    avActions.add(i);
                }
            }
            Integer[] aiStatus = AIPlayer.getInstance().makeMove(field, avActions.toArray());
            ret[1] = aiStatus[1];
            if (aiStatus[0].equals(1)) {
                ret[0] = -2;
            }
        }
        return ret;
    }
}
