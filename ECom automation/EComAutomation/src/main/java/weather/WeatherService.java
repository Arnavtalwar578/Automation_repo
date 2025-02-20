package weather;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherService {
    private static final String GEO_API_URL = "http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=1&appid=%s";
    private static final String WEATHER_API_URL = "https://api.openweathermap.org/data/3.0/onecall?lat=%s&lon=%s&appid=%s";
    private String apiKey;

    public WeatherService(String apiKey) {
        this.apiKey = apiKey;
    }

    public City getCityCoordinates(String cityName) throws IOException {
        String url = String.format(GEO_API_URL, cityName, apiKey);
        String response = sendGetRequest(url);
        JSONArray jsonArray = new JSONArray(response);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        double lat = jsonObject.getDouble("lat");
        double lon = jsonObject.getDouble("lon");
        return new City(cityName, lat, lon);
    }

    public CityStats getWeatherStats(City city) throws IOException {
        String url = String.format(WEATHER_API_URL, city.getLatitude(), city.getLongitude(), apiKey);
        String response = sendGetRequest(url);
        JSONObject jsonObject = new JSONObject(response).getJSONObject("current");
        double temp = jsonObject.getDouble("temp");
        double humidity = jsonObject.getDouble("humidity");
        return new CityStats(city.getName(), temp, humidity);
    }

    private String sendGetRequest(String url) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request)) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);
                System.out.println("API Response: " + responseBody); // Print the response
                return responseBody;
            }
        }
    }

    public List<City> getCitiesFromCSV(String filePath) {
        List<City> cities = new ArrayList<>();
        // Logic to read CSV and populate cities list
        return cities;
    }

    public void saveStatsToCSV(List<CityStats> cityStatsList, String filePath) {
        // Logic to save stats to CSV
    }
}
