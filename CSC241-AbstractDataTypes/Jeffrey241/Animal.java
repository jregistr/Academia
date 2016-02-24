package Jeffrey241;

/*
 * @author Jeff Registre :')
 created on 1/31/2014
 */
public final class Animal extends Creature {

    public Animal(String n, String d) {
        super(n, d, Room.clean);
    }

    @Override
      public void react(String st) {
        if((!(st.equals(Room.halfDirty))) && (!(st.equals(_roomPref)))){
            System.out.println("ROAR!!, bad room,signed: "+ _name);
            if(_moved){
                _moved = false;
                _curRoom.SetState(true);
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
    public void expression (boolean pos){
        if(pos){
            System.out.println("Lick Face");
            if(!_commanded){
            PC.getInstance().setRespect(1);
            }else{
                 System.out.println(_name + " has Bigger respect");
                PC.getInstance().setRespect(3);
                _commanded = false;
            }
        }else{
            System.out.println("I do not approve of the state of this room");
            System.out.println("GROWL, GROWL UNTIL THE EARTH SHATTERS O.O");
            if(!_commanded){
            PC.getInstance().setRespect(-1);
            }else{
                System.out.println(_name + " has Bigger disrespect");
                PC.getInstance().setRespect(-3);
                _commanded = false;
            }
        }
    }
    
    @Override
    public String toString (){
        return String.format("\n--------< %s >--------\nName: %s\nDescription:%s", "Animal",_name, _discription);
    }

}
