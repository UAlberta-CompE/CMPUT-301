package com.example.listycity;

/**
 * Represents a city with its name and province.
 * <p>
 * This class stores basic information about a city,
 * including its name and the province it belongs to.
 * </p>
 */
public class City implements Comparable<City> {

    /**
     * The name of the city.
     */
    private String city;

    /**
     * The name of the province that the city belongs to.
     */
    private String province;

    /**
     * Constructs a City object with the specified city and province names.
     *
     * @param city     the name of the city (e.g., "Edmonton")
     * @param province the name of the province (e.g., "Alberta")
     */
    public City(String city, String province) {
        this.city = city;
        this.province = province;
    }

    /**
     * Returns the name of this city.
     *
     * @return the city's name
     */
    public String getCityName() {
        return this.city;
    }

    /**
     * Returns the name of the province where this city is located.
     *
     * @return the province's name
     */
    public String getProvinceName() {
        return this.province;
    }

    /**
     * Compares this City object with another City object for order.
     * Cities are compared lexicographically by their city name.
     *
     * @param other the other City object to compare to
     * @return a negative integer, zero, or a positive integer as this city's
     *         name is less than, equal to, or greater than the specified city's name
     */
    @Override
    public int compareTo(City other) {
        return this.city.compareTo(other.getCityName());
    }
}
