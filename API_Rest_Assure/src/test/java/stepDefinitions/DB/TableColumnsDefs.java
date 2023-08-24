package stepDefinitions.DB;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import stepDefinitions.SharedData;
import utils.DBUtils;

import java.util.ArrayList;
import java.util.List;



//------------------The database should have "tbl_users" table which should contain the
//        following columns--------------------------

public class TableColumnsDefs {

    SharedData sharedData;

    public TableColumnsDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }

//    ArrayList<String> list2;
    @When("I retrieve the column names from the {string} table")
    public void i_retrieve_the_column_names_from_the_table(String tableName) {
     List<List<Object>> list = DBUtils.getQueryResultAsListOfLists("describe "+tableName);

        System.out.println(list);
        sharedData.setDbColumnNames(new ArrayList<>());

//        list2 = new ArrayList<>();
        for (List<Object> each : list) {
//           list2.add((String) each.get(0));
            sharedData.getDbColumnNames().add((String) each.get(0));
        }

    }
    @Then("it should have the following list")
    public void it_should_have_the_following_list(List<String> expected) {
        System.out.println(expected);
        Assert.assertEquals(expected,sharedData.getDbColumnNames());

    }

}
