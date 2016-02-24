package metro.utils

import metro.engine.Person

class PersonMessage extends Message{

    public final Person person

    PersonMessage(CommandOrigin origin, Command command, Person person) {
        super(origin, command)
        this.person = person
    }
}
