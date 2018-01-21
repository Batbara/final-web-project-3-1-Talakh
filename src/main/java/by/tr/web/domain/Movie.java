package by.tr.web.domain;

import java.io.Serializable;

public class Movie extends Show implements Serializable {
    private static final long serialVersionUID = -7910806745825801682L;

    private String boxOffice;
    private String budget;
    private MPAARating mpaaRating;

    public enum MPAARating {
        G, PG, PG_13, R, NC_17
    }

    public Movie() {
        mpaaRating = MPAARating.G;
    }

    public Movie(int id) {
        this();
        this.setShowID(id);
    }

    public String getBoxOffice() {

        return getFormattedString(boxOffice);
    }

    public void setBoxOffice(long boxOffice) {
        this.boxOffice = String.valueOf(boxOffice);
    }

    public String getBudget() {

        return getFormattedString(budget);
    }

    public void setBudget(long budget) {
        this.budget = String.valueOf(budget);
    }

    public void setMpaaRating(MPAARating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public MPAARating getMpaaRating() {
        return mpaaRating;
    }

    public void setMpaaRating(String rating) {
        this.mpaaRating = MPAARating.valueOf(transformMPAAString(rating));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Movie movie = (Movie) o;

        if (boxOffice != null ? !boxOffice.equals(movie.boxOffice) : movie.boxOffice != null) return false;
        if (budget != null ? !budget.equals(movie.budget) : movie.budget != null) return false;
        return mpaaRating == movie.mpaaRating;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (boxOffice != null ? boxOffice.hashCode() : 0);
        result = 31 * result + (budget != null ? budget.hashCode() : 0);
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
