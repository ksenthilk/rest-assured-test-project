import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import utility.AllureLogger;
import utility.BaseTest;
import utility.FrameworkConstants;

import static io.restassured.RestAssured.given;
public class Auth extends BaseTest {

    public static String CreateAuth(){
        AllureLogger.logToAllure("Starting test for POST method for auth creation");
        JSONObject jsonObject = returDefaultPayLoadObject(FrameworkConstants.POSTRequest_AUTH_DEFAULT_REQUEST);
        String api_key = readConfigurationFile("api_key");
        String username = readConfigurationFile("username");
        String password = readConfigurationFile("password");
        jsonObject.put("api_key", api_key);
        jsonObject.put("username", username);
        jsonObject.put("password", password);
        AllureLogger.logToAllure("api_key from config file: " + api_key);
        AllureLogger.logToAllure("username from config file: " + username);
        AllureLogger.logToAllure("password from config file: " + password);

        RequestSpecification given = given();
        given.spec(requestSpec);
        given.contentType("application/json");
        given.body(jsonObject.toJSONString());
        given.when();
        Response response = given.
                post("forecast?id=524901&appid={api_key}");

        AllureLogger.logToAllure("Checking if the response status code is 200");
        response.then().spec(responseSpec);
        String token = response.then().extract().path("token");
        return token;

    }


}