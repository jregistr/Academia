package Dijks

class DisjointSets {

    private ArrayList<HashSet<String>> sets;

    static constraints = {
    }

    public DisjointSets(){
        sets = new ArrayList<>()
    }

    public void createSet(String element){
        HashSet<String> set = new HashSet<>()
        set.add(element)
        sets.add(set)
    }

    public void union(int firstIndex, int secIndex){
        if(firstIndex != -1 && secIndex != -1){
            HashSet<String> fSet = sets.get(firstIndex)
            HashSet<String> sSet = sets.get(secIndex)
            sets.remove(fSet)
            sets.remove(sSet)
            HashSet<String> unioned = new HashSet<>()
            fSet.each {x->
                if(x)
                    unioned.add(x)
            }
            sSet.each {x->
                if(x)
                    unioned.add(x)
            }
            sets.add(unioned)
        }
    }

    public int findSet(String element){
        for(int i = 0; i < sets.size(); i ++){
            def set = sets.get(i)
            if(set && set.contains(element)){
                return i
            }
        }
        -1
    }

    public int setCount(){
        sets.size()
    }

}
