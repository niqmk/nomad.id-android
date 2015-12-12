package com.allega.nomad.controller.encrypt.utils;

import java.util.ArrayList;
import java.util.List;

public class ADZRandom {

    private static int x;
    private static int y;
    private static int z;
    private static int w;

    public ADZRandom() {
        x = ((int) System.currentTimeMillis() % 883208140);
        y = 362436069;
        z = 521288629;
        w = 88675123;
    }

    public ADZRandom(int seed) {
        x = seed;
        y = 362436069;
        z = 521288629;
        w = 88675123;
    }

    public int nextInt() {
        int t;

        t = x ^ (x << 11);
        x = y;
        y = z;
        z = w;
        return w = w ^ (w >> 19) ^ (t ^ (t >> 8));
    }

    public List<Byte> shuffleArray(List<Byte> sourceArray) {
        List<Byte> resultArray = new ArrayList<Byte>();
        int randomValue = nextInt();

        int startPoint = randomValue % sourceArray.size();
        int leftRowIndex = startPoint - 1;
        int rightRowIndex = startPoint + 1;
        for (int i = 0; i < sourceArray.size(); i++) {
            if (i == 0) {
                resultArray.add(sourceArray.get(startPoint));
            } else if (i % 2 == 0) {
                if (leftRowIndex < 0) {
                    leftRowIndex += sourceArray.size();
                }
                resultArray.add(sourceArray.get(leftRowIndex));
                leftRowIndex--;
            } else {
                rightRowIndex = rightRowIndex % sourceArray.size();
                resultArray.add(sourceArray.get(rightRowIndex));
                rightRowIndex++;
            }
        }

        return resultArray;
    }

}
