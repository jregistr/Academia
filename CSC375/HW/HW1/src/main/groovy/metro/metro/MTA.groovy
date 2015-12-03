package metro.metro

import groovyx.gpars.actor.DynamicDispatchActor
import groovyx.gpars.scheduler.Scheduler
import javafx.beans.binding.Bindings
import javafx.beans.binding.StringBinding
import javafx.beans.property.FloatProperty
import javafx.beans.property.IntegerProperty
import javafx.beans.property.SimpleFloatProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import metro.engine.Person
import metro.engine.Station
import metro.engine.Train
import metro.utils.PersonMessage
import metro.utils.StationID
import metro.utils.StationMessage
import metro.utils.TrainMessage

import static metro.utils.CommandOrigin.*
import static metro.utils.Command.*

/**
 * @author Jeff Registre
 */

class MTA extends DynamicDispatchActor{

    private static final int MAX_CREATE_INTERVAL = 2000;
    private static final int MIN_CREATE_INTERVAL = 100;

    private static MTA instance
    private boolean sContinue

    private Station stationA, stationB

    private Train train

    private Scheduler schedulerA, schedulerB
    Random rand

    FloatProperty sta1, sta2, track

  /*  FloatProperty sta1, sta2, track
    IntegerProperty sta1Pop, sta2Pop, tPop*/

    public MTA(){
        sContinue = false
        stationA = stationB = null
        schedulerA = schedulerB = null
        rand = new Random()
    }

    public static MTA getInstance(){
        if(!instance)
            instance = new MTA()
        instance
    }

    public void beginOperations(ProgressBar p1, ProgressBar p2, ProgressBar p3, Label sta1Lb, Label sta2Lb, Label tCount){
        this.start()
       // train.start()
        create()

        p1.progressProperty().bind(sta1)
        p2.progressProperty().bind(track)
        p3.progressProperty().bind(sta2)

        // sta1Lb.textProperty().bind(Bindings.convert(stationA.count))
        sta1Lb.textProperty().bind(new StringBinding() {
            {
                bind(stationA.count)
            }
            @Override
            protected String computeValue() {
                return "People:" + stationA.count.get()
            }
        })

        sta2Lb.textProperty().bind(new StringBinding() {
            {
                bind(stationB.count)
            }
            @Override
            protected String computeValue() {
                return "People:" + stationB.count.get()
            }
        })

        tCount.textProperty().bind(new StringBinding() {
            {
                bind(train.count)
            }
            @Override
            protected String computeValue() {
                return "Count:" + train.count.get()
            }
        })

        println "Start"
        train << new StationMessage(MASTER, DOCK, stationA)
        sta1.setValue(1)
       // train << new PersonMessage(STATION, ADD, null)
       /* new Scheduler().execute({
           sleep(40000)
           println "Time to dock"
           train << new StationMessage(MASTER, DOCK, stationA)
       })*/
    }

    private def create(){
        sta1 = new SimpleFloatProperty()
        sta2 = new SimpleFloatProperty()
        track = new SimpleFloatProperty()
        sta1.setValue(0)
        sta2.setValue(0)
        track.setValue(0)

        stationA = new Station("Port Authority Station", StationID.A)
        stationB = new Station("Penn Station", StationID.B)
        train = new Train()
        stationA.start()
        stationB.start()
        train.start()

        def rng = {int min, int max -> rand.nextInt((max - min) + 1) + min}
        def name = {int min, int max -> (1..rng(min, max)).inject (""){a, b -> a + ('a'..'z')[rng(0,26)]}}
        def shedProg = {
            while (sContinue){
                sleep(rng(MIN_CREATE_INTERVAL, MAX_CREATE_INTERVAL))
                if(sContinue)
                    this << new Person(name(3,6).toString().capitalize() + " " + name(6,10).toString().capitalize(), Math.abs(rand.nextLong()), rng(1, 20))
            }
        }
        sContinue = true
        schedulerA = new Scheduler()
        schedulerB = new Scheduler()
        schedulerA.execute(shedProg)
        schedulerB.execute(shedProg)
        println "End of create method"
    }

    //<editor-fold desc="Message handling">
    @SuppressWarnings('all')
    public def onMessage(Person person){
        if(rand.nextInt(2) == 0)
            stationA.send(new PersonMessage(MASTER, ADD, person))
        else
            stationB.send(new PersonMessage(MASTER, ADD, person))
    }

    @SuppressWarnings('all')
    public def onMessage(TrainMessage trainMessage){
        if(trainMessage != null){
            trainMessage.with {
                if(origin == TRAIN && stationID != null){
                    if(command == DOCK){
                        if(stationID == StationID.A){
                            reply(new StationMessage(MASTER, DOCK, stationB))
                            sta1.setValue(0)
                            sta2.setValue(1)
                            track.setValue(0)
                        }else {
                            reply(new StationMessage(MASTER, DOCK, stationA))
                            sta1.setValue(1)
                            sta2.setValue(0)
                            track.setValue(0)
                        }
                    }else if(command == GO){
                        if(stationID == StationID.A){
                            sta1.setValue(0)
                            track.setValue(1)
                        }else if(stationID == StationID.B){
                            sta2.setValue(0)
                            track.setValue(1)
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings('all')
    public def onMessage(String go){
        train << new TrainMessage(MASTER, GO, null)
    }
    //</editor-fold>

}
