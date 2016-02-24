package models

class Edge {

    float weight
    static belongsTo = [vertex:Vertex]
    static hasOne = [linksTo:Vertex]

    static mapping = {linksTo:'linkTo'}

    static constraints = {
        linksTo nullable: false
    }

    @Override
    public String toString() {
        return "\nEdge{linksTo=" + (linksTo != null) ? linksTo.url : "null" + ", weight=" + weight +'}';
    }
}
