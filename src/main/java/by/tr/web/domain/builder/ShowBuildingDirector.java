package by.tr.web.domain.builder;

import by.tr.web.domain.Show;

public class ShowBuildingDirector {
    private ShowBuilder showBuilder;
    private String showType;

    public ShowBuildingDirector() {
    }

    public void setShowType(String showType) {
        this.showType = showType;
        Show.ShowType builderType = Show.ShowType.valueOf(showType.toUpperCase());

        if (builderType == Show.ShowType.MOVIE) {
            setShowBuilder(new MovieBuilder());
            return;
        }
        if (builderType == Show.ShowType.TV_SERIES) {
            setShowBuilder(new TvShowBuilder());
        }

    }

    public void setShowBuilder(ShowBuilder showBuilder) {
        this.showBuilder = showBuilder;
    }
    public Show create(int showId, String showTitle, int year, double rating){
        Show show = showBuilder
                .addId(showId)
                .addTitle(showTitle)
                .addYear(year)
                .addUserRating(rating)
                .addShowType(showType)
                .create();
        return show;
    }
}
