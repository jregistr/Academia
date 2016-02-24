package thewatchers


class Server {

    private enum Headers{
        INIT(0),
        UPDATE(1),
        CLOSE(2),

        final int id
        Headers(int id) {
            this.id = id
        }
    }

    private static Server instance

    private ServerSocket serverSocket
    private Socket first
    private Socket second

    private DataInputStream firstInputStream
    private DataOutputStream firstOutputStream

    private DataInputStream secondInputStream
    private DataOutputStream secondOutputStream

    private int firstStart,
    firstEnd,secondStart,secondEnd,height

    private double lct,rct,maxTemp

    private int interate

    private GUIMan guiMan

    private Server() {
    }

    public void create(int portNum, int firstStart, int firstEnd, int secondStart, int secondEnd, int height, double lct, double rct,
                       double maxTemp, GUIMan guiMan){
        this.firstStart = firstStart
        this.firstEnd = firstEnd
        this.secondStart = secondStart
        this.secondEnd = secondEnd
        this.height = height
        this.lct = lct
        this.rct = rct
        this.maxTemp = maxTemp
        this.guiMan = guiMan

       /* println "The first debug:${(firstEnd - firstStart) + 1}"
        println "The first debug:${(secondEnd - secondStart) + 1}"*/

        serverSocket = new ServerSocket(portNum, 2)
        println "Server running on port#:$portNum"
        while (first == null){
            first = serverSocket.accept()
            println "Made connection with:${first.inetAddress.hostName}"
            firstInputStream = new DataInputStream(first.inputStream)
            firstOutputStream = new DataOutputStream(first.outputStream)
           // firstOutputStream.writeInt(1)
            firstOutputStream.flush()

          //  readData()
        }
        println "Received first connection"
        while (second == null){
            second = serverSocket.accept()
            println "Made connection with:${second.inetAddress.hostName}"
            secondInputStream = new DataInputStream(second.inputStream)
            secondOutputStream = new DataOutputStream(second.outputStream)
            secondOutputStream.flush()
        }

        sendInitData()
        sendEmptyEdgeData()

        while (true){
            readData()
        }
    }

    private void readData(){
        int header = firstInputStream.readInt()
      //  println "Server Received Header:$header"
        List<Double> secondLeftEdge = []
        if(header == Headers.UPDATE.id){

            /*int count = height * ((firstEnd - firstStart) + 1)
            1.upto(count){firstList.add(firstInputStream.readDouble())}*/


            int edgeIndex = ((firstEnd - firstStart) + 1) - 1;

            for(int i = 0; i < height; i ++){
                for(int j = 0; j < ((firstEnd - firstStart) + 1); j++){
                    double cur = firstInputStream.readDouble()
                    if(j == edgeIndex){
                        secondLeftEdge.add(cur)
                    }
                    guiMan.addGUIUpdate(i,j,cur)
                }
            }




        }else {
            throw new IllegalArgumentException ("Hiii")
        }

        header = secondInputStream.readInt()
        List<Double> firstRightEdge = []
        if(header == Headers.UPDATE.id){
           /* int count = height * ((secondEnd - secondStart) + 1)
            1.upto(count){secondList.add(secondInputStream.readDouble())}*/

            int edgeIndex = 0;
           // List<Double> firstRightEdge = []
            for(int i = 0; i < height; i ++){
                for(int j = 0; j < ((secondEnd - secondStart) + 1); j++){
                    double cur = secondInputStream.readDouble();
                    if(j == edgeIndex){
                        firstRightEdge.add(cur)
                    }
                    guiMan.addGUIUpdate(i,j + secondStart, cur)
                }
            }


        }
       // sendEmptyEdgeData()
        sendEdgeLists(firstRightEdge, secondLeftEdge)
    }

    private void sendInitData(){
        firstOutputStream.with {
            writeInt(Headers.INIT.id)
            writeInt(firstStart)
            writeInt(firstEnd)
            writeInt(height)
            writeDouble(lct)
            writeDouble(0)
            writeDouble(maxTemp)
            writeBoolean(false)
            writeBoolean(true)
        }
        firstOutputStream.flush()
         secondOutputStream.with {
            writeInt(Headers.INIT.id)
            writeInt(secondStart)
            writeInt(secondEnd)
            writeInt(height)
            writeDouble(0)
            writeDouble(rct)
            writeDouble(maxTemp)
            writeBoolean(true)
            writeBoolean(false)
        }
        secondOutputStream.flush()
    }

    public void sendEmptyEdgeData(){
        firstOutputStream.with {
            writeInt(Headers.UPDATE.id)
            writeBoolean(false)
            writeBoolean(true)
            1.upto(height){writeDouble(0)}
        }

        firstOutputStream.flush()

        secondOutputStream.with {
            writeInt(Headers.UPDATE.id)
            writeBoolean(true)
            1.upto(height){writeDouble(0)}
            writeBoolean(false)
        }
        secondOutputStream.flush()
    }

    public void sendEdgeLists(List<Double> forFirstRight, List<Double> forSecondLeft){
        firstOutputStream.with {
            writeInt(Headers.UPDATE.id)
            writeBoolean(false)
            writeBoolean(true)
            forFirstRight.each {writeDouble(it)}
        }

        firstOutputStream.flush()

        secondOutputStream.with {
            writeInt(Headers.UPDATE.id)
            writeBoolean(true)
            forSecondLeft.each {writeDouble(it)}
            writeBoolean(false)
        }
    }

    private void close(){
        firstInputStream.close()
        firstOutputStream.close()
        first.close()
        secondInputStream.close()
        secondOutputStream.close()
        second.close()
    }

    static Server getInstance() {
        if(instance == null)
            instance = new Server()
        return instance
    }
}
