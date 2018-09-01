package ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.StreamSupport;

public class TestMain {
    public static void main(String[] args) {
        HashSet<Decision<Integer, String>> l = new HashSet<>();
        ArrayList<Decision<Integer, String>> l2 = new ArrayList<>();

        Decision<Integer, String> d1 = new Decision<>(3, "Left");
        Decision<Integer, String> d11 = new Decision<>(3, "Left");
        Decision<Integer, String> d2 = new Decision<>(1, "Left");
        Decision<Integer, String> d3 = new Decision<>(4, "Up");
        Decision<Integer, String> d4 = new Decision<>(4, "Down");
        Decision<Integer, String> d5 = new Decision<>(7, "Back");
        Decision<Integer, String> d7 = new Decision<>(7, "Back");
        Decision<Integer, String> d8 = new Decision<>(7, "Up");
        Decision<Integer, String> d9 = new Decision<>(7, "Left");
        Decision<Integer, String> d6 = new Decision<>(7, "Backg");

        d5.updateEstimation(100.0);
        d4.updateEstimation(263.74);
        d3.updateEstimation(239.74);
        d2.updateEstimation(273.74);
        d1.updateEstimation(2333.74);
        d6.updateEstimation(24344.74);
        d11.updateEstimation(11.0);
        System.out.println("d5 " + d5.hashCode());
        System.out.println("d6 " + d6.hashCode());



        l.add(d1);
        l.add(d2);
        l.add(d3);
        l.add(d4);
        l.add(d5);
        l.add(d6);
        l.add(d7);
        l.add(d8);
        l.add(d9);
        l.add(d11);

        l2.add(d5);

        l2.forEach(d -> d.updateEstimation(900.0));

        String[] legi = {"Left", "Right", "Back"};

        l.stream().filter(a -> a.getSituation().equals(7) && Arrays.asList(legi).contains(a.getAction())).sorted().forEach(System.out::println);
    }
}
