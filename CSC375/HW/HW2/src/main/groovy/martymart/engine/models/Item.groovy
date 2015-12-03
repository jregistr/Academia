package martymart.engine.models


/**
 * Created by Jeff Registre. */
class Item {
    String name
    float price

    String toString(){
        return "$name costs $price dollars"
    }
}
