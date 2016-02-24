package martymart.engine.models

import java.util.concurrent.ConcurrentHashMap

/**
 * Created by Jeff Registre.
 */
class HashRUs {

    private ConcurrentHashMap<String, Item> items = new ConcurrentHashMap<>()

    public boolean exists(String name){items.containsKey(name)}

    public void addItem(String name, int price){
        if(!items.containsKey(name))
            items.put(name, new Item(name: name, price: price))
    }
}
