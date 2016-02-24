package Models;


import java.io.Closeable;
import java.sql.*;

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

    protected static void closeConnection(Connection connection){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected static void closeStatement(Statement statement){
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected static void closePreparedStatement(PreparedStatement preparedStatement){
        if(preparedStatement != null){
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected static void closeResultset(ResultSet resultSet){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public abstract String toString();

}
