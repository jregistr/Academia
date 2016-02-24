package martymart.application

import martymart.engine.datastore.Tree
import martymart.engine.models.TreesRUs

import java.util.concurrent.ThreadLocalRandom


/**
 * Main class
 */
class Main {

    public static def main(args){

        TreesRUs treesRUs = new TreesRUs()

        def rand = ThreadLocalRandom.current()

        def rng = {int min, int max ->
            rand.nextInt((max - min) + 1) + min
        }

        def name = {int min, int max ->
            (1..rng(min, max)).inject (""){a, b ->
                a + ('a'..'z')[rng(0,26)]
            }
        }

        def keys = []
        0.upto(10000){keys.add(name(4, 10).toString())}
        keys = keys.toUnique()

        keys.each {x->
           treesRUs.addItem(x.toString(), rng(1, 10000))
        }

    }

}
