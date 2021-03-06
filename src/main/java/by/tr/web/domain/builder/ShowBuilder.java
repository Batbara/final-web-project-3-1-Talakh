package by.tr.web.domain.builder;

import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Review;
import by.tr.web.domain.Show;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public abstract class ShowBuilder {
    protected int showId;

    protected String language;
    protected String title;
    protected String synopsis;
    protected String poster;

    protected int year;
    protected Date premiereDate;
    protected Time runtime;

    protected List<Genre> genres;
    protected List<Country> countries;

    protected double userRating;
    protected List<Review> reviews;
    protected Show.ShowType showType;

    public ShowBuilder addLanguage(String language){
        this.language = language;
        return this;
    }
    public ShowBuilder addTitle(String title){
        this.title = title;
        return this;
    }
    public ShowBuilder addSynopsis(String synopsis){
        this.synopsis = synopsis;
        return this;
    }
    public ShowBuilder addYear (int year){
        this.year = year;
        return this;
    }
    public ShowBuilder addGenres(List<Genre> genres){
        this.genres = genres;
        return this;
    }

    public ShowBuilder addId(int showId) {
        this.showId = showId;
        return this;
    }

    public ShowBuilder addPoster(String poster) {
        this.poster = poster;
        return this;
    }

    public ShowBuilder addPremiereDate(Date premiereDate) {
        this.premiereDate = premiereDate;
        return this;
    }

    public ShowBuilder addRuntime(Time runtime) {
        this.runtime = runtime;
        return this;
    }

    public ShowBuilder addCountries(List<Country> countries) {
        this.countries = countries;
        return this;
    }

    public ShowBuilder addUserRating(double userRating) {
        this.userRating = userRating;
        return this;
    }

    public ShowBuilder addReviews(List<Review> reviews) {
        this.reviews = reviews;
        return this;
    }
    public ShowBuilder addShowType(String showType) {
        this.showType = Show.ShowType.valueOf(showType.toUpperCase());
        return this;
    }

    public abstract Show create();
}
