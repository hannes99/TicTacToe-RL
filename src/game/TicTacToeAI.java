package game;

import ai.AIPlayer;
import ai.Decision;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class TicTacToeAI extends AIPlayer<Integer[], Integer> {

    public TicTacToeAI(TicTacToe env) {
        super(env);
    }

    public void rewardExplicitLastAction(double r) {
        if (decisionsMade.size() == 0)
            return;
        decisionsMade.get(decisionsMade.size() - 1).updateEstimation(r);
    }

    @Override
    public void makeMove(Integer[] s, Integer[] availableActions) {
        System.out.println("situation: " + Arrays.toString(s));
        System.out.println("actions: " + Arrays.toString(availableActions));
        Random r = new Random();
        for (Integer a : availableActions) {
            decisions.add(new TicTacToeDecision(s, a));
        }
        decisions.forEach(System.out::println);
        System.out.println("size of decs.: " + decisions.size());
        int toSkip = 0;
        if (r.nextDouble() < exploreRatio && availableActions.length > 1) {
            toSkip = Math.abs(r.nextInt(availableActions.length) - 1);
        }
        //TODO: if there are many with same EV, take random of those
        TicTacToeDecision decisionMade = (TicTacToeDecision) decisions.stream()
                .filter(d -> Arrays.equals(d.getSituation(), s) && Arrays.asList(availableActions).contains(d.getAction()))
                .skip(toSkip)
                .sorted()
                .findFirst()
                .get();
        decisionsMade.add(decisionMade);
        getEnv().playAI(decisionMade.getAction(), this);
    }

    @Override
    public void saveToFile(String fname) {
        try {
            FileOutputStream fos = new FileOutputStream(fname + ".ais");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            reset();
            oos.writeObject(decisions);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromFile(String fname) {
        try {
            // read object from file
            FileInputStream fis = new FileInputStream(fname + ".ais");
            ObjectInputStream ois = new ObjectInputStream(fis);
            decisions = (HashSet<Decision<Integer[], Integer>>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            System.out.println("NO SAVE-FILE FOUND");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
