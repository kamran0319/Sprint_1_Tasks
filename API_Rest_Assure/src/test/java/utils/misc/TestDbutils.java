package utils.misc;

import org.apache.commons.codec.digest.DigestUtils;
import utils.DBUtils;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TestDbutils {


    public static void main(String[] args) throws SQLException {


        String password = DigestUtils.md5Hex("donniedarko1990");

        System.out.println(password);

        DBUtils.createConnection();

//        List<List<Object>> resultSet = DBUtils.getQueryResultAsListOfLists("SELECT * from users");
//
//        for (List<Object> row : resultSet) {
//
//            System.out.println(row);
//
//        }
//
//        String o = (String) resultSet.get(0).get(1);
//        System.out.println(o);

//        List<Map<String, Object>> result2 = DBUtils.getQueryResultListOfMaps("SELECT * from users");
//
//        for (Map<String, Object> row : result2) {
//            System.out.println(row);
//        }
//
//        Object username2 = result2.get(0).get("username");
//
//        System.out.println(username2);
//
//        String username = "SherlockHolmes";
//        String id = "47";
//
//        DBUtils.executeUpdate("UPDATE users SET username='"+username+"' WHERE id='"+id+"'");
//
//        List<List<Object>> userupdated = DBUtils.getQueryResultAsListOfLists("SELECT * from users where id='"+id+"'");
//
//        System.out.println(userupdated);
//
//        List<String> columnNames = DBUtils.getColumnNames("SELECT * from users where id='" + id + "'");
//
//        System.out.println(columnNames);


        List<List<Object>> list = DBUtils.getQueryResultAsListOfLists("select * from users");

        for (List<Object> eachRow : list) {
            System.out.println(eachRow);
        }


        List<Object> firstRow = list.get(0);

        Integer o = (Integer)firstRow.get(0);
        System.out.println(o.doubleValue());


        List<Map<String, Object>> listOfMaps = DBUtils.getQueryResultListOfMaps("select * from users");

        Map<String, Object> firstRow2 = listOfMaps.get(0);

//        System.out.println(firstRow2);

        LocalDateTime signUpDate = (LocalDateTime) firstRow2.get("signUpDate");

        System.out.println(signUpDate.getDayOfMonth());


        DBUtils.close();


    }
}
