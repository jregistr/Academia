package metro.engine

import groovyx.gpars.actor.DynamicDispatchActor
import groovyx.gpars.scheduler.Scheduler
import javafx.application.Platform
import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleIntegerProperty
import metro.metro.MTA
import metro.utils.Message
import metro.utils.PersonMessage
import metro.utils.StationID
import metro.utils.StationMessage
import metro.utils.TrainMessage

import static metro.utils.CommandOrigin.*
import static metro.utils.Command.*

/**
 * @author Jeff Registre
 * @since 9/24/2015
 */
class Train extends DynamicDispatchActor{

    private boolean running
    private int capacity
    private boolean acceptPassengers
    private List<Person> people
    private Scheduler askScheduler
    private Scheduler travelScheduler
    private StationID lastStation

    public IntegerProperty count = new SimpleIntegerProperty()

    public Train(){
        running = false
        capacity = 10
        acceptPassengers = false
        people = new ArrayList<>()
        Platform.runLater({count.setValue(people.size())})
        askScheduler = new Scheduler()
        travelScheduler = new Scheduler()
    }

    private void doTravel(){
        MTA.instance << new TrainMessage(TRAIN, GO, lastStation)
        running = true
        acceptPassengers = false
       // askScheduler.shutdown()
        travelScheduler.execute({
            println "===================================================================================="
            println "==========================  Doors closed, leaving soon  ============================"
            println "===================================================================================="
            sleep(2000)
            println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
            println "==========================  On the journey    ======================================"
            println ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"
            sleep(15000)
            println "************************************************************************************"
            println "==========================  I've arrived    ========================================"
            println "************************************************************************************"
            this << new TrainMessage(TRAIN, DOCK, null)
        })
    }

    @SuppressWarnings('all')
    public def onMessage(StationMessage message){
        println "Station message"
        if(message != null){
            message.with {
                if(station != null && origin == MASTER && command == DOCK){
                    if(!running){
                        println "Docked at: " + station.stationID
                        lastStation = station.stationID
                        acceptPassengers = true
                       // askScheduler = new Scheduler()
                        askScheduler.execute({
                            while (acceptPassengers){
                             //   println "---------------------------------------------"
                                sleep(300)
                                this << station
                            }
                        })
                    }
                }
            }
        }
    }

    @SuppressWarnings('all')
    public def onMessage(PersonMessage message){
        println "Person message"
        if(message != null){
            message.with {
                if(person != null && origin == STATION && command == ADD){
                    if(!running && acceptPassengers && people.size() < capacity){
                        people << person
                        Platform.runLater({count.setValue(people.size())})
                        if(people.size() == capacity){
                            acceptPassengers = false
                           // askScheduler.shutdown()
                            doTravel()
                        }
                    }else {
                        def ps = people.size();
                        Platform.runLater({count.setValue(people.size())})
                        println "I cannot add another person. Running:$running, Accept:$acceptPassengers, Size:$ps,$capacity"
                        reply(new PersonMessage(TRAIN, TAKE, message.person))
                    }
                }else {
                    println "Failed initial conditions on Person Message"
                }
            }
        }else {
            println "Null message in person message"
        }
    }

    @SuppressWarnings('all')
    public def onMessage(Station station){
        if(acceptPassengers && !running){
            station << new PersonMessage(TRAIN, GIVE, null)
        }
    }

    @SuppressWarnings('all')
    public def onMessage(TrainMessage trainMessage){
        if(trainMessage != null){
            if(trainMessage.origin == TRAIN){
                if(trainMessage.command == DOCK){
                    people = new ArrayList<>()
                    Platform.runLater({count.setValue(people.size())})
                    running = false
                    acceptPassengers = true
                    MTA.instance << new TrainMessage(TRAIN, DOCK, lastStation)
                }
            }else if(trainMessage.origin == MASTER){
                if(trainMessage.command == GO){
                    if(!running)
                        doTravel()
                }
            }
        }
    }

}

