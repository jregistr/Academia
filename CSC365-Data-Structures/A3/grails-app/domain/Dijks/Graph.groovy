package Dijks

import models.Edge
import models.Vertex

class Graph {


    public Graph(){
    }

    public void findPath(String origin){
        buildList()
        MinHeap heap = new MinHeap()
        Vertex current = Vertex.findByUrl(origin)
        if(current != null){
            def currentEdges = Edge.findAllByVertex(current)
            current.dijksDistance = 0
            current.visited = true
            if(currentEdges){
                currentEdges.each {x->
                    if(x){
                        def vert = x.linksTo
                        if(vert){
                            vert.dijksDistance = x.weight
                            vert.backtraceUrl = x.vertex.url
                            vert.visited = false
                            heap.enqueue(vert)
                        }
                    }
                }

                while (!heap.isEmpty()){
                    current = heap.dequeue()
                    if(current != null){
                        current.visited = true
                        currentEdges = Edge.findAllByVertex(current)
                        if(currentEdges){
                            currentEdges.each {x->
                                if(x){
                                    def vert = x.linksTo
                                    if(vert){
                                        if(vert.dijksDistance == Double.POSITIVE_INFINITY){
                                            vert.dijksDistance = current.dijksDistance + x.weight
                                            vert.backtraceUrl = current.url
                                            heap.enqueue(vert)
                                        }else {
                                            if(!vert.visited){
                                                def newDist = current.dijksDistance + x.weight
                                                if(vert.dijksDistance > newDist){
                                                    vert.dijksDistance = newDist
                                                    vert.backtraceUrl = current.url
                                                    heap.update(vert.posInHeap)
                                                }
                                            }
                                        }
                                    }else {
                                        println "an edge has no links to"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int runDFS(){
        String seed = "http://en.wikipedia.org/wiki/Hash_table"
        Vertex vertex = Vertex.findByUrl(seed)
        HashSet<Vertex> visited = new HashSet<>()
        dfs(vertex,visited)
        visited.size()
    }

    private static void dfs(Vertex site,HashSet<Vertex> visited){
        visited.add(site)
        def edges = Edge.findAllByVertex(site)
        if(edges){
            edges.each {x->
                if(x){
                    if(!visited.contains(x)){
                        dfs(x.linksTo,visited)
                    }
                }

            }
        }
    }

    public int kruskals(){
        def all = Vertex.getAll()
        DisjointSets sets = new DisjointSets()
        all.each {x->
            sets.createSet(x.url)
        }

        def edges = Edge.getAll()
        edges.sort {it.weight}
        edges.each {x->
            int findA = sets.findSet(x.vertex.url)
            int findB = sets.findSet(x.linksTo.url)
            if(findA != -1 && findB != -1){
                if(findA != findB){
                    sets.union(findA,findB)
                }
            }else {
                throw new IllegalArgumentException("Sets not found")
            }
        }
        sets.setCount()
    }

/*    public ArrayList<String> backtrace(String destination){
        def vert = Vertex.findByUrl(destination)
        ArrayList<String> steps = new ArrayList<>()
        println vert.dijksDistance
        if(vert){
            while(vert != null){
                steps.add(0,vert.url)
                def back = vert.backtraceUrl
                if(back){
                    vert = Vertex.findByUrl(back)
                }else {
                    vert = null
                }
            }
        }
       steps
    }*/

    public ArrayList<Step> backtrace(String destination){
        def vert = Vertex.findByUrl(destination)
        ArrayList<Step> steps = new ArrayList<>()
        println vert.dijksDistance
        if(vert){
            while(vert != null){
                steps.add(0,new Step(vert.url,vert.dijksDistance))
                def back = vert.backtraceUrl
                if(back){
                    vert = Vertex.findByUrl(back)
                }else {
                    vert = null
                }
            }
        }
        steps
    }

    private void buildList(){
        def verts = Vertex.getAll()
        verts.each {x->
            x.dijksDistance = Double.POSITIVE_INFINITY
            x.backtraceUrl = null
            x.visited = false
            x.posInHeap = -1
        }
    }

   /* private def buildList = {->
        def verts = Vertex.getAll()
        verts.each {x->
            x.dijksDistance = Double.POSITIVE_INFINITY
            x.backtraceUrl = null
            x.visited = false
            x.posInHeap = -1
        }
    }*/


}
