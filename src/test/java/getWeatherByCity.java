import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojoClasses.WeatherDetails;
import utility.AllureLogger;
import utility.BaseTest;
import utility.EnvProperties;
import utility.ReadFromSheet;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class getWeatherByCity extends BaseTest {

    @Test(dataProvider = "test-data-sheet")
    @SuppressWarnings("unused")
    public void verifyWeatherByCity(String TestCase, String Execute, String City, String api_key)
    {
        String[] element_a = new String[4];
        element_a[0] = TestCase;
        System.out.println("TestCase - " + TestCase);
        element_a[1] = Execute;
        System.out.println("Execute - " + Execute);
        element_a[2] = City;
        System.out.println("City - " + City);
        element_a[3] = api_key;
        System.out.println("api_key - " + api_key);

//      There is an exception when just using raw url, trying urlEncoding
//        java.lang.IllegalArgumentException: Invalid number of path parameters. Expected 0, was 2. Redundant path parameters are:
//        %20api.openweathermap.org%2Fdata%2F2.5%2Fweather%3Fq%3DLos%20Angeles%26appid%3D6822ee95d07221f369e0aedd6c86e889
//        String API = EnvProperties.getApiBaseUrl() + TestCase + "?q=" + City + "&appid=" + api_key; >> Works
//        String BaseURI = EnvProperties.getApiBaseUrl();

//        String PartURI = TestCase + "?q=" + City + "&appid=" + api_key;
//        String API = EnvProperties.getApiBaseUrl() + URLEncoder.encode(PartURI, StandardCharsets.UTF_8);

        String API = EnvProperties.getApiBaseUrl() + TestCase + "?q=" + City + "&appid=" + api_key;
        System.out.println("API that is about to be tested is - " + API);

        if (element_a[1].contentEquals("Yes"))
        {
            // Set a GET request to Cities and receive the weather description
//            RestAssured.urlEncodingEnabled = false;
            Response response = (Response) given().spec(requestSpec).
//                    pathParam("city", element_a[2]).
//                    pathParam("api_key", element_a[3]).
                        queryParam("city", element_a[2]).
                        queryParam("api_key", element_a[3]).
                    when().
                    // get("/weather?q={city}&appid={API_KEY}");
                                get("API").then().log().body();

            AllureLogger.logToAllure("Start  testing /weather?q={city name}&appid={API key} API to get the weather description SUCCESS");
            AllureLogger.logToAllure("Verify the response if the status code returned is 200");
            response.then().spec(responseSpec);
            AllureLogger.logToAllure("Asserting of response body with the data from test data excel");
            WeatherDetails weatherDetails = response.as(WeatherDetails.class);
            Assert.assertEquals(weatherDetails.getCity(), City);
            Assert.assertEquals(weatherDetails.getApi_key(), api_key);

        } else
            System.out.println("City is empty");
        AllureLogger.logToAllure("Start testing /weather?q={city name}&appid={API key} API to get the weather description FAILED");
    }


        @DataProvider(name = "test-data-sheet")
    public Object[][] testDataSheet() {
        Object[][] testData = null;
        try {
            testData = ReadFromSheet.ReadFromSheetOne("TestData.xlsx", "Weather");
        } catch (IOException e) {
            Assert.fail("Not able to read data from excel sheet. Please check the sheet under Test_Data and confirm");
        }
        return testData;
        }
}