package pojoClasses;
import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherDetails {
    @JsonProperty
    private String City;

    @JsonProperty
    private int ZipCode;

    @JsonProperty
    private String api_key;

    public String getCity() {
        return City;
    }
    public void setCity(String City) {
        this.City = City;
    }

    public int getZipCode() {
        return ZipCode;
    }
    public void setZipCode(int ZipCode) {
        this.ZipCode = ZipCode;
    }

    public String getApi_key() {
        return api_key;
    }
    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }
}