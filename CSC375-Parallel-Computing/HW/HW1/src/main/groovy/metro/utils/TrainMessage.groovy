package metro.utils


class TrainMessage extends Message{

    public final StationID stationID

    TrainMessage(CommandOrigin origin, Command command, StationID stationID) {
        super(origin, command)
        this.stationID = stationID
    }
}
