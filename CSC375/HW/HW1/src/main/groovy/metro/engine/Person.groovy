package metro.engine


/**
 * @author Jeff Registre
 * @since 9/20/2015.
 */
class Person{

    private final def name
    private final def id
    private final def fare

    Person(String name, long id, float fare) {
        this.name = name
        this.id = id
        this.fare = fare
    }

    def getName() {
        return name
    }

    def getId() {
        return id
    }

    def getFare() {
        return fare
    }

    @Override
    public String toString() {
        return "Person{" +"name=" + name +", id=" + id +", fare=" + fare +'}';
    }
}
