package Jeffrey241;

import java.util.Scanner;

/*
 @author Jeff Registre
 created on 2/4/2014
 */
public final class PC extends Creature {

    private int _respect;
    private boolean _gameOn;
    private final int _maxRespect = 80;

    private static PC _instance;

    private final String help = "help", look = "look", clean = "clean", dirty = "dirty", north = "north", south = "south", east = "east", west = "west";
    private final String helpDesc = String.format("[%s]: Displays\nall possible commands and what they do", help),
            lookDesc = String.format("[%s]: Outputs information about the current room you're in", look),
            cleanDesc = String.format("[%s]: Performs a cleaning action on the room,\nkeep in mind your actions may upset other creatures", clean),
            dirtyDesc = String.format("[%s]: Performs a Dirtying action on the room,\nkeep in mind your actions may upset other creatures", dirty),
            directionMoveDes = String.format("[%s,%s,%s,%s]: Moves you to the room in the direction, if possible", north, south, east, west),
            creatureCommandDes = "Plus CreatureName:Command to command a creature to do an action, like move or change room state";
    private final String commandConcat = String.format("%s,%s\n%s,%s\n%s,%s\n%s,%s", help, look, clean, dirty, north, south, east, west);
    //final String discConcat = String.format("%s\n%s\n%s\n%s\n%s",helpDesc,lookDesc,cleanDesc, dirtyDesc,directionMoveDes);
    private final String underLine = "----------------------------------------------------------------------";
    private final String discConcat = String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s", lookDesc, underLine,
            cleanDesc, underLine, dirtyDesc, underLine, directionMoveDes, underLine, helpDesc, underLine, creatureCommandDes, underLine);

    private PC(String n, String d) {
        super(n, d, "None");
        _respect = 40;
    }

    public static PC getInstance() {
        if (_instance == null) {
            System.out.println("PC Not Instanciated Yet");
        }
        return _instance;
    }

    public static void createInstance(String n, String d) {
        if (_instance == null) {
            _instance = new PC(n, d);
        } else {
            System.out.println("PC Already Exists");
        }
    }

    public void play(Scanner scan) {
        _gameOn = true;
        System.out.println("Welcome to the House " + "[" + _name + "]");

        System.out.println("Below are your possible inputs\n" + commandConcat);
        String input = scan.nextLine();
        while (_gameOn) {
            System.out.println(String.format("---------Input Recieved >>>[%s]<<< Input Recieved---------", input));
            executeCommand(input, this);
            if (_gameOn) {
                System.out.println(underLine + "\nBelow are your possible inputs\n" + commandConcat);
                input = scan.nextLine();
            }
        }
    }

    private void executeCommand(String input, Creature creach) {
        switch (input) {
            case "quit": {
                creach.leaveGame();
                break;
            }
            case help: {
                System.out.println(discConcat);
                break;
            }
            case look: {
                System.out.println("Here is the current Condition of the room\n" + _curRoom.toString());
                break;
            }
            case clean: {
                //creach._curRoom.SetState(true);
                creach.commandModifyRoom(true);
                break;
            }
            case dirty: {
                // creach._curRoom.SetState(false);
                creach.commandModifyRoom(false);
                break;
            }
            case north: {
                creach.moveRoom(RoomCheck.North);
                break;
            }
            case south: {
                creach.moveRoom(RoomCheck.South);
                break;
            }
            case east: {
                creach.moveRoom(RoomCheck.East);
                break;
            }
            case west: {
                creach.moveRoom(RoomCheck.West);
                break;
            }

            default: {
                Scanner lineSC = new Scanner(input);
                lineSC.useDelimiter(":");
                String creachName = (lineSC.hasNext()) ? lineSC.next() : null;
                String command = (lineSC.hasNext()) ? lineSC.next() : null;
                boolean failInput = (creachName == null || command == null);
                if (!failInput) {
                    Creature temp = _curRoom.search(creachName);

                    if (temp != null) {
                        boolean okCommand = (temp.getName().equals(_name) || !((command.equals("quit") || command.equals(look) || command.equals(help))));
                        if (!okCommand) {
                            System.out.println("Cant force " + temp.getName() + " to do " + command);
                        } else {
                            executeCommand(command, temp);
                        }

                    } else {
                        System.out.println("No such a creature exists in the room");
                    }
                } else {
                    System.out.println(input + " is an Unknown command breh, Try again!");
                }
                break;
            }
        }
    }

    public int getRespect() {
        return _respect;
    }

    public final void setRespect(int change) {
        _respect += change;
        if ((_respect >= _maxRespect) || (_respect <= 0)) {
            leaveGame();
        }
    }

    @Override
    public void moveRoom(RoomCheck rC) {
        Room to = _curRoom;
        if (rC == RoomCheck.North) {
            to = _curRoom.GetNeighbor(RoomCheck.North);
        } else if (rC == RoomCheck.South) {
            to = _curRoom.GetNeighbor(RoomCheck.South);
        } else if (rC == RoomCheck.West) {
            to = _curRoom.GetNeighbor(RoomCheck.West);
        } else if (rC == RoomCheck.East) {
            to = _curRoom.GetNeighbor(RoomCheck.East);
        }

        if (to != null && to != _curRoom) {
            boolean moveFail = (!canMoveTo(to));
            if (!moveFail) {
                _curRoom.RemoveCreature(this, false);
                to.AddCreature(this);
                System.out.println("Room after move " + _curRoom.GetName());
            } else {
                System.out.println("Cant Move there");
            }
        } else {
            System.out.println("Cant Move there");
        }

    }

    @Override
    public void commandModifyRoom(boolean cleanUp) {
        _curRoom.SetState(cleanUp);
    }

    @Override
    public void lookState() {
        //System.out.println(_curRoom.GetState());
        //  System.out.println(_curRoom.toString());
    }

    @Override
    public void leaveGame() {
        //System.out.println("Game Over");
        _gameOn = false;
        if (_respect <= 0) {
            System.out.println("Game over, you've destroyed the house");
        } else if (_respect >= 80) {
            System.out.println("You're awesome, good game breh!");
        } else {
            System.out.println("Game ended");
        }
    }

    @Override
    public void react(String st) {
        System.out.println("I dont care about state");
    }

    @Override
    public String toString() {
        return String.format("\n--------< %s >--------\nName: %s\nDescription:%s\nRespect: %s", "PC", _name, _discription, _respect);
    }
}
