package controllers

import Dijks.Graph
import helpers.ReaderController
import models.Edge
import models.Vertex
import models.Flag


class WelcomeController {

   /* def forAll(Vertex vertex){
        def edges = Edge.findAllByVertex(vertex)
        if(edges){
            edges.each {x->
                println vertex
            }
        }
    }*/

    def index() {
       /* Vertex vertex = Vertex.findByUrl("http://en.wikipedia.org/wiki/Hash_table")
        def edges = Edge.findAllByVertex(vertex)
        edges.each {x->
            println vertex.url + " ============ Links to ==========" + x.linksTo.url
            def ender = x.linksTo.edges
            println "ENDER LENGTH:" + ender.size()
            if(!ender){
                println"NOT ENDER"
            }
            ender.each {y->
                println "This"
                println "                      " + y.linksTo.url
            }
        }*/

       /* Vertex temp = new Vertex(url: "AAA")
        temp.save()*/

        def flag = Flag.findByState(Flag.LoadState.done)
        [loaded:flag != null]
    }

    def load(String seed,int amount,int depth){
        println "SEED:" + seed + "\nAMOUNT:" + amount + " DEPTH:" + depth
        ReaderController.doLoad(seed,amount,depth)
       // redirect(controller: 'graph',action: 'index')
        render(contentType: "application/json", text:  "{\"load\" : true}")
    }

    def isDoneLoading(){
        def flag = Flag.findByState(Flag.LoadState.done)
        if(flag != null)
            render(contentType: "application/json", text:  "{\"load\" : true}")
        else
            render(contentType: "application/json", text:  "{\"load\" : false}")
    }

}