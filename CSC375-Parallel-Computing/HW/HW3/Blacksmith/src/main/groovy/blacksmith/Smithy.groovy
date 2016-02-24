package blacksmith

import javafx.scene.paint.Color

import static groovyx.gpars.GParsPool.*

class Smithy {

    private final Alloy[][] alloys;
  //  private final Alloy[][] history;
    public static double maxTemp
    public static double lct, rct
    private final int xLen
    private final int yLen

    Smithy(int xLen, int yLen, double tL, double bR) {
        this.xLen = xLen
        this.yLen = yLen
        alloys = new Alloy[yLen][xLen]
       // history = new Alloy[yLen][xLen]
        maxTemp = tL > bR ? tL : bR
        lct = tL
        rct = bR

        0.upto(yLen -1){y->
            0.upto(xLen -1){x->
                if(y == 0 && x ==0){//Top left
                    Alloy temp = Alloy.makeStaticAlloy(x,y,tL)
                    alloys[y][x] = temp
                }else if(y == yLen-1 && x == xLen - 1){//bottom right
                    Alloy temp = Alloy.makeStaticAlloy(x,y,bR)
                    alloys[y][x] = temp
                }else {//everything else
                    Alloy temp = Alloy.makeAlloy(x,y)
                    alloys[y][x] = temp
                }
            }
        }
    }

    void execute(){

        Alloy[][] current = this.alloys

        final int totalCells = alloys.length * alloys[0].length
        final int cpuCount = Runtime.runtime.availableProcessors()
        final int taskCount = 2 * cpuCount
        println "$taskCount"
        final int cellsPer = totalCells/taskCount

        double convergenceThresh = 0.0001
        int maxIterations = 1000000

        double highestDif = Double.MAX_VALUE
        int curIter = 0
        def clone = {
            Alloy[][] temp = new Alloy[current.length][current[0].length]
            current.each {Alloy[] array->
                array.each {Alloy alloy->
                    temp[alloy.y][alloy.x] = alloy.clone()
                }
            }
            temp
        }

        Alloy[][] historic = clone()

        def swap = {
            def temp = current
            current = historic
            historic = temp
        }

        withPool(taskCount){
            while (curIter < maxIterations && highestDif > convergenceThresh){
                highestDif = runForkJoin(current, historic){Alloy[][] workLoad, Alloy[][] reference ->
                    if(workLoad.length * workLoad[0].length > cellsPer && workLoad[0].length >= 2){//we have too much work, split it up
                        Tuple2<Alloy[][],Alloy[][]> parted = partition(workLoad)
                        [parted.first, parted.second].each {forkOffChild(it, reference)}
                        List<Double> vals = childrenResults
                        return vals.max()
                    }else {
                        calcHighestDifference(workLoad, reference)
                    }
                }
                swap()
                curIter ++
            }
            println "Converged!!!!!"
        }
    }

    public double calcHighestDifference(Alloy[][] partition, Alloy[][] reference){
        double highestDif = Double.NEGATIVE_INFINITY
        partition.each {Alloy[] array->
            array.each {Alloy alloy ->
                if(!(alloy.y == 0 && alloy.x == 0) && !(alloy.y == (yLen-1) && alloy.x == (xLen-1))){
                    double alloyTemp = calculateTempForAlloy(alloy, reference)
                    alloyTemp = Math.min(alloyTemp, maxTemp)
                    int y = alloy.y,
                        x = alloy.x
                    double diff = Math.abs(reference[y][x].temperature - alloyTemp)
                    if(diff > highestDif)
                        highestDif = diff
                    alloy.temperature = alloyTemp
                    updateGUI(y,x,alloyTemp)
                }
            }
        }
        highestDif
    }

    private double calculateTempForAlloy(Alloy alloy, Alloy[][] reference){
        Alloy[] neighBors = findNeighbors(alloy, reference)
        int nCount = neighBors.size()

        double steelTemp = calculateTempForMetal(Metal.STEEL, neighBors)
        double adaTemp = calculateTempForMetal(Metal.ADAMANTIUM, neighBors)
        double vibTemp = calculateTempForMetal(Metal.VIBRANIUM, neighBors)

        steelTemp *= Metal.STEEL.thermalConst
        adaTemp *= Metal.ADAMANTIUM.thermalConst
        vibTemp *= Metal.VIBRANIUM.thermalConst

        steelTemp /= nCount
        adaTemp /= nCount
        vibTemp /= nCount

        steelTemp + adaTemp + vibTemp
    }

    private Alloy[] findNeighbors(Alloy alloy, Alloy[][] reference){
        def neiCords = neighborCords(alloy.y, alloy.x)
        List<Alloy> out = []
        neiCords.each {cord->
           out.add reference[cord.first][cord.second]
        }
        out
    }

    private static double calculateTempForMetal(Metal metal, Alloy[] neighbors){
        double theTotal = 0
        neighbors.each {Alloy nei ->
            theTotal += nei.temperature * getCompositionInAlloy(metal, nei)
        }
        theTotal
    }

    private static double getCompositionInAlloy(Metal metal, Alloy alloy){
        if(metal == Metal.STEEL)
            alloy.steelCompotion
        else if(metal == Metal.ADAMANTIUM)
            alloy.adamantiumCompotion
        else
            alloy.vibraniumCompotion
    }

    private List<Tuple2<Integer,Integer>> neighborCords (int y, int x){
        List<Tuple2<Integer,Integer>> list = [
               /* new Tuple2<Integer,Integer>(y - 1, x - 1),
                new Tuple2<Integer,Integer>(y - 1, x    ),
                new Tuple2<Integer,Integer>(y - 1, x + 1),

                new Tuple2<Integer,Integer>(y + 1, x - 1),
                new Tuple2<Integer,Integer>(y + 1, x    ),
                new Tuple2<Integer,Integer>(y + 1, x + 1),*/

                new Tuple2<Integer,Integer>(y + 1, x    ),
                new Tuple2<Integer,Integer>(y - 1, x    ),
                new Tuple2<Integer,Integer>(y    , x + 1),
                new Tuple2<Integer,Integer>(y    , x - 1),
        ]

        list.findAll{
            it.get(0) >= 0 && it.get(0) < alloys.length &&

             it.get(1) >= 0 && it.get(1) < alloys[0].length
        }

    }

    public static Tuple2<Alloy[][],Alloy[][]> partition(Alloy[][] cells){
        final int yLen = cells.length;
        final int xLen = cells[0].length

        if(xLen >= 2){
            int fxLen = xLen/2
            int sxLen = xLen - fxLen

            Alloy[][] first = new  Alloy[yLen][fxLen]
            Alloy[][] second = new Alloy[yLen][sxLen]

            0.upto(yLen-1){y->
                0.upto(fxLen-1){x->
                    first[y][x] = cells[y][x]
                }

                0.upto(sxLen-1){x->
                    second[y][x] = cells[y][x + fxLen]
                }
            }
            new Tuple2<Alloy[][], Alloy[][]>(first, second)
        }else
            throw new IllegalArgumentException("Too small to split")
    }

    public static Color calcColor(double temp){
        double ratio = temp/maxTemp
        if(ratio < 0.05){
            new Color(1,1,1,1)
        }else if(ratio < 0.1){
            new Color(121.0/255, 254.0/255, 254.0/255, 1)
        }else if(ratio < 0.3){
            new Color(0/255, 254.0/255, 121/255, 1)
        }else if(ratio < 0.5){
            new Color(254.0/255, 254.0/255,121.0/255, 1)
        }else if(ratio < 0.8){
            new Color(254.0/255, 254.0/255, 0.0/255, 1)
        }else {
            new Color(1, 0, 0, 1)
        }
    }

    public static double leftCornerTemp(){
        return lct
    }

    public static double rightCornerTemp(){
        return rct
    }

    public static void updateGUI(int y, int x, double temp){
        GUIMan.instance.addGUIUpdate(y,x, calcColor(temp))
    }

}




















