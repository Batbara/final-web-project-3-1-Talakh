package by.tr.web.domain;

import java.io.Serializable;

public class Movie extends Show implements Serializable {
    private static final long serialVersionUID = -7910806745825801682L;

    private long boxOffice;
    private long budget;
    private MPAARating mpaaRating;

    public enum MPAARating {
        G, PG, PG_13, R, NC_17, NONE
    }

    public enum MovieOrderType {
        TITLE, YEAR, RATING
    }

    public Movie() {
        mpaaRating = MPAARating.G;
    }

    public Movie(int id) {
        this();
        this.setShowID(id);
    }

    public String getFormattedBoxOffice() {

        return getFormattedString(Long.toString(boxOffice));
    }

    public String getFormattedBudget() {

        return getFormattedString(Long.toString(budget));
    }

    public long getBoxOffice() {

        return boxOffice;
    }

    public void setBoxOffice(long boxOffice) {
        this.boxOffice =boxOffice;
    }

    public long getBudget() {

        return budget;
    }

    public void setBudget(long budget) {
        this.budget =budget;
    }

    public void setMpaaRating(MPAARating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public MPAARating getMpaaRating() {
        return mpaaRating;
    }
    public String getFormattedMpaaRating(){
        return mpaaRating.toString().replace("_", "-").toUpperCase();
    }
    public void setMpaaRating(String rating) {
        if (rating != null) {
            this.mpaaRating = MPAARating.valueOf(transformMPAAString(rating));
        } else {
            this.mpaaRating = Movie.MPAARating.NONE;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Movie movie = (Movie) o;

        if (boxOffice != movie.boxOffice) return false;
        if (budget != movie.budget) return false;
        return mpaaRating == movie.mpaaRating;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (boxOffice ^ (boxOffice >>> 32));
        result = 31 * result + (int) (budget ^ (budget >>> 32));
        result = 31 * result + (mpaaRating != null ? mpaaRating.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                super.toString() +
                " boxOffice='" + boxOffice + '\'' +
                ", budget='" + budget + '\'' +
                ", mpaaRating=" + mpaaRating +
                "} ";
    }

    private String transformMPAAString(String rating) {
        return rating.replace("-", "_").toUpperCase();
    }

    private static String getFormattedString(String input) {
        if (Long.parseLong(input) == 0) {
            return "-";
        }
        StringBuilder formattedBuilder = new StringBuilder();
        int lastIndex = input.length() - 1;
        for (int token = lastIndex, counter = 1; token >= 0; token--, counter++) {

            formattedBuilder.append(input.charAt(token));
            if (counter % 3 == 0) {
                formattedBuilder.append(" ");
            }

        }
        return formattedBuilder.reverse().toString();
    }

}
