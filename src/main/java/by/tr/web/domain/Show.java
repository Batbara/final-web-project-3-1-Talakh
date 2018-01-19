package by.tr.web.domain;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Show implements Serializable {
    private static final long serialVersionUID = 4088005724983165608L;

    private int showID;

    private String title;
    private String synopsis;
    private String poster;

    private int year;
    private Date premiereDate;
    private Time runtime;

    private List<Genre> genreList;
    private List<Country> countryList;

    private double userRating;
    private List<UserReview> reviewList;

    public Show() {
        genreList = new ArrayList<>();
        countryList = new ArrayList<>();
        reviewList = new ArrayList<>();
    }

    public int getShowID() {
        return showID;
    }

    public void setShowID(int showID) {
        this.showID = showID;
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

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public List<Country> getCountryList() {
        return countryList;
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

    public void addReview(UserReview review) {
        this.reviewList.add(review);
        userRating = calculateAvgRating();
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

    public String getFormattedUserRating() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(userRating);
    }

    public double getUserRating() {
        return userRating;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }

    public List<UserReview> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<UserReview> reviewList) {
        this.reviewList = reviewList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Show show = (Show) o;

        if (showID != show.showID) return false;
        if (year != show.year) return false;
        if (userRating != show.userRating) return false;
        if (!title.equals(show.title)) return false;
        if (!synopsis.equals(show.synopsis)) return false;
        if (!poster.equals(show.poster)) return false;
        if (!premiereDate.equals(show.premiereDate)) return false;
        if (!runtime.equals(show.runtime)) return false;
        if (!genreList.equals(show.genreList)) return false;
        return countryList.equals(show.countryList);

    }

    @Override
    public int hashCode() {
        int result = showID;
        result = 31 * result + title.hashCode();
        result = 31 * result + synopsis.hashCode();
        result = 31 * result + poster.hashCode();
        result = 31 * result + year;
        result = 31 * result + premiereDate.hashCode();
        result = 31 * result + runtime.hashCode();
        result = 31 * result + genreList.hashCode();
        result = 31 * result + countryList.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Show{" +
                "showID=" + showID +
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

    private double calculateAvgRating() {
        double avgRating = 0;
        for (UserReview review : reviewList) {
            avgRating += review.getUserRate();
        }
        avgRating = avgRating / reviewList.size();
        return avgRating;
    }
}
