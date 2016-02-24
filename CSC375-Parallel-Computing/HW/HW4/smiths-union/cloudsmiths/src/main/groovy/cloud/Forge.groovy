package cloud

import static groovyx.gpars.GParsPool.*

class Forge {

    private final int xStartLocation,xEndLocation
    public final boolean hasLeftNeighbor,hasRightNeighbor
    private final double maxTemp
    private final int taskCount
    private final int cellsPer
    public final int height
    public final int fullLength

    private Alloy[][] forge
    private Alloy[][] manual

    private double  [][] leftEdge
    private double  [][] rightEdge


    Forge(int xStartLocation, int xEndLocation, int height, double leftCornerTemp, double rightCornerTemp,double maxTemp,
          boolean hasLeftNeighbor,boolean hasRightNeighbor) {
        this.xStartLocation = xStartLocation
        this.xEndLocation = xEndLocation
        println "Forge:$xStartLocation to $xEndLocation"
        this.hasLeftNeighbor = hasLeftNeighbor
        this.hasRightNeighbor = hasRightNeighbor
        //maxTemp = leftCornerTemp > rightCornerTemp ? leftCornerTemp : rightCornerTemp
        this.maxTemp = maxTemp
        this.height = height

        int len = (xEndLocation - xStartLocation) + 1
        fullLength = len
        int totalCells = len * height
        final int cpuCount = Runtime.runtime.availableProcessors()
        taskCount = 2 * cpuCount
        cellsPer = totalCells/taskCount
        makeForge(height, leftCornerTemp, rightCornerTemp)
        initEdges()

        List<Double> list = []
        forge.each {Alloy[] alloys->
            alloys.each {
                list.add(it.temperature)
            }
        }
    }

    private void makeForge(int height, double leftCornerTemp, double rightCornerTemp){
        int horizontalCount = fullLength
        forge = new Alloy[height][horizontalCount]
        manual = new Alloy[height][horizontalCount]


        0.upto(height-1){y->
            0.upto(horizontalCount-1){x->
              //  println "------------------------------------------->>>>>>> $x"
                if(leftCornerTemp > 0 || rightCornerTemp > 0){//there is a corner heat source here
                    if(y == 0 && x == 0 && leftCornerTemp > 0){//Top left
                        addAlloy(y,x, Alloy.makeStatic(y, x + xStartLocation, leftCornerTemp))
                    }else if(y == height-1 && x == horizontalCount-1 && rightCornerTemp > 0){//bottom right
               //         println "I AM RIGHT HERE IN THIS CORNER"
                        addAlloy(y,x, Alloy.makeStatic(y, x + xStartLocation, rightCornerTemp))
                    }else {//everything else
                        addAlloy(y,x, Alloy.make(y,x + xStartLocation))
                    }
                }else {
                    addAlloy(y,x, Alloy.make(y,x + xStartLocation))
                }
            }
        }
    }

    //Edge come as sequence of threes. 0:temp, 1:steel 2:adamantium 3:vibranium
    public void initEdges(){
        if(hasLeftNeighbor){
            List<List<Double>> left = []
            0.upto(forge.length-1){y->
                double [] comp = Alloy.makeDistrib()
                left.add([0, comp[0], comp[1], comp[2]] as List<Double>)
            }
            leftEdge = left as double[][]
        }

        if(hasRightNeighbor){
            List<List<Double>> right = []
            0.upto(forge.length-1){y->
                double [] comp = Alloy.makeDistrib()
                right.add([0, comp[0], comp[1], comp[2]] as List<Double>)
            }
            rightEdge = right as double[][]
        }
    }

    public void updateEdgeTemps(double[] left, double [] right){
        /*if(hasLeftNeighbor && left == null || hasRightNeighbor && right == null)
            throw new IllegalArgumentException("Missing an edge here")*/

        if(left != null && hasLeftNeighbor){
            if(left.length != leftEdge.length){
                throw new IllegalArgumentException("Size mismatch here")
            }

            left.eachWithIndex { double entry, int i ->
                leftEdge[i][0] = entry
            }
        }

        if(right != null && hasRightNeighbor){
            if(right.length != rightEdge.length){
                throw new IllegalArgumentException("Size mismatch here in right")
            }

            right.eachWithIndex { double entry, int i ->
                rightEdge[i][0] = entry
            }
        }

        calculate()

    }

    private void addAlloy(int y, int x, Alloy alloy){
        Alloy clone = (Alloy)alloy.clone()
        forge[y][x] = alloy
        manual[y][x] = clone
    }

    private void swap(){
        Alloy[][] tempForge = forge;
        forge = manual
        manual = tempForge
    }

    private static Tuple2<Tuple2<Integer,Integer>,Tuple2<Integer,Integer>> partition(int from, int to){
        int len = to - from
        int subLen = len/2
        if(len < 2)
            throw new IllegalArgumentException("From:$from -- To:$to")

        int subTo = from + subLen
        new Tuple2<Tuple2<Integer,Integer>, Tuple2<Integer,Integer>>(new Tuple2<Integer, Integer>(from, subTo),
        new Tuple2<Integer, Integer>(subTo + 1, to))
    }

    public double[][] findNeighbors(int y, int x){
        List<List<Double>> neis = []

        if((y - 1) >= 0){
            Alloy temp = manual[y-1][x]
            neis.add([temp.temperature, temp.steelComp, temp.adamantiumComp, temp.vibraniumComp] as List<Double>)
        }

        if(y + 1 < manual.length){
            Alloy temp = manual[y+1][x]
            neis.add([temp.temperature, temp.steelComp, temp.adamantiumComp, temp.vibraniumComp] as List<Double>)
        }

        if(x + 1 < manual[0].length){//we're not reaching out of our bounds, just get this
            Alloy temp = manual[y][x + 1]
            neis.add([temp.temperature, temp.steelComp, temp.adamantiumComp, temp.vibraniumComp] as List<Double>)
        }else {
            if(rightEdge != null){//we have right edge list
                neis.add(rightEdge[y] as List<Double>)
            }
        }

        if(x - 1 >= 0){//we're not reaching out of our bounds, just get this
            Alloy temp = manual[y][x - 1]
            neis.add([temp.temperature, temp.steelComp, temp.adamantiumComp, temp.vibraniumComp] as List<Double>)
        }else {
            if(leftEdge != null){//we have a right edge list
                neis.add(leftEdge[y] as List<Double>)
            }
        }
        neis
    }

    public void calculate(){
        if(hasLeftNeighbor && leftEdge == null || hasRightNeighbor && rightEdge == null)
            throw new IllegalStateException("Supposed to have edge but no edge")

        swap()

        double highestDif = Double.POSITIVE_INFINITY
        withPool (taskCount){
            highestDif = runForkJoin(0, forge[0].length-1){int start, int end ->
                int len = end - start
                int workLoad = len * height
                if(len >= 2){
                    if(workLoad > cellsPer){
                        Tuple2<Tuple2<Integer,Integer>,Tuple2<Integer,Integer>> parts = partition(start, end)
                        [[parts.first.first, parts.first.second], [parts.second.first, parts.second.second]].each {
                            forkOffChild(it[0], it[1])
                        }
                        List<Double> vals = childrenResults
                        return vals.max()
                    }else {
                        calcHighestDifference(start, end)
                    }
                }else {
                    calcHighestDifference(start, end)
                }
            }
        }

     //   println "Done doing these calculations"

        List<Double> list = []
        forge.each {Alloy[] alloys->
            alloys.each {
                list.add(it.temperature)
            }
        }
        Client.instance.send(list)
    }

    private double calcHighestDifference(int from, int to){
        double highestDif = Double.MIN_VALUE
        int height = manual.length
        int horiCount = to - from

        0.upto(height -1){y->
            0.upto(horiCount){x->
                Alloy alloy1 = forge[y][x + from]
                if(!alloy1.isStatic){
                    double alloyTemp = calculateAlloyTemp(y, x + from)
                    alloyTemp = Math.min(alloyTemp, maxTemp)
                    double diff = Math.abs(manual[y][x + from].temperature - alloyTemp)
                    if(diff > highestDif)
                        highestDif = diff
                    alloy1.temperature = alloyTemp
                }
            }
        }
        highestDif
    }

    private double calculateAlloyTemp(int y, int x){
        double [][] neighbors = findNeighbors(y, x)
        int nCount = neighbors.length

        double steelTemp = calcTempForMetal(Metal.STEEL, neighbors)
        double adaTemp = calcTempForMetal(Metal.ADAMANTIUM, neighbors)
        double vibTemp = calcTempForMetal(Metal.VIBRANIUM, neighbors)

        steelTemp *= Metal.STEEL.thermalConst
        adaTemp *= Metal.ADAMANTIUM.thermalConst
        vibTemp *= Metal.VIBRANIUM.thermalConst

        steelTemp /= nCount
        adaTemp /= nCount
        vibTemp /= nCount

        steelTemp + adaTemp + vibTemp
    }

    private static double calcTempForMetal(Metal metal, double[][] neighbors){
        double total = 0
        neighbors.each {double [] array->
            double tt = array[0]
            tt *= findCompositionInNeighbor(metal, array)
            total += tt
        }
        total
    }

    private static double findCompositionInNeighbor(Metal metal, double[] neis){
        if(metal == Metal.STEEL)
            neis[1]
        else if(metal == Metal.ADAMANTIUM)
            neis[2]
        else
            neis[3]
    }

}
