package metro.utils


abstract class Message {

    public final CommandOrigin origin
    public final Command command

    Message(CommandOrigin origin, Command command) {
        this.origin = origin
        this.command = command
    }
}
