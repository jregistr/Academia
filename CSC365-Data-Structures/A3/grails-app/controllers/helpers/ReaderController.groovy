package helpers

import models.Edge
import models.Flag
import models.Site
import models.Vertex
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.AbstractMap.SimpleEntry

class ReaderController {

    def index() {

    }

    def parse(String url){
        def parsed = parsePage(url)
        def freq = frequencyList(parsed.words)
        def compiled = ""
        freq.each {x->
            compiled += x.getKey() + "," + x.getValue() + "<br>"
        }
        render(contentType: "text/html", text:  compiled)
    }

    def distance(String first, String second){
        def firstParsed = parsePage(first)
        def secondParsed = parsePage(second)
        render("Distance:" + compare(firstParsed.freqList,secondParsed.freqList))
    }

    public static double compare(ArrayList<SimpleEntry<String,Integer>> firstFreq, ArrayList<SimpleEntry<String,Integer>> secFreq){
        float numerator = 0
        float firstInner = 0
        float secondInner = 0
        //inner product of first with second
        firstFreq.each {x->
            def exist = secFreq.find {y->y.getKey().equals(x.getKey())}
            if(exist != null)
                numerator += x.getValue() * exist.getValue()
        }

        //inner product of first with itself
        firstFreq.each {x->
            firstInner += x.getValue() * x.getValue()
        }
        //inner product of second with itself
        secFreq.each {x->
            secondInner += x.getValue() * x.getValue()
        }
        Math.acos(numerator/Math.sqrt(firstInner * secondInner))
    }

    /*public static double compare(Collection<String> first, Collection<String> second){
        def firstFreq = frequencyList(first)
        def secFreq = frequencyList(second)
        compare(firstFreq,secFreq)
    }*/

    /**
     *
     * @param list - list of words where the words may or may not repeat
     * @return returns an arraylist of SimpleEntry key value pairs paring together a word and its frequency in the array
     */
    public static ArrayList<SimpleEntry<String,Integer>> frequencyList(Collection<String> list){
        if(list != null && list.size() > 0){
            ArrayList<SimpleEntry<String,Integer>> entries = new ArrayList<>()
            list.each {x->
                def lowered = x.toLowerCase()
                if(entries.find {y->y.getKey().equals(lowered)} == null)
                    entries.add(new SimpleEntry<String, Integer>(lowered,list.findAll {y->y.toLowerCase().equals(lowered)}.size()))
            }
            (entries.size() > 0) ? entries : null
        }else {
            return null
        }
    }

    /**
     *
     * @param uri - link to connect to and retrieve document
     * @return returns an array containing all the words in the page
     */
    public static Site parsePage(String uri){
        Document document
        try{
            document = Jsoup.connect(uri).get()
        }catch (Exception e){
            document = null
        }
        if(document != null){
            String stripped
            try{
                stripped = Jsoup.parse(document.toString()).text()
            }catch (Exception e){
                stripped = null
            }
            if(stripped != null && !stripped.isEmpty()) {
                def links = document.select("a[href]")
                def title = document.select("title")

                Pattern regex = Pattern.compile("([A-Za-z])+")
                Matcher matcher = regex.matcher(stripped)
                ArrayList<String> words = new ArrayList<>()

                Site site = new Site(url: uri, title: title, links: new ArrayList<String>(), words: new ArrayList<String>())

                links.each { x ->
                    def abs = x.attr("abs:href")
                    if (abs != null && (!abs.isEmpty()) && abs.length() > 28 && abs.substring(0, 28).equals("http://en.wikipedia.org/wiki")) {
                        if (!abs.find(uri)) {
                            int ind = abs.indexOf("#")
                            String u = ((ind == -1) ? abs : abs.substring(0, ind))
                            if (site.links.find { y -> y.equals(u) } == null)
                                site.links.add(u)
                        }
                    }
                }

                matcher.each { x ->
                    String group = matcher.group()
                    if (group != null && !group.isEmpty())
                        words.add(group)
                }
                site.words = words
                site.freqList = frequencyList(words)
                site
            }else {
                return null
            }
        }else
            return null
    }

    public static def doLoad(String seed,int amount,int depth){
        if(seed.find("http://en.wikipedia.org/wiki")){
            ArrayList<Site> currents = new ArrayList<>()
            ArrayList<Site> nextSites = new ArrayList<>()
            Site seedParsed = parsePage(seed)
            currents.add(seedParsed)
            int count = 0
            Random random = new Random()
            while (count < amount){
                currents.each {x->
                    if(x && x.words && x.freqList && x.links){
                        Vertex vertex = (Vertex.findByUrl(x.url)) ? Vertex.findByUrl(x.url) : new Vertex(url: x.url,title: x.title)
                        vertex.save(failOnError: true)

                        ArrayList<String> links = new ArrayList<>()
                        for(int i = 0; i < depth; i ++){
                            def picked = x.links[random.nextInt(x.links.size())]
                            if(!links.find {z->z.equals(picked)}){
                                links.add(picked)
                            }
                        }

                        if(links){
                            links.each {y->
                                if(count < amount){
                                    Site yParsed = parsePage(y)
                                    if(yParsed){
                                        Vertex link = (Vertex.findByUrl(yParsed.url)) ? Vertex.findByUrl(yParsed.url) : new Vertex(url: yParsed.url,title: yParsed.title)
                                        link.save(failOnError: true)
                                        def testEdge = Edge.findByVertexAndLinksTo(vertex,link)
                                        if(!testEdge){
                                            double sim = compare(x.freqList,yParsed.freqList)
                                            vertex.addToEdges(weight: sim, linksTo: link)
                                            link.save(failOnError:true)
                                            count ++
                                            nextSites.add(yParsed)
                                            //println "Added:" + link.url
                                            println "Count:" + count
                                        }else {
                                            println "==================>>>>> " + testEdge.vertex + "====>" + testEdge.linksTo + "====>::" + testEdge.weight
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                currents.clear()
                currents.addAll(nextSites)
                nextSites.clear()
            }
            Flag flag = new Flag(state: Flag.LoadState.done)
            flag.save()
        }
    }

   /* private static void makeFile(){
        def verts = Vertex.getAll()
        verts.each {x->
            println("=================================================\n")
            if(x.edges != null && x.edges.size() > 0){
                x.edges.each {y->
                    println(x.url + "----------------> " + y.linksTo.url + ":" + y.weight + "\n")
                }
            }else {
                println(x.url + "----------------------> NULL\n")
            }
           println("\n")
        }
    }*/

    private static boolean linkExists(String uriOne, String uriTwo){
        def vert = Vertex.findByUrl(uriOne)
        if(vert != null && vert.edges != null && vert.edges.size() > 0){
            def connect = vert.edges.find {x->x.linksTo.url.equals(uriTwo)}
            return connect != null
        }else
            false
    }

}
