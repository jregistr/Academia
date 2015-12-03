import Dijks.MinHeap
import models.Vertex

class BootStrap {

    def init = { servletContext ->

    /*    Vertex zero = new Vertex(url: "A")
        Vertex one = new Vertex(url: "B")
        Vertex two = new Vertex(url: "C")
        Vertex three = new Vertex(url: "D")
        Vertex four = new Vertex(url: "E")
        Vertex five = new Vertex(url: "F")
        Vertex six = new Vertex(url: "G")
        Vertex seven = new Vertex(url: "H")

        zero.addToEdges(weight: 5, linksTo: three)
        zero.addToEdges(weight: 2,linksTo: one)

        one.addToEdges(weight: 7,linksTo: three)
        one.addToEdges(weight: 1, linksTo: two)
        one.addToEdges(weight: 8,linksTo: zero)

        two.addToEdges(weight: 1,linksTo: one)
        two.addToEdges(weight: 5,linksTo: five)

        three.addToEdges(weight: 2,linksTo: five)
        three.addToEdges(weight: 5,linksTo: seven)

        four.addToEdges(weight: 4,linksTo: seven)

        five.addToEdges(weight: 1,linksTo: seven)
        five.addToEdges(weight: 9,linksTo: one)

        six.addToEdges(weight: 2,linksTo: five)
        six.addToEdges(weight: 2,linksTo: four)

        seven.addToEdges(weight: 3,linksTo: six)


        zero.save()
        one.save()
        two.save()
        three.save()
        four.save()
        five.save()
        six.save()
        seven.save()

*/

    /*    Vertex a = new Vertex(url: "A")
        Vertex b = new Vertex(url: "B")
        Vertex c = new Vertex(url: "C")
        Vertex d = new Vertex(url: "D")
        Vertex e = new Vertex(url: "E")
        Vertex f = new Vertex(url: "F")
        Vertex g = new Vertex(url: "G")
       // Vertex h = new Vertex(url: "H")

        a.addToEdges(weight: 5, linksTo: b)
        a.addToEdges(weight: 10,linksTo: c)

        b.addToEdges(weight: 6, linksTo: d)
        b.addToEdges(weight: 3, linksTo: e)

        d.addToEdges(weight: 6, linksTo: f)

        e.addToEdges(weight: 2, linksTo: d)
        e.addToEdges(weight: 2, linksTo: c)
        e.addToEdges(weight: 2, linksTo: g)

        g.addToEdges(weight: 2,linksTo: f)

        a.save(failOnError:true)
        b.save(failOnError:true)
        c.save(failOnError:true)
        d.save(failOnError:true)
        e.save(failOnError:true)
        f.save(failOnError:true)
        g.save(failOnError:true)*/

    }
    def destroy = {
    }
}
