import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojoClasses.WeatherDetails;
import utility.AllureLogger;
import utility.BaseTest;
import utility.ExcelLib;

import static io.restassured.RestAssured.given;

public class getWeatherByCityByZip extends BaseTest {

    @DataProvider(name = "DataFromExcel")
    public Object[][] data() throws Exception {
        ExcelLib xl = new ExcelLib("Weather", this.getClass().getSimpleName());
        return xl.getTestdata();
    }

    //Test Case 1 - See Weather use cases in Excel
    @Test(dataProvider = "DataFromExcel",
            description = "To get how the weather feels in all the cities provided in the spreadsheet")
    @SuppressWarnings("unused")
    public void GetWeatherByCity(String City,
                                 String api_key
    ){
        //Logs
        AllureLogger.logToAllure("Start testing /weather?q={city name}&appid={API key} API to get the weather description");

        System.out.println("City name = " + City);
        System.out.println("API Key is - " + api_key);

        // Set a GET request to Cities and receive the weather description
        Response response = given().spec(requestSpec).
                pathParam("city", City).
                pathParam("API_KEY", api_key).
                when().
               // get("/weather?q={city}&appid={API_KEY}");
                       get("/weather?q={city}");
        // Verify the response code
        AllureLogger.logToAllure("Verify the response if the status code returned is 200");
        response.then().spec(responseSpec);
        AllureLogger.logToAllure("Asserting of response body with the data from test data excel");

        WeatherDetails weatherDetails = response.as(WeatherDetails.class);
        Assert.assertEquals(weatherDetails.getCity(),City);
        Assert.assertEquals(weatherDetails.getApi_key(),api_key);
    }
}