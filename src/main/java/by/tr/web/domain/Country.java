package by.tr.web.domain;

import java.io.Serializable;

public class Country implements Serializable {
    private static final long serialVersionUID = 6987578084084822484L;
    private String countryName;

    public Country() {
        countryName = "";
    }

    public String getCountryName() {
        return countryName;
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
}
