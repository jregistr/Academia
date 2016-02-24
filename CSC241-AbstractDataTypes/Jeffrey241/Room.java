package Jeffrey241;

import java.util.HashMap;
import java.util.Map;

/*
 * @author Jeff Registre :)
 created on 1/31/2014
 */
public final class Room {

    private final String _name, _discription;
    //private Creature[] _creatures;
    private HashMap<String, Creature> creatures;

    private Room _northNeighbor, _southNeighbor, _westNeighbor, _eastNeighbor;
    private String _state;

    public static final String clean = "clean";
    public static final String halfDirty = "half-dirty";
    public static final String dirty = "dirty";

    public Room(String n, String d, String s) {
        _name = n;
        _discription = d;
        _state = s;
        //  _creatures = new Creature[1];
    }

    public Room GetNeighbor(RoomCheck r) {
        switch (r) {
            case North: {
                return _northNeighbor;
            }
            case South: {
                return _southNeighbor;
            }
            case West: {
                return _westNeighbor;
            }
            case East: {
                return _eastNeighbor;
            }
            default: {
                return null;
            }
        }
    }

    public void SetNeighbor(RoomCheck ch, Room r) {
        switch (ch) {
            case North: {
                if (_northNeighbor == null) {
                    _northNeighbor = r;
                }
                break;
            }
            case South: {
                if (_southNeighbor == null) {
                    _southNeighbor = r;
                }
                break;
            }
            case West: {
                if (_westNeighbor == null) {
                    _westNeighbor = r;
                }
                break;
            }
            case East: {
                if (_eastNeighbor == null) {
                    _eastNeighbor = r;
                }
                break;
            }
        }
    }

    public int CreatureCount() {
        if (creatures == null) {
            return 0;
        } else {
            return creatures.size();
        }
    }

    public boolean FullRoom() {
        boolean full = (CreatureCount() == 10);
        return full;
    }

    public String GetName() {
        return _name;
    }

    public String GetDiscription() {
        return _discription;
    }

    public String GetState() {
        return _state;
    }

    public void SetState(boolean cleanUp) {
        System.out.println(_name + " Before Clean: " + _state);
        boolean performedAction = false;
        if (cleanUp) {
            if (_state.equals(dirty)) {
                _state = halfDirty;
                performedAction = true;
            } else if (_state.equals(halfDirty)) {
                _state = clean;
                performedAction = true;
            }
        } else {
            if (_state.equals(clean)) {
                _state = halfDirty;
                performedAction = true;
            } else if (_state.equals(halfDirty)) {
                _state = dirty;
                performedAction = true;
            }
        }

        System.out.println(_name + " After Clean: " + _state);
        if (performedAction) {
//            for (Creature creach : _creatures) {
//                if (creach != null) {
//                    creach.lookState();
//                } else {
//                    throw new NullPointerException("There is a null spot in the array, fix your code jeff");
//                }
//            }
            HashMap<String,Creature> tempMap = new HashMap(creatures);
            for (Map.Entry<String, Creature> entry : tempMap.entrySet()) {
                entry.getValue().lookState();
            }

        }
    }

    public void AddCreature(Creature c) {
        if (!FullRoom()) {
            if (creatures == null) {
                creatures = new HashMap<>();
                Creature toAdd = c;
                toAdd.setCurRoom(this);
               // _creatures[0] = toAdd;
                creatures.put(c.getName(), c);
            } else {
//                Creature[] temp = new Creature[_creatures.length + 1];
//                System.arraycopy(_creatures, 0, temp, 0, _creatures.length);

                Creature toAdd = c;
                toAdd.setCurRoom(this);
//                temp[temp.length - 1] = toAdd;
                   creatures.put(c.getName(), c);
//                _creatures = temp;
            }
           // sort(0, _creatures.length - 1);
        }
    }

    public void RemoveCreature(Creature c, boolean leaving) {
//        for (int i = 0; i < _creatures.length; i++) {
//            if (_creatures[i] != null && _creatures[i] == c) {
//                _creatures[i] = null;
//                break;
//            }
//        }
        creatures.remove(c.getName());
//        Creature[] temp = new Creature[_creatures.length - 1];
//        int tempIndex = 0;
//        for (int i = 0; i < _creatures.length; i++) {
//            Creature cr = _creatures[i];
//            if (cr != null) {
//                temp[tempIndex] = cr;
//                tempIndex++;
//            }
//        }
//        _creatures = temp;
        //sort(0, _creatures.length - 1);
        if (leaving) {
//            for (Creature cr : _creatures) {
//                if (cr != null) {
//                    cr.expression(true);
//                }
//            }
            HashMap<String,Creature> tempMap = new HashMap(creatures);
            for (Map.Entry<String, Creature> entry : tempMap.entrySet()) {
                entry.getValue().expression(true);
            }
        }
    }

    public Creature search(String name) {
        //return binarySearch(name, 0, _creatures.length - 1);
        return creatures.get(name);
    }

//    private Creature binarySearch(String name, int lowerBound, int upperBound) {
//        if ((upperBound - lowerBound) <= 1) {
//            if (_creatures[lowerBound].getName().equals(name)) {
//                return _creatures[lowerBound];
//            }
//            if (_creatures[upperBound].getName().equals(name)) {
//                return _creatures[upperBound];
//            }
//            return null;
//        }
//        int midIndex = (upperBound + lowerBound) / 2;
//        if (name.compareTo(_creatures[midIndex].getName()) >= 0) {
//            return binarySearch(name, midIndex, upperBound);
//        } else {
//            return binarySearch(name, lowerBound, midIndex);
//        }
//
//    }

//    public void sort(int lowerBound, int upperBound) {
//        if (_creatures == null || _creatures.length == 0) {
//            return;
//        }
//        if (lowerBound < upperBound) {
//            int splitPoint = splitArray(lowerBound, upperBound);
//            sort(lowerBound, splitPoint);
//            sort(splitPoint + 1, upperBound);
//        }
//    }

//    private int splitArray(int lowerBound, int upperBound) {
//        Creature pivot = _creatures[upperBound / 2];
//        int left = lowerBound;// - 1;
//        int right = upperBound;// + 1;
//        for (;;) {
//            //left++;
//            while (left < upperBound && _creatures[left].compareTo(pivot) > 0) {
//                left++;
//            }
//            // right--;
//            while (right > lowerBound && _creatures[right].compareTo(pivot) < 0) {
//                right--;
//            }
//            if (left < right) {
//                //swap
//                Creature temp = _creatures[left];
//                _creatures[left] = _creatures[right];
//                _creatures[right] = temp;
//                return left;
//            } else {
//                return right;
//            }
//        }
//    }

    @Override
    public String toString() {

        String neiConcat = "";
        if (_northNeighbor != null) {
            neiConcat += String.format("[North: %s]", _northNeighbor.GetName());
        }
        if (_eastNeighbor != null) {
            neiConcat += String.format(" [East: %s]", _eastNeighbor.GetName());
        }
        if (_southNeighbor != null) {
            neiConcat += String.format(" [South: %s]", _southNeighbor.GetName());
        }
        if (_westNeighbor != null) {
            neiConcat += String.format(" [West: %s]", _westNeighbor.GetName());
        }

        String creachConcat = "";

//        for (Creature creach : _creatures) {
//            if (creach != null) {
//                creachConcat += creach.toString();
//            }
//        }
        HashMap<String,Creature> tempMap = new HashMap(creatures);
            for (Map.Entry<String, Creature> entry : tempMap.entrySet()) {
                creachConcat += entry.getValue().toString();
            }

        String roomConcat = String.format("\n   [Room]  \nName: %s\nDescription: %s\nState: %s\nNeighbors: %s\n\nOccupants%s", _name, _discription, _state, neiConcat, creachConcat);
        return roomConcat;
    }

}
