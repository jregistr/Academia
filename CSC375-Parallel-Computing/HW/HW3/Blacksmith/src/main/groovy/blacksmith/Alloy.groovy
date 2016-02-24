package blacksmith

import java.util.concurrent.ThreadLocalRandom


class Alloy implements Cloneable{

    final double steelCompotion
    final double adamantiumCompotion
    final double vibraniumCompotion

    final boolean isStatic
    double temperature

    final int x
    final int y

    Alloy(int x, int y, double steelCompotion, double adamantiumCompotion, double vibraniumCompotion) {
        this.x = x
        this.y = y
        this.steelCompotion = steelCompotion
        this.adamantiumCompotion = adamantiumCompotion
        this.vibraniumCompotion = vibraniumCompotion
        isStatic = false
        temperature = 0
    }

    Alloy(int x, int y, double steelCompotion, double adamantiumCompotion, double vibraniumCompotion, double temperature) {
        this.x = x
        this.y = y
        this.steelCompotion = steelCompotion
        this.adamantiumCompotion = adamantiumCompotion
        this.vibraniumCompotion = vibraniumCompotion
        this.isStatic = true
        this.temperature = temperature
    }

    private static double  [] getComposition(){
        ThreadLocalRandom random = ThreadLocalRandom.current()
        float[] preList = [random.nextInt(101), random.nextInt(101), random.nextInt(100)]
        float sum = preList.sum()
        preList.collect {it/sum}
    }

    public static Alloy makeAlloy(int x, int y){
        float [] comp = getComposition()
        new Alloy(x, y, comp[0], comp[1], comp[2])
    }

    public static Alloy makeStaticAlloy(int x, int y, double temp){
        float [] comp = getComposition()
        new Alloy(x, y, comp[0], comp[1], comp[2], temp)
    }

}
