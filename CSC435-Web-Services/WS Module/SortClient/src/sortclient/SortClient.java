/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortclient;

/**
 *
 * @author Jeff
 */
import org.tempuri.*;

public class SortClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String key = getKey();
        String result = mergeSort("12 23 2 34 6 45 10",key);
        System.out.println("Sort:" + result);
        
       
    }

    private static String getKey() {
        Service service = new Service();
        IService port = service.getBasicHttpBindingIService();
        return port.getKey();
    }

    private static String mergeSort(String input, String userKey) {
        Service service = new Service();
        IService port = service.getBasicHttpBindingIService();
        return port.mergeSort(input, userKey);
    }

    
    
    
}
