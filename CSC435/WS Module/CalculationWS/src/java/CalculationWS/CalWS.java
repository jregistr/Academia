/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CalculationWS;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Jeff
 */
@WebService(serviceName = "CalWS")
public class CalWS {

    /**
     * This is a sample web service operation
     * @param value1
     * @param value2
     * @return returns the value of value1 plus value2
     */
   @WebMethod(operationName = "Addition")
    public String Addition(@WebParam(name = "value1") String value1,@WebParam(name = "value2") String value2 ) {
        float value=Float.valueOf(value1)+Float.valueOf(value2);
        return (Float.toString(value));
    }
    
    /**
     * 
     * @param value1
     * @param value2
     * @return sends back the value of value1 minus value2
     */
    @WebMethod(operationName = "Substraction")
    public String Substraction (@WebParam(name = "value1") String value1,@WebParam(name = "value2") String value2){
        float value=Float.valueOf(value1)- Float.valueOf(value2);
        return (Float.toString(value));
    }
    
    /**
     * 
     * @param value1
     * @param value2
     * @return value1 divided by value2
     */
    @WebMethod(operationName = "Division")
    public String Division (@WebParam(name = "value1") String value1,@WebParam(name = "value2") String value2){
        float val1 = Float.valueOf(value1);
        float val2 = Float.valueOf(value2);
        if(val2 > 0)
            return Float.toString(val1/val2);
        else
            throw new IllegalArgumentException("Argument 'value2' is 0");
    }
    
    /**
     * 
     * @param value1
     * @param value2
     * @return value1 multiplied by value2
     */
    @WebMethod(operationName = "Multiplication")
    public String Multiplication (@WebParam(name = "value1") String value1,@WebParam(name = "value2") String value2){
       float value=Float.valueOf(value1) * Float.valueOf(value2);
       return (Float.toString(value));
    }

}
