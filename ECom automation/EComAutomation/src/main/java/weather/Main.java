package weather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.opencsv.exceptions.CsvValidationException;

public class Main {
    private static final String API_KEY = "4d3969938c6fd1d46ea9f2703ee6c82a";
    private static final String CITY_CSV = "city.csv";
    private static final String STATS_CSV = "city_stats.csv";
    private static final int TOP_N = 3;

    public static void main(String[] args) throws CsvValidationException {
        WeatherService weatherService = new WeatherService(API_KEY);

        // Read cities from CSV
        List<String> cityNames = CSVUtil.readCities(CITY_CSV);

        List<CityStats> cityStatsList = new ArrayList<>();
        for (String cityName : cityNames) {
            try {
                City city = weatherService.getCityCoordinates(cityName);
                CityStats stats = weatherService.getWeatherStats(city);
                cityStatsList.add(stats);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }}}