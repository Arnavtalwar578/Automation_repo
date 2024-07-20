package weather;

public class CityStats {
    private String name;
    private double temperature;
    private double humidity;

    public CityStats(String name, double temperature, double humidity) {
        this.name = name;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public String getName() {
        return name;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
}