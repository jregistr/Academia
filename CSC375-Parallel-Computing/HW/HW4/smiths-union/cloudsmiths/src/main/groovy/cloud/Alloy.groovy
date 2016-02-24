package cloud

import java.util.concurrent.ThreadLocalRandom


class Alloy implements Cloneable{

    final int y,x
    final double steelComp, adamantiumComp, vibraniumComp
    final boolean isStatic

    double temperature = 0

    private Alloy(int y, int x, double steelComp, double adamantiumComp, double vibraniumComp, boolean isStatic) {
        this.y = y
        this.x = x
        this.steelComp = steelComp
        this.adamantiumComp = adamantiumComp
        this.vibraniumComp = vibraniumComp
        this.isStatic = isStatic
    }

    static Alloy make(int y, int x){
        double [] dist = makeDistrib()
        new Alloy(y,x, dist[0], dist[1], dist[2], false)
    }

    static Alloy makeExact(int y, int x, double stComp, double adaComp, double vibComp, boolean isSta){
        new Alloy(y,x,stComp,adaComp,vibComp, isSta)
    }

    static Alloy makeStatic(int y, int x, double temp){
        double [] dist = makeDistrib()
        Alloy a = new Alloy(y,x, dist[0], dist[1], dist[2], true)
        a.temperature = temp
        a
    }

    public static double [] makeDistrib(){
        ThreadLocalRandom random = ThreadLocalRandom.current()
        double [] preList = [random.nextInt(101), random.nextInt(101), random.nextInt(100)]
        double sum = preList.sum()
        preList.collect {it/sum}
    }



}
