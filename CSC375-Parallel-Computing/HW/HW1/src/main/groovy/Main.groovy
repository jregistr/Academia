import Application.MetroSimulator
import metro.metro.MTA

/**
 * @author Jeff Registre
 */
class Main {

    public static def main(String[] args){

        new MetroSimulator().launch(MetroSimulator.class, args)

       // new MetroSimulator().launch(args)

        /*MTA mta = MTA.getInstance()
        mta.beginOperations()
        mta.join()*/
    }

}
