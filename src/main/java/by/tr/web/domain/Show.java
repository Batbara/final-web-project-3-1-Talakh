package by.tr.web.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Show implements Serializable {
    private static final long serialVersionUId = 4088005724983165608L;

    protected int showId;

    protected String language;
    protected String title;
    protected String synopsis;
    protected String poster;

    protected int year;
    protected Date premiereDate;
    protected Time runtime;

    protected List<Genre> genreList;
    protected String[] genres;
    protected List<Country> countryList;
    protected String[] countries;

    protected double userRating;
    protected List<Review> reviewList;

    protected ShowType showType;

    public enum ShowType {
        MOVIE, TV_SERIES
    }
    public Show() {
        genreList = new ArrayList<>();
        countryList = new ArrayList<>();
        reviewList = new ArrayList<>();
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(Date premiereDate) {
        this.premiereDate = premiereDate;
    }

    public Time getRuntime() {
        return runtime;
    }

    public void setRuntime(Time runtime) {
        this.runtime = runtime;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public String[] getGenres() {
        return convertToStringArray(genreList);
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public String[] getCountries() {

        return convertToStringArray(countryList);
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public void addGenre(Genre genre) {
        this.genreList.add(genre);
    }

    public void addCountry(Country country) {
        this.countryList.add(country);
    }

    public void addReview(Review review) {
        this.reviewList.add(review);
    }

    public ShowType getShowType() {
        return showType;
    }

    public void setShowType(ShowType showType) {
        this.showType = showType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Show show = (Show) o;

        if (showId != show.showId) return false;
        if (year != show.year) return false;
        if (Double.compare(show.userRating, userRating) != 0) return false;
        if (title != null ? !title.equals(show.title) : show.title != null) return false;
        if (synopsis != null ? !synopsis.equals(show.synopsis) : show.synopsis != null) return false;
        if (poster != null ? !poster.equals(show.poster) : show.poster != null) return false;
        if (premiereDate != null ? !premiereDate.equals(show.premiereDate) : show.premiereDate != null) return false;
        if (runtime != null ? !runtime.equals(show.runtime) : show.runtime != null) return false;
        if (genreList != null ? !genreList.equals(show.genreList) : show.genreList != null) return false;
        if (countryList != null ? !countryList.equals(show.countryList) : show.countryList != null) return false;
        return reviewList != null ? reviewList.equals(show.reviewList) : show.reviewList == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = showId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (synopsis != null ? synopsis.hashCode() : 0);
        result = 31 * result + (poster != null ? poster.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + (premiereDate != null ? premiereDate.hashCode() : 0);
        result = 31 * result + (runtime != null ? runtime.hashCode() : 0);
        result = 31 * result + (genreList != null ? genreList.hashCode() : 0);
        result = 31 * result + (countryList != null ? countryList.hashCode() : 0);
        temp = Double.doubleToLongBits(userRating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (reviewList != null ? reviewList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Show{" +
                "showId=" + showId +
                ", title='" + title + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", poster='" + poster + '\'' +
                ", year=" + year +
                ", premiereDate=" + premiereDate +
                ", runtime=" + runtime +
                ", genreList=" + genreList +
                ", countryList=" + countryList +
                ", userRating=" + userRating +
                ", reviewList=" + reviewList +
                '}';
    }

    private <T> String[] convertToStringArray(List<T> list) {
        Object[] arr = list.toArray();
        String[] strings = new String[arr.length];
        for (int element = 0; element < arr.length; element++) {
            strings[element] = arr[element].toString();
        }
        return strings;
    }
}
