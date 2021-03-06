package ai;

import game.TicTacToeDecision;

import java.util.HashSet;

public class TestMain {
    public static void main(String[] args) {

        TicTacToeDecision d1 = new TicTacToeDecision(new Integer[]{1, 2, 3, 4, 5}, 7);
        TicTacToeDecision d2 = new TicTacToeDecision(new Integer[]{1, 2, 3, 4, 5}, 7);
        TicTacToeDecision d3 = new TicTacToeDecision(new Integer[]{1, 2, 3, 4, 5}, 8);

        d1.updateEstimation(100.0);

        HashSet<TicTacToeDecision> hs = new HashSet<>();

        hs.add(d1);
        hs.add(d2);
        hs.add(d3);

        System.out.println(hs);

//        System.out.println(new Integer[]{1,2,4,3}.hashCode());
//        System.out.println(new Integer[]{1,2,4,3}.hashCode());
//
//        HashSet<Decision<Integer, String>> l = new HashSet<>();
//        ArrayList<Decision<Integer, String>> l2 = new ArrayList<>();
//
//        Decision<Integer, String> d1 = new Decision<>(3, "Left");
//        Decision<Integer, String> d11 = new Decision<>(3, "Left");
//        Decision<Integer, String> d2 = new Decision<>(1, "Left");
//        Decision<Integer, String> d3 = new Decision<>(4, "Up");
//        Decision<Integer, String> d4 = new Decision<>(4, "Down");
//        Decision<Integer, String> d5 = new Decision<>(7, "Back");
//        Decision<Integer, String> d7 = new Decision<>(7, "Back");
//        Decision<Integer, String> d8 = new Decision<>(7, "Up");
//        Decision<Integer, String> d9 = new Decision<>(7, "Left");
//        Decision<Integer, String> d6 = new Decision<>(7, "Backg");
//
//        d5.updateEstimation(100.0);
//        d4.updateEstimation(263.74);
//        d3.updateEstimation(239.74);
//        d2.updateEstimation(273.74);
//        d1.updateEstimation(2333.74);
//        d6.updateEstimation(24344.74);
//        d11.updateEstimation(11.0);
//        System.out.println("d5 " + d5.hashCode());
//        System.out.println("d6 " + d6.hashCode());


    }
}
