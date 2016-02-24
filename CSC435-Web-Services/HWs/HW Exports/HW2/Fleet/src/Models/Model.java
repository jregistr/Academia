package Models;


public abstract class Model {

    protected static final String DB_LINK = "jdbc:mysql://localhost:8888/commanders";
    protected static final String DB_USER = "jeff";
    protected static final String DB_PASS = "xxxx";

    public abstract boolean save();

    protected static String buildCommaSeperated(String one,String two,String... values){
        String seperator = ", ";
        String result = one + seperator + two;
        if(values != null && values.length > 0){
            for (String value : values) {
                result  += seperator + value;
            }
        }
        return result;
    }

    protected static String buildPreparedParameters(String one,String two,String... values) {
        String seperator = " = ?,";
        String end = " = ?";
        String result = one + seperator + two;
        if(values != null && values.length > 0){
            for (String value : values) {
                result +=seperator + value;
            }
        }
        result += end;
        return result;
    }

    @Override
    public abstract String toString();

}
