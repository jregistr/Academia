package Jeffrey241;

/*
 * @author Jeff Registre :)
 created on 3/04/2014
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) throws Exception {
        //System.out.println("I \nam \na \ndude" + "\nTest");
        
        Scanner scan = new Scanner (System.in);
        XMLReader reader = new XMLReader();
        System.out.println("Please Enter the file name or quit, press enter");
        boolean fileNeedLoad = true;
        
        while (fileNeedLoad && !(scan.hasNext("quit"))){
            String in = scan.nextLine();
            
            try{
                reader.read(in);
                fileNeedLoad = false;
            }catch(FileNotFoundException e){
                String message = String.format("The file with name [%s] cannot be found, Try Again or type quit", in);
                System.out.println(message);
            }
            
        }
        
        if(!fileNeedLoad){
        PC pc = PC.getInstance();
        pc.play(scan);
        }
    }
}
