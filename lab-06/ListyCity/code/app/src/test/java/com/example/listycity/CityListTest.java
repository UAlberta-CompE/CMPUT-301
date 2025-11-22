package com.example.listycity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the functionalities of the CityList class using JUnit.
 * <p>
 * Each test method checks a specific behavior of the CityList class
 * such as adding a city and verifying that duplicates are not allowed.
 * </p>
 */
class CityListTest {

    /**
     * Creates and returns a mock CityList object with one sample City added.
     * <p>
     * This helper method is reused in multiple test methods so that we don’t repeat setup code.
     * </p>
     *
     * @return a CityList object containing one mock City
     */
    private CityList mockCityList() {
        CityList cityList = new CityList();
        cityList.add(mockCity());
        return cityList;
    }

    /**
     * Creates and returns a mock City object.
     * <p>
     * Used to generate a consistent test city without typing the same code multiple times.
     * </p>
     *
     * @return a City object with name "Edmonton" and province "Alberta"
     */
    private City mockCity() {
        return new City("Edmonton", "Alberta");
    }

    /**
     * Tests whether a City can be successfully added to the CityList.
     * <p>
     * Steps:
     * <ul>
     *   <li>Start with one mock city already in the list.</li>
     *   <li>Add another City object using add().</li>
     *   <li>Verify the total size increases and that the new city is actually present.</li>
     * </ul>
     * </p>
     */
    @Test
    void testAdd() {
        CityList cityList = mockCityList();   // start with one city
        assertEquals(1, cityList.getCities().size());   // verify initial size = 1

        // create a new city and add it
        City city = new City("Regina", "Saskatchewan");
        cityList.add(city);

        // check that size increased and the new city exists in the list
        assertEquals(2, cityList.getCities().size());
        assertTrue(cityList.getCities().contains(city));
    }

    /**
     * Tests that adding a duplicate City throws an IllegalArgumentException.
     * <p>
     * Steps:
     * <ul>
     *   <li>Create a mock CityList with one city.</li>
     *   <li>Add a second city (different from the mock one).</li>
     *   <li>Try adding the same second city again — should throw an exception.</li>
     * </ul>
     * </p>
     */
    @Test
    void testAddException() {
        CityList cityList = mockCityList();   // create base list
        City city = new City("Yellowknife", "Northwest Territories");
        cityList.add(city);

        // adding the same city again should cause an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            cityList.add(city);
        });
    }

    /**
     * Tests whether the getCities() method in CityList returns a properly sorted list of City objects.
     * <p>
     * The sorting is done alphabetically (lexicographically) by city name,
     * based on the compareTo() method implemented in the City class.
     * </p>
     */
    @Test
    void testGetCities() {
        // Create a mock CityList with one city ("Edmonton", "Alberta")
        CityList cityList = mockCityList();

        // Verify that the first city in the list is equal to the mock city
        // compareTo() returns 0 when two city names are the same
        assertEquals(0, mockCity().compareTo(cityList.getCities().get(0)));

        // Create a new City object with a name that comes *before* "Edmonton" alphabetically
        // "Charlottetown" < "Edmonton", so it should appear earlier when sorted
        City city = new City("Charlottetown", "Prince Edward Island");

        // Add the new city to the list
        cityList.add(city);

        // After sorting, "Charlottetown" should now appear first in the list (index 0)
        assertEquals(0, city.compareTo(cityList.getCities().get(0)));

        // The original mock city ("Edmonton") should now be second (index 1)
        assertEquals(0, mockCity().compareTo(cityList.getCities().get(1)));
    }

    /**
     * Tests whether the hasCity() method correctly identifies
     * if a city exists or not in the CityList.
     */
    @Test
    void testHasCity() {
        // Create a list containing one mock city ("Edmonton")
        CityList cityList = mockCityList();

        // The mock city should exist
        assertTrue(cityList.hasCity(mockCity()));

        // A new city not added yet should not exist
        City city = new City("Calgary", "Alberta");
        assertFalse(cityList.hasCity(city));
    }

    /**
     * Tests that the delete() method successfully removes a city from the list.
     */
    @Test
    void testDelete() {
        // Create a list containing one mock city ("Edmonton")
        CityList cityList = mockCityList();

        // Verify initial count is 1
        assertEquals(1, cityList.countCities());

        // Delete the city
        cityList.delete(mockCity());

        // After deletion, list should be empty
        assertEquals(0, cityList.countCities());
        assertFalse(cityList.hasCity(mockCity()));
    }

    /**
     * Tests that delete() throws an IllegalArgumentException
     * when attempting to remove a city that does not exist in the list.
     */
    @Test
    void testDeleteException() {
        CityList cityList = mockCityList();   // has only "Edmonton"
        City city = new City("Karachi", "Sindh");

        // "Karachi" was never added, so this should throw an exception
        assertThrows(IllegalArgumentException.class, () -> {
            cityList.delete(city);
        });
    }

    /**
     * Tests that countCities() correctly returns
     * the number of cities in the CityList.
     */
    @Test
    void testCountCities() {
        CityList cityList = mockCityList();   // starts with one city
        assertEquals(1, cityList.countCities()); // verify count = 1

        // Add another city and verify the count increases
        City city = new City("Lahore", "Punjab");
        cityList.add(city);
        assertEquals(2, cityList.countCities());

        // Delete one and verify the count decreases
        cityList.delete(city);
        assertEquals(1, cityList.countCities());
    }

}
