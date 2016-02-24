package cloud


class Client {

    private enum Headers{
        INIT(0),
        UPDATE(1),
        CLOSE(2),

        final int id
        Headers(int id) {
            this.id = id
        }
    }

    private Forge forge
    private static Client instance
    private Socket connection
    private DataInputStream inputStream
    private DataOutputStream outputStream

    private String address
    private int port

    private Client() {

    }

    public void create(String address, int port){
        this.address = address
        this.port = port
        connect()
    }

    private void connect(){
        println "Attempting to connect"
        connection = new Socket(InetAddress.getByName(address), port)
        println "Connected to:${connection.getInetAddress().getHostName()}"
        inputStream = new DataInputStream(connection.inputStream)
        outputStream = new DataOutputStream(connection.outputStream)
        //outputStream.flush()
        while (true){
           read()
        }
    }

    private void read(){
        int header = inputStream.readInt()
        if(header == Headers.CLOSE.id){
            close()
        }else if(header == Headers.INIT.id){
            inputStream.with {
                forge = new Forge(readInt(), readInt(), readInt(), readDouble(), readDouble(), readDouble(), readBoolean(), readBoolean())
            }
        }else if(header == Headers.UPDATE.id){//Receive info about edges
          //  println "About to read Update DATA!!!!"
            List<Double> left = null
            List<Double> right = null

            if(inputStream.readBoolean()){//we have left data
                left = []
                int count = forge.height
          //      println "Expecting left data, Count is:$count"
                1.upto(count){left.add(inputStream.readDouble())}
            }

            if(inputStream.readBoolean()){
                right = []
                int count = forge.height
                1.upto(count){right.add(inputStream.readDouble())}
            }
            forge.updateEdgeTemps(left as double[], right as double[])
        }else {
            close()
            throw new IllegalArgumentException("Unknown header type")
        }
    }

    public void send(List<Double> temps){
   //     println "I am sending your way:${temps.size()} items"
        outputStream.writeInt(Headers.UPDATE.id)
        temps.each {outputStream.writeDouble(it)}
        outputStream.flush()
    }

    private void close(){
        inputStream.close()
        outputStream.close()
        connection.close()
    }

    static Client getInstance() {
        if(instance == null)
            instance = new Client()
        return instance
    }
}
