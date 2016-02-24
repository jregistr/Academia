package metro.engine

import groovyx.gpars.actor.DynamicDispatchActor
import javafx.application.Platform
import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import metro.utils.PersonMessage
import metro.utils.StationID

import static metro.utils.CommandOrigin.*
import static metro.utils.Command.*

class Station extends DynamicDispatchActor {

    private String name
    private List<Person> departures
    private int revenue
    private boolean operating
    public final StationID stationID

    public IntegerProperty count = new SimpleIntegerProperty()
  //  public StringProperty property = new SimpleStringProperty()

    Station(String name, StationID stationID){
        departures = new ArrayList<>()
        count.setValue(departures.size())
    //    property.setValue("Ballz")
        this.name = name
        revenue = 0
        operating = true
        this.stationID = stationID
    }

    @SuppressWarnings('all')
    public def onMessage(PersonMessage message){
        if(message != null){
            if(message.origin == MASTER){
                masterMessage(message)
              //  println "$name Has $departures.size people"
            }else if(message.origin == TRAIN){
                trainMessage(message)
            }else if(message.origin == STATION){
                stationMessage(message)
            }
        }
    }

    private def masterMessage(PersonMessage message){
        if(message.command == ADD && message.person != null){
            if(operating){
                departures << message.person
              //  count.setValue(1)
                Platform.runLater({count.setValue(departures.size())})
            }
        }
    }

    private def trainMessage(PersonMessage message){
        if(message.command == TAKE){
            if(message.person != null){
                println "Recieved from Train:" + message.person.name
                departures << message.person
                Platform.runLater({count.setValue(departures.size())})
             //   count.setValue(departures.size())
            }
        }else if(message.command == GIVE){
            if(operating && departures.size() > 0){
                println "$name is giving"
                reply(new PersonMessage(STATION, ADD, departures.removeAt(0)))
                Platform.runLater({count.setValue(departures.size())})
               // count.setValue(departures.size())
            }
        }
    }

    private def stationMessage(PersonMessage message){
        if(message.command == KICK){
            if(departures.size() > 0){
                departures.removeAt(0)
                Platform.runLater({count.setValue(departures.size())})
             //   count.setValue(departures.size())
            }
        }
    }

}



