package com.example.lab08;

// City class stores city and province
public class City {
    private String city;
    private String province;

    public City(String city, String province){
        this.city = city;
        this.province = province;
    }

    public String getCityName(){
        return this.city;
    }

    public String getProvinceName(){
        return this.province;
    }

    // To make "contains" work properly in tests
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City cityObj = (City) o;
        return this.city.equals(cityObj.city)
                && this.province.equals(cityObj.province);
    }

    @Override
    public int hashCode() {
        return city.hashCode() + province.hashCode();
    }
}
