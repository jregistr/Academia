package metro.utils

import metro.engine.Station


class StationMessage extends Message{

    public final Station station

    StationMessage(CommandOrigin origin, Command command, Station station) {
        super(origin, command)
        this.station = station
    }
}
