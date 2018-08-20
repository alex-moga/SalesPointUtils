package ru.amogirevskiy.utils.generator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {
   public static LocalDateTime generateRandomDate(LocalDateTime minValue, LocalDateTime maxValue) {
        long random = ThreadLocalRandom.current().nextLong(
                minValue.toEpochSecond(ZoneOffset.UTC),
                maxValue.toEpochSecond(ZoneOffset.UTC));

        return LocalDateTime.ofEpochSecond(random, 0, ZoneOffset.UTC);
    }

    public static int generateRandomInt(int maxValue) {
        return ThreadLocalRandom.current().nextInt(maxValue);
    }

    public static float generateRandomSum(float minValue, float maxValue) {
        Random rand = new Random();
        float value = minValue + rand.nextFloat() * (maxValue - minValue);
        return Math.round(value * 100f) / 100f;
    }
}
