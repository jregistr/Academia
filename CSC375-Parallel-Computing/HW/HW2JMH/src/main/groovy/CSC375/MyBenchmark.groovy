
package CSC375

import martymart.engine.models.HashRUs
import martymart.engine.models.TreesRUs
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State

import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
class MyBenchmark {

  /*  @Benchmark
    @BenchmarkMode([Mode.Throughput, Mode.AverageTime])
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    def testMyTree() {

        TreesRUs store = new TreesRUs()

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

        keys.each {String x->//populate the store
            store.addItem(x, rng(1, 10000))
        }

        def inspiredWriter = new Thread({
            def i = 0
            def rand2 = ThreadLocalRandom.current()

            def rng2 = {int min, int max ->
                rand2.nextInt((max - min) + 1) + min
            }

            def name2 = {int min, int max ->
                (1..rng2(min, max)).inject (""){a, b ->
                    a + ('a'..'z')[rng2(0,26)]
                }
            }

            while (i < 2000){
                i++
                store.addItem(name2(4,10), rng2(1,10000))
            }
        })

        inspiredWriter.start()

        def i  = 0

        while (i < 10000){
            i++
            int index = rng(0, keys.size()-1)
            store.exists(keys[index].toString())
        }

        inspiredWriter.join()
    }*/

    @Benchmark
    @BenchmarkMode([Mode.Throughput, Mode.AverageTime])
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    def testHashy(){
      //  TreesRUs store = new TreesRUs()

        HashRUs store = new HashRUs()

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

        keys.each {String x->//populate the store
            store.addItem(x, rng(1, 10000))
        }

        def inspiredWriter = new Thread({
            def i = 0
            def rand2 = ThreadLocalRandom.current()

            def rng2 = {int min, int max ->
                rand2.nextInt((max - min) + 1) + min
            }

            def name2 = {int min, int max ->
                (1..rng2(min, max)).inject (""){a, b ->
                    a + ('a'..'z')[rng2(0,26)]
                }
            }

            while (i < 2000){
                i++
                store.addItem(name2(4,10), rng2(1,10000))
            }
        })

        inspiredWriter.start()

        def i  = 0

        while (i < 10000){
            i++
            int index = rng(0, keys.size()-1)
            store.exists(keys[index].toString())
        }

        inspiredWriter.join()
    }

}
