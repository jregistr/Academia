package models

class Flag {

    public enum LoadState{
        error,
        notDone,
        done
    }

    LoadState state

    static constraints = {
        state unique: true
    }
}
