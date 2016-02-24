package Jeffrey241;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * @author Jeff Registre :)
 created on 3/07/2014
 */
public class XMLReader extends DefaultHandler {

    public class Dictionnaire {

        private Node _storage;

        public void add(String n, String v) {
            if (_storage == null) {
                _storage = new Node(n, v);
            } else {
                _storage = _storage.insert(n, v);
            }
        }

        public String[] getValues(String n) {
            Node temp = _storage.search(n);
            return (temp != null) ? temp._values : null;
        }

//        public void Display() {
//            System.out.println("Nodes: " + _storage.toString());
//        }
        private class Node implements Comparable<Node> {

            public String _name;
            public String[] _values; //store strings as ex: "442,South"
            public Node _next;

            public Node(String n, String v) {
                _name = n;
                _values = new String[1];
                _values[0] = v;
            }

            private Node(String n, String[] v) {
                _name = n;
                _values = v;
            }

            public Node insert(String n, String v) {
                //n = 
                if (contains(n)) {
                    Node temp = search(n);
                    temp.addValue(v);
                    return this;
                } else {
                    Node temp = new Node(n, v);
                    return insert(temp);
                }
            }

            private Node insert(Node item) {
                if (item.compareTo(this) <= 0) {
                    Node temp = new Node(this._name, this._values);
                    this._values = item._values;
                    this._name = item._name;
                    temp._next = this._next;
                    this._next = temp;
                    return this;
                } else if (_next == null) {
                    Node temp = item;
                    _next = temp;
                    return this;
                } else {
                    _next = _next.insert(item);
                    return this;
                }
            }

            public void addValue(String v) {
                String[] temp = new String[_values.length + 1];
                System.arraycopy(_values, 0, temp, 0, _values.length);
//                for (int i = 0; i < _values.length; i++) {
//                    temp[i] = _values[i];
//                }
                temp[temp.length - 1] = v;
                _values = temp;
            }

            public boolean contains(String n) {
                if (n.equals(_name)) {
                    return true;
                } else if (_next == null) {
                    return false;
                } else {
                    return _next.contains(n);
                }
            }

            public Node search(String n) {
                if (n.equals(_name)) {
                    return this;
                }
                return (_next == null) ? null : _next.search(n);
            }

            @Override
            public int compareTo(Node other) {
                return _name.compareTo(other._name);
            }

            @Override
            public String toString() {
                String concat = "";
                for (String s : _values) {
                    if (s != null) {
                        if (concat.equals("")) {
                            concat += "" + s;
                        } else {
                            concat += "," + s;
                        }
                    }
                }
                String temp = String.format("%s {%s}", _name, concat);
                if (_next != null) {
                    temp = temp + " : " + _next.toString();
                }
                return temp;
            }
        }
    }

    private Dictionnaire _dict;
    private final String _roomTag = "room";
    private final String _npcTag = "NPC";
    private final String _animalTag = "animal";
    private final String _pcTag = "PC";
    private final String _nameTag = "name";
    private final String _descTag = "description";
    private final String _stateTag = "state";
    private final String _northTag = "north", _southTag = "south", _eastTag = "east", _westTag = "west";

    private Room _room = null;
    private Creature _creach = null;
    //ArrayList<Room> _rooms;
    HashMap<String,Room> rooms;

    public Room[] getRooms() {
//        Room[] ts = new Room[_rooms.size()];
//        return _rooms.toArray(ts);
        
       // Room[] ts = new Room[rooms.size()];
        return rooms.entrySet().toArray(new Room[rooms.size()]);
    }

    public void read(String fn) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        File file;
        file = new File(fn);

        SAXParser saxParser = saxParserFactory.newSAXParser();
        saxParser.parse(file, this);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals(_roomTag)) {
            String name = " ", desc = "", state = "", north = null, south = null, west = null, east = null;
            for (int i = 0; i < attributes.getLength(); i++) {
                String temp = attributes.getQName(i);
                switch (temp) {
                    case _nameTag: {
                        name = attributes.getValue(i);
                        break;
                    }
                    case _descTag: {
                        desc = attributes.getValue(i);
                        break;
                    }
                    case _stateTag: {
                        state = attributes.getValue(i);
                        break;
                    }
                    case _northTag: {
                        north = String.format("%s,%s", _northTag, attributes.getValue(i));
                        break;
                    }
                    case _southTag: {
                        south = String.format("%s,%s", _southTag, attributes.getValue(i));
                        break;
                    }
                    case _westTag: {
                        west = String.format("%s,%s", _westTag, attributes.getValue(i));
                        break;
                    }
                    case _eastTag: {
                        east = String.format("%s,%s", _eastTag, attributes.getValue(i));
                        break;
                    }
                }
            }

            Room r = new Room(name, desc, state);
            _room = r;

            if (_dict == null) {
                _dict = new Dictionnaire();
            }

            if (north != null) {
                _dict.add(r.GetName(), north);
            }
            if (south != null) {
                _dict.add(r.GetName(), south);
            }

            if (west != null) {
                _dict.add(r.GetName(), west);
            }
            if (east != null) {
                _dict.add(r.GetName(), east);
            }

        } else {
            String elem = qName;
            String name = " ", desc = "";

            for (int i = 0; i < attributes.getLength(); i++) {
                String temp = attributes.getQName(i);
                if (temp.equals(_nameTag)) {
                    name = attributes.getValue(i);
                } else if (temp.equals(_descTag)) {
                    desc = attributes.getValue(i);
                }
            }

            if (elem.equals(_npcTag)) {
                NPC np = new NPC(name, desc);
                _creach = np;
            } else if (elem.equals(_animalTag)) {
                Animal an = new Animal(name, desc);
                _creach = an;
            } else if (elem.equals(_pcTag)) {
                PC.createInstance(name, desc);
                PC pc = PC.getInstance();
                _creach = pc;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equals(_roomTag)) {
            if (rooms == null) {
                rooms = new HashMap<>();
            }

            if (_room != null) {
                //_rooms.add(_room);
                rooms.put(_room.GetName(), _room);

                _room = null;
            }
        } else if (qName.equals(_npcTag) || qName.equals(_animalTag) || qName.equals(_pcTag)) {
            if (_room != null && _creach != null) {
                _room.AddCreature(_creach);
            }
        }
    }

    @Override
    public void endDocument() {
       // HashMap<String,Creature> tempMap = new HashMap(creatures);
            for (Map.Entry<String, Room> entry : rooms.entrySet()) {
                Room r = entry.getValue();
                     String[] neis = _dict.getValues(r.GetName());
            for (String s : neis) {
                Scanner scan = new Scanner(s);
                scan.useDelimiter(",");
                String dir = scan.next();
                String name = scan.next();

                Room nei = rooms.get(name);
//                for (Room ne : _rooms) {
//                    if (ne.GetName().equalsIgnoreCase(name)) {
//                        nei = ne;
//                        break;
//                    }
//                }

                if (nei != null) {
                    switch (dir) {
                        case _northTag: {
                            r.SetNeighbor(RoomCheck.North, nei);
                            break;
                        }
                        case _southTag: {
                            r.SetNeighbor(RoomCheck.South, nei);
                            break;
                        }
                        case _westTag: {
                            r.SetNeighbor(RoomCheck.West, nei);
                            break;
                        }
                        case _eastTag: {
                            r.SetNeighbor(RoomCheck.East, nei);
                            break;
                        }
                    }
                }
            }
            }
        
        
//        for (Room r : _rooms) {
//            String[] neis = _dict.getValues(r.GetName());
//            for (String s : neis) {
//                Scanner scan = new Scanner(s);
//                scan.useDelimiter(",");
//                String dir = scan.next();
//                String name = scan.next();
//
//                Room nei = null;
//                for (Room ne : _rooms) {
//                    if (ne.GetName().equalsIgnoreCase(name)) {
//                        nei = ne;
//                        break;
//                    }
//                }
//
//                if (nei != null) {
//                    switch (dir) {
//                        case _northTag: {
//                            r.SetNeighbor(RoomCheck.North, nei);
//                            break;
//                        }
//                        case _southTag: {
//                            r.SetNeighbor(RoomCheck.South, nei);
//                            break;
//                        }
//                        case _westTag: {
//                            r.SetNeighbor(RoomCheck.West, nei);
//                            break;
//                        }
//                        case _eastTag: {
//                            r.SetNeighbor(RoomCheck.East, nei);
//                            break;
//                        }
//                    }
//                }
//            }
//
//        }

    }
    
    public Room RoomSearch (String n){
        return rooms.get(n);
//        Room temp = null;
//        for(Room r : _rooms){
//            if(r != null && r.GetName().equals(n)){
//                temp = r;
//                break;
//            }
//        }
//        return temp;
    }
    //end of code,
}
