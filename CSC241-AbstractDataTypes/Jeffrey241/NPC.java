package Jeffrey241;

/*
 @author Jeff Registre
 created on 2/4/2014
 */
public final class NPC extends Creature {

    public NPC(String n, String d) {
        super(n, d, Room.dirty);
    }

    @Override

    public void react(String st) {
        if((!(st.equals(Room.halfDirty))) && (!(st.equals(_roomPref)))){
            System.out.println(_name + " Does not like the room");
            if(_moved){
                _moved = false;
                _curRoom.SetState(false);
            }else {
                if(PC.getInstance().getCurRoom() == _curRoom){
                expression(false);
                }
                moveRoom();
            }
        }else{
            if(!_moved && (PC.getInstance().getCurRoom() == _curRoom))
            expression(true);
        }
    }
    
    @Override 
    public void expression(boolean pos){
         if(pos){
            System.out.println("Smile ;)");
            if(!_commanded){
            PC.getInstance().setRespect(1);
            }else{
                 System.out.println(_name + " has Bigger respect");
                PC.getInstance().setRespect(3);
                _commanded = false;
            }
        }else{
            System.out.println("GRUMBLE, GRUMBLE VERY LOUDLY >.<");
            if(!_commanded){
            PC.getInstance().setRespect(-1);
            }else{
                System.out.println(_name + " has Bigger Disrespect");
                PC.getInstance().setRespect(-3);
                _commanded = false;
            }
        }
    }
    
    @Override
    public String toString (){
        return String.format("\n--------< %s >--------\nName: %s\nDescription:%s", "NPC",_name, _discription);
    }

}
