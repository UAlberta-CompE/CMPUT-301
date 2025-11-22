package com.example.listycity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a class that keeps a list of City objects.
 */
public class CityList {
    private List<City> cities = new ArrayList<>();

    /**
     * Adds a City to the list if it does not already exist.
     *
     * @param city the City object to add
     * @throws IllegalArgumentException if the city already exists in the list
     */
    public void add(City city) {
        if (cities.contains(city)) {
            throw new IllegalArgumentException();
        }
        cities.add(city);
    }

    /**
     * Returns a sorted list of City objects.
     *
     * @return a list of cities sorted alphabetically by city name
     */
    public List<City> getCities() {
        List<City> list = cities;
        Collections.sort(list);
        return list;
    }

    /**
     * Checks whether a specific City object exists in the list.
     *
     * @param city the City object to check
     * @return true if the city exists in the list, false otherwise
     */
    public boolean hasCity(City city) {
        return cities.contains(city);
    }

    /**
     * Removes a City object from the list if it exists.
     * <p>
     * If the city does not exist, this method throws an IllegalArgumentException.
     * </p>
     *
     * @param city the City object to remove
     * @throws IllegalArgumentException if the city is not found in the list
     */
    public void delete(City city) {
        if (!cities.contains(city)) {
            throw new IllegalArgumentException();
        }
        cities.remove(city);
    }

    /**
     * Returns the number of City objects currently in the list.
     *
     * @return the total count of cities
     */
    public int countCities() {
        return cities.size();
    }
}
