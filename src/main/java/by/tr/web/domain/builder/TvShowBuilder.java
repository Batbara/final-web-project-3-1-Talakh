package by.tr.web.domain.builder;

import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.Review;
import by.tr.web.domain.TvChannel;
import by.tr.web.domain.TvShow;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TvShowBuilder extends ShowBuilder {

    private TvShow.ShowStatus showStatus;
    private int seasonsNum;
    private int episodesNum;
    private int finishedYear;
    private TvChannel channel;
    
    public TvShowBuilder(){
        showStatus = TvShow.ShowStatus.RETURNING;
        channel = new TvChannel();
        reviews = new ArrayList<>();
        genres = new ArrayList<>();
        countries = new ArrayList<>();
    }


    @Override
    public TvShowBuilder addTitle(String title) {
        super.addTitle(title);
        return this;
    }

    @Override
    public TvShowBuilder addSynopsis(String synopsis) {
        super.addSynopsis(synopsis);
        return this;
    }

    @Override
    public TvShowBuilder addYear(int year) {
        super.addYear(year);
        return this;
    }

    @Override
    public TvShowBuilder addGenres(List<Genre> genres) {
        super.addGenres(genres);
        return this;
    }

    @Override
    public TvShowBuilder addId(int showId) {
        super.addId(showId);
        return this;
    }

    @Override
    public TvShowBuilder addPoster(String poster) {
        super.addPoster(poster);
        return this;
    }

    @Override
    public TvShowBuilder addPremiereDate(Date premiereDate) {
        super.addPremiereDate(premiereDate);
        return this;
    }

    @Override
    public TvShowBuilder addRuntime(Time runtime) {
        super.addRuntime(runtime);
        return this;
    }

    @Override
    public TvShowBuilder addCountries(List<Country> countries) {
        super.addCountries(countries);
        return this;
    }

    @Override
    public TvShowBuilder addUserRating(double userRating) {
        super.addUserRating(userRating);
        return this;
    }

    @Override
    public TvShowBuilder addReviews(List<Review> reviews) {
        super.addReviews(reviews);
        return this;
    }

    public TvShowBuilder addShowStatus(String showStatus) {
        this.showStatus = TvShow.ShowStatus.valueOf(showStatus.toUpperCase());
        return this;
    }

    public TvShowBuilder addSeasonsNum(int seasonsNum) {
        this.seasonsNum = seasonsNum;
        return this;
    }

    public TvShowBuilder addEpisodesNum(int episodesNum) {
        this.episodesNum = episodesNum;
        return this;
    }

    public TvShowBuilder addFinishedYear(int finishedYear) {
        this.finishedYear = finishedYear;
        return this;
    }

    public TvShowBuilder addChannel(String channel) {
        this.channel = new TvChannel(channel);
        return this;
    }

    @Override
    public TvShow create() {
        TvShow tvShow = new TvShow();
        tvShow.setShowId(showId);
        tvShow.setShowType(showType);
        tvShow.setLanguage(language);
        tvShow.setTitle(title);
        tvShow.setSynopsis(synopsis);
        tvShow.setPoster(poster);
        tvShow.setYear(year);
        tvShow.setPremiereDate(premiereDate);
        tvShow.setRuntime(runtime);
        tvShow.setGenreList(genres);
        tvShow.setCountryList(countries);
        tvShow.setUserRating(userRating);
        tvShow.setReviewList(reviews);

        tvShow.setChannel(channel);
        tvShow.setEpisodesNum(episodesNum);
        tvShow.setSeasonsNum(seasonsNum);
        tvShow.setFinishedYear(finishedYear);
        tvShow.setShowStatus(showStatus);

        return tvShow;
    }
}
