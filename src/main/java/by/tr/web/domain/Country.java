package by.tr.web.domain;

import java.io.Serializable;

public class Country implements Serializable {
    private static final long serialVersionUID = 6987578084084822484L;
    private int countryId;
    private String countryName;

    public Country() {
    }
    public Country(int countryId) {
        this.countryId = countryId;
    }

    public Country(String countryName) {
        this.countryName = countryName;
    }
    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Country country = (Country) o;

        return countryName.equals(country.countryName);
    }

    @Override
    public int hashCode() {
        return countryName.hashCode();
    }

    @Override
    public String toString() {
        return countryName;
    }
}
