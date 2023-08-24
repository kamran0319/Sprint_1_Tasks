package utils.misc;

import utils.ConfigReader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestJDBC {

    public static void main(String[] args) throws SQLException {


        String url = "jdbc:mysql://apps-database.cb72canasobc.us-east-2.rds.amazonaws.com/employees";
        Connection connection = DriverManager.getConnection(url, ConfigReader.getProperty("db.username"), ConfigReader.getProperty("db.password"));
        System.out.println("Connection established");

        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String sqlQuery = "select * from departments";
        ResultSet resultSet = statement.executeQuery(sqlQuery);


        System.out.println(resultSet);

        //move the cursor to the next row

        resultSet.next();

        String second = resultSet.getString(2); // NOT zero based

        System.out.println(second);

        System.out.println(resultSet.getString("dept_name"));


        // move to  specific row

        resultSet.absolute(4);

        System.out.println(resultSet.getString("dept_name"));

        // go back to before the first row

        resultSet.beforeFirst();

        // iterate through a result set

        while(resultSet.next()){
            System.out.println(resultSet.getString("dept_no") + " " + resultSet.getString("dept_name"));
        }

        resultSet.beforeFirst();

        System.out.println(resultSet.getRow());

        // number of rows

        resultSet.last();

        int numOfRows = resultSet.getRow();

        System.out.println("Rows: " + numOfRows);

        // number of columns

        ResultSetMetaData metaData = resultSet.getMetaData();

        int numOfColumns = metaData.getColumnCount();

        System.out.println("Columns: " + numOfColumns);

        // get column names

        for (int i = 1; i <= numOfColumns; i++) {
            System.out.println(metaData.getColumnName(i));
        }


        resultSet.beforeFirst();

        List<Object> listOfLists =  new ArrayList<>();

        for (int i = 1; i <=numOfRows ; i++) {
             resultSet.absolute(i);
            List<Object> inner =  new ArrayList<>();
            for (int j = 1; j <=numOfColumns ; j++) {

                inner.add(resultSet.getObject(j));

            }
            listOfLists.add(inner);

        }

        System.out.println(listOfLists);


        statement.executeUpdate("INSERT INTO departments (dept_no, dept_name) VALUES ('d011', 'Machine Learning')");



        resultSet = statement.executeQuery("select * from departments");


        while(resultSet.next()){
            System.out.println(resultSet.getString("dept_no") + " " + resultSet.getString("dept_name"));
        }


        resultSet.close();
        statement.close();
        connection.close();

    }
}
