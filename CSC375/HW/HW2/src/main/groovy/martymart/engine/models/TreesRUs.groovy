package martymart.engine.models

import martymart.engine.datastore.Tree

/**
 * Created by Jeff Registre.
 */
class TreesRUs {

    private Tree<String, Item> items = new Tree<>()

    public boolean exists(String name){items.get(name) != null }

    public void addItem(String name, int price){
        if(items.get(name) == null)
            items.insert(name, new Item(name: name, price:price))
    }
}
