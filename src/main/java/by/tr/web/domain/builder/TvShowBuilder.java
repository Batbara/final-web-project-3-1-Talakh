package by.tr.web.domain.builder;

import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.TVChannel;
import by.tr.web.domain.TvShow;
import by.tr.web.domain.UserReview;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class TvShowBuilder extends ShowBuilder {

    private TvShow.ShowStatus showStatus;
    private int seasonsNum;
    private int episodesNum;
    private int finishedYear;
    private TVChannel channel;
    
    public TvShowBuilder(){
        showStatus = TvShow.ShowStatus.RETURNING;
        channel = new TVChannel();
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
    public TvShowBuilder addId(int showID) {
        super.addId(showID);
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
    public TvShowBuilder addReviews(List<UserReview> reviews) {
        super.addReviews(reviews);
        return this;
    }

    public TvShowBuilder addShowStatus(TvShow.ShowStatus showStatus) {
        this.showStatus = showStatus;
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

    public TvShowBuilder addChannel(TVChannel channel) {
        this.channel = channel;
        return this;
    }

    @Override
    public TvShow create() {
        TvShow tvShow = new TvShow();
        tvShow.setShowID(showID);
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
