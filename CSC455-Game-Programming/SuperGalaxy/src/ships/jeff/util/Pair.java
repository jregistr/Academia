package ships.jeff.util;

/**
 * Didnt want to use javafx pair so here is a basic pair thingy.
 *
 * @param <K> The type of the key.
 * @param <V> The type of the valeue.
 */
public class Pair<K, V> {
    public final K key;
    public final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

}
