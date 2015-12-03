package com.jeff.heatingmetals.util

import java.util.concurrent.ThreadLocalRandom


class Alloy {

    double steelCompotion
    double adamantiumCompotion
    double vibraniumCompotion

    boolean isStatic = false
    double temperature = 0


    private static double  [] getComposition(){
        ThreadLocalRandom random = ThreadLocalRandom.current()
        float[] preList = [random.nextInt(101), random.nextInt(101), random.nextInt(100)]
        float sum = preList.sum()
        preList.collect {it/sum}
    }

    public static Alloy makeAlloy(){
        float [] comp = getComposition()
        new Alloy(steelCompotion: comp[0], adamantiumCompotion: comp[1], vibraniumCompotion: comp[2])
    }

}
