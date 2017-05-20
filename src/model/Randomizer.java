package model;

import java.util.Random;

public class Randomizer {
    private static volatile Randomizer instance;
    private Random randomGenerator = new Random();

    public static Randomizer getInstance() {
        Randomizer localInstance = instance;
        if (localInstance == null) {
            synchronized (Randomizer.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Randomizer();
                }
            }
        }
        return localInstance;
    }

    public Random getRandomGenerator() {
        return randomGenerator;
    }
}
