package weather;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;

public class WeatherServiceTest {
    private static final String API_KEY = "4d3969938c6fd1d46ea9f2703ee6c82a";
    private WeatherService weatherService;
    private List<String> cityNames;

    @BeforeClass
    public void setUp() throws CsvValidationException {
        weatherService = new WeatherService(API_KEY);
        cityNames = CSVUtil.readCities("src/test/resources/city_test.csv");
    }

    @Test
    public void testGetCityCoordinates() {
        for (String cityName : cityNames) {
            try {
                City city = weatherService.getCityCoordinates(cityName);
                Assert.assertNotNull(city, "City should not be null");
                Assert.assertEquals(city.getName(), cityName, "City name should match");
                Assert.assertTrue(city.getLatitude() != 0 && city.getLongitude() != 0, "Coordinates should not be zero");
            } catch (IOException e) {
                Assert.fail("Exception thrown: " + e.getMessage());
            }
        }
    }

    @Test
    public void testGetWeatherStats() {
        for (String cityName : cityNames) {
            try {
                City city = weatherService.getCityCoordinates(cityName);
                CityStats stats = weatherService.getWeatherStats(city);
                Assert.assertNotNull(stats, "CityStats should not be null");
                Assert.assertEquals(stats.getName(), cityName, "City name should match");
                Assert.assertTrue(stats.getTemperature() != 0, "Temperature should not be zero");
                Assert.assertTrue(stats.getHumidity() != 0, "Humidity should not be zero");
            } catch (IOException e) {
                Assert.fail("Exception thrown: " + e.getMessage());
            }
        }
    }

    @Test
    public void testSaveStatsToCSV() throws CsvValidationException {
        List<CityStats> cityStatsList = cityNames.stream().map(cityName -> {
            try {
                City city = weatherService.getCityCoordinates(cityName);
                return weatherService.getWeatherStats(city);
            } catch (IOException e) {
                Assert.fail("Exception thrown: " + e.getMessage());
                return null;
            }
        }).collect(java.util.stream.Collectors.toList());

        String statsFile = "src/test/resources/city_stats_test.csv";
        CSVUtil.writeStats(statsFile, cityStatsList);

        // Read the file again and check if the data matches
        List<CityStats> readStats = CSVUtil.readStats(statsFile);
        Assert.assertEquals(cityStatsList.size(), readStats.size(), "Number of entries should match");

        for (int i = 0; i < cityStatsList.size(); i++) {
            CityStats original = cityStatsList.get(i);
            CityStats read = readStats.get(i);
            Assert.assertEquals(original.getName(), read.getName(), "City name should match");
            Assert.assertEquals(original.getTemperature(), read.getTemperature(), "Temperature should match");
            Assert.assertEquals(original.getHumidity(), read.getHumidity(), "Humidity should match");
        }
    }

    @Test
    public void testTopNColdestCities() {
        List<CityStats> cityStatsList = cityNames.stream().map(cityName -> {
            try {
                City city = weatherService.getCityCoordinates(cityName);
                return weatherService.getWeatherStats(city);
            } catch (IOException e) {
                Assert.fail("Exception thrown: " + e.getMessage());
                return null;
            }
        }).collect(java.util.stream.Collectors.toList());

        int topN = 3;
        List<CityStats> topNColdestCities = cityStatsList.stream()
                .sorted(java.util.Comparator.comparingDouble(CityStats::getTemperature))
                .limit(topN)
                .collect(java.util.stream.Collectors.toList());

        Assert.assertEquals(topNColdestCities.size(), topN, "Top N coldest cities size should match");

        for (int i = 1; i < topNColdestCities.size(); i++) {
            Assert.assertTrue(topNColdestCities.get(i - 1).getTemperature() <= topNColdestCities.get(i).getTemperature(),
                    "Cities should be sorted by temperature");
        }
    }

    @Test
    public void testTopNHighestHumidityCities() {
        List<CityStats> cityStatsList = cityNames.stream().map(cityName -> {
            try {
                City city = weatherService.getCityCoordinates(cityName);
                return weatherService.getWeatherStats(city);
            } catch (IOException e) {
                Assert.fail("Exception thrown: " + e.getMessage());
                return null;
            }
        }).collect(java.util.stream.Collectors.toList());

        int topN = 3;
        List<CityStats> topNHighestHumidityCities = cityStatsList.stream()
                .sorted(java.util.Comparator.comparingDouble(CityStats::getHumidity).reversed())
                .limit(topN)
                .collect(java.util.stream.Collectors.toList());

        Assert.assertEquals(topNHighestHumidityCities.size(), topN, "Top N highest humidity cities size should match");

        for (int i = 1; i < topNHighestHumidityCities.size(); i++) {
            Assert.assertTrue(topNHighestHumidityCities.get(i - 1).getHumidity() >= topNHighestHumidityCities.get(i).getHumidity(),
                    "Cities should be sorted by humidity");
        }
    }
}

