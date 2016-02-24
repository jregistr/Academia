package metro.utils

/**
 * @author Jeff Registre
 * @since 9/24/2015
 */

enum StationID {
    A,
    B,
    C,
    D
}

enum CommandOrigin{
    MASTER,
    TRAIN,
    STATION
}

enum Command{
    ADD,
    GIVE,
    TAKE,
    KICK,
    CLOSE,
    GO,
    REVENUE,
    DOCK
}

/*enum StationUtilCommand{
    REVENUE
}

enum StationManageCommand{
    ADD,
    GIVE,
    TAKE
}

enum StationControlCommand{
    KICK,
    CLOSE
}

enum TrainManageControlCommand{
    KICK,
    GO
}*/
