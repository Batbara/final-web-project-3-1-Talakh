package by.tr.web.domain.builder;

import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Movie;
import by.tr.web.domain.Review;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MovieBuilder extends ShowBuilder {
    private long boxOffice;
    private long budget;
    private Movie.MPAARating mpaaRating;

    public MovieBuilder() {
        reviews = new ArrayList<>();
        genres = new ArrayList<>();
        countries = new ArrayList<>();
        mpaaRating = Movie.MPAARating.NONE;
    }

    @Override
    public MovieBuilder addTitle(String title) {
        super.addTitle(title);
        return this;
    }

    @Override
    public MovieBuilder addSynopsis(String synopsis) {
        super.addSynopsis(synopsis);
        return this;
    }

    @Override
    public MovieBuilder addYear(int year) {
        super.addYear(year);
        return this;
    }

    @Override
    public MovieBuilder addGenres(List<Genre> genres) {
        super.addGenres(genres);
        return this;
    }

    @Override
    public MovieBuilder addId(int showId) {
        super.addId(showId);
        return this;
    }

    @Override
    public MovieBuilder addPoster(String poster) {
        super.addPoster(poster);
        return this;
    }

    @Override
    public MovieBuilder addPremiereDate(Date premiereDate) {
        super.addPremiereDate(premiereDate);
        return this;
    }

    @Override
    public MovieBuilder addRuntime(Time runtime) {
        super.addRuntime(runtime);
        return this;
    }

    @Override
    public MovieBuilder addCountries(List<Country> countries) {
        super.addCountries(countries);
        return this;
    }

    @Override
    public MovieBuilder addUserRating(double userRating) {
        super.addUserRating(userRating);
        return this;
    }

    @Override
    public MovieBuilder addReviews(List<Review> reviews) {
        super.addReviews(reviews);
        return this;
    }

    public MovieBuilder addBoxOffice(long boxOffice) {
        this.boxOffice = boxOffice;
        return this;
    }

    public MovieBuilder addBudget(long budget) {
        this.budget = budget;
        return this;
    }

    public MovieBuilder addMpaaRating(String mpaaRating) {
        if (mpaaRating != null) {
            this.mpaaRating = Movie.MPAARating.valueOf(transformMPAAString(mpaaRating));
        } else {
            this.mpaaRating = Movie.MPAARating.NONE;
        }
        return this;
    }

    public Movie create() {
        Movie movie = new Movie();
        movie.setShowId(showId);
        movie.setShowType(showType);
        movie.setLanguage(language);
        movie.setTitle(title);
        movie.setSynopsis(synopsis);
        movie.setPoster(poster);
        movie.setYear(year);
        movie.setPremiereDate(premiereDate);
        movie.setRuntime(runtime);
        movie.setGenreList(genres);
        movie.setCountryList(countries);
        movie.setUserRating(userRating);
        movie.setReviewList(reviews);

        movie.setBoxOffice(boxOffice);
        movie.setBudget(budget);
        movie.setMpaaRating(mpaaRating);

        return movie;
    }

    private String transformMPAAString(String rating) {
        return rating.replace("-", "_").toUpperCase();
    }
}
