package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.ConfigReader;
import utils.DBUtils;
import utils.Driver;

import java.time.Duration;

public class Hooks {



    @Before("not (@db_only or @api_only)")  // before each scenario
    public void setupScenario(){
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Driver.getDriver().get(ConfigReader.getProperty("url"));

    }

    @Before("@DB")
    public void setupScenarioForDB(){
        DBUtils.createConnection();

    }

    @Before("@API")
    public void setupScenarioForAPI(){
        RestAssured.baseURI = ConfigReader.getProperty("api.base.uri");

    }

//    @Before(order = 2)  // before each scenario
//    public void setupScenario2(){
//        System.out.println("Second Before Hook");
//    }

    @After ("not (@db_only or @api_only)") // after each scenario
    public void tearDownScenario(Scenario scenario){
        if(scenario.isFailed()){
            byte[] screenshotFile = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshotFile, "image/png", "screenshot");
        }

        Driver.quitDriver();
    }

    @After ("@DB") // after each scenario
    public void tearDownScenario2(){
       DBUtils.close();

    }


//    @BeforeAll
//    public static void beforeAllScenarios(){
//        System.out.println("Before all sceanrios");
//        // establish db connection
//    }
//
//    @AfterAll
//    public static void afterAllScenarios(){
//        System.out.println("After all sceanrios");
//        // close db connection
//    }

//    @BeforeStep
//    public void beforeEachStep(){
//        System.out.println("Before step");
//    }
//
//    @AfterStep
//    public void afterEachStep(){
//        System.out.println("After step");
//    }


}
