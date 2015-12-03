package models

class Vertex implements Comparable<Vertex>{

    String url;
    String title

    String backtraceUrl = null
    double dijksDistance
    boolean visited = false
    int posInHeap

    static hasMany = [edges:Edge]

    static constraints = {
        url unique: true, nullable: false
        title nullable: true
        backtraceUrl nullable: true
    }

    //static transients = ['backtraceUrl','dijksDistance',"visited"]

    //static fetchMode = [edges: 'eager']

    @Override
    public String toString() {
        return "Vertex{" + "url='" + url + '\'' + "\n, edges=" + edges + '}';
    }

    public int compareTo(Vertex o){
        assert o != null
        Double weight = this.dijksDistance
        Double other = o.dijksDistance
        return weight.compareTo(other)
    }

}
