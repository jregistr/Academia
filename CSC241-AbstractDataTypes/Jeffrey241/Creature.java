package Jeffrey241;

import java.util.Random;

/*
 @author Jeff Registre
 created on 2/4/2014
 */
public abstract class Creature implements Comparable<Creature> {

    protected Room _curRoom;
    protected String _name;
    protected String _discription;
    protected String _roomPref;
    protected boolean _moved;
    protected boolean _commanded;

    public Creature(String n, String d, String p) {
        _name = n;
        _discription = d;
        _roomPref = p;
    }

    public final Room getCurRoom() {
        return _curRoom;
    }

    public final void setCurRoom(Room t) {
        _curRoom = t;
    }

    public final String getName() {
        return _name;
    }

    public final String getDiscription() {
        return _discription;
    }

    public final void modifyRoom(boolean cleanUp) {
        _curRoom.SetState(cleanUp);
    }
    
    public void commandModifyRoom (boolean cleanUp){
        _commanded = true;
        _curRoom.SetState(cleanUp);
    }

    public final boolean canMoveTo(Room to) {
        return (to != null && (!to.FullRoom()));
    }

    public void moveRoom(RoomCheck rc) {
        Room temp = _curRoom.GetNeighbor(rc);
        if(canMoveTo(temp)){
            _curRoom.RemoveCreature(this, false);
            temp.AddCreature(this);
        }
    }

    public void moveRoom() {
        Room[] temp = new Room[4];
        int count = 0;
        if (canMoveTo(_curRoom.GetNeighbor(RoomCheck.North))) {
            temp[count] = _curRoom.GetNeighbor(RoomCheck.North);
            count++;
        }
        if (canMoveTo(_curRoom.GetNeighbor(RoomCheck.South))) {
            temp[count] = _curRoom.GetNeighbor(RoomCheck.South);
            count++;
        }
        if (canMoveTo(_curRoom.GetNeighbor(RoomCheck.West))) {
            temp[count] = _curRoom.GetNeighbor(RoomCheck.West);
            count++;
        }
        if (canMoveTo(_curRoom.GetNeighbor(RoomCheck.East))) {
            temp[count] = _curRoom.GetNeighbor(RoomCheck.East);
            count++;
        }

        if (count > 0) {
            Random rng = new Random();
            Room r = temp[rng.nextInt(count)];
            System.out.println(_name + " is Moving to " + r.GetName());
            _curRoom.RemoveCreature(this,false);
            r.AddCreature(this);
            
            _moved = true;
            lookState();
        }else{
            System.out.println("No where to go, I'm leaving");
            leaveGame();
        }
    }

    public void lookState() {
        String state = _curRoom.GetState();
        react(state);
    }

    public abstract void react(String st);

    public void leaveGame() {
        System.out.println("Creature " + this.toString());
        _curRoom.RemoveCreature(this,true);
    }
    
    public void expression (boolean pos){
        
    }

    @Override
    public abstract String toString();

    @Override
    public int compareTo (Creature other){
        return other.getName().compareTo(_name);
    }
}
