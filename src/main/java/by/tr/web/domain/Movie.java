package by.tr.web.domain;

import java.io.Serializable;

public class Movie extends Show implements Serializable {
    private static final long serialVersionUID = -7910806745825801682L;

    private long boxOffice;
    private long budget;
    private MPAARating rating;

    public enum MPAARating {
        G, PG, PG_13, R, NC_17
    }
    public Movie(){
        rating = MPAARating.G;
    }

    public long getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(long boxOffice) {
        this.boxOffice = boxOffice;
    }

    public long getBudget() {
        return budget;
    }

    public void setBudget(long budget) {
        this.budget = budget;
    }

    public MPAARating getRating() {
        return rating;
    }

    public void setRating(MPAARating rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Movie movie = (Movie) o;

        if (boxOffice != movie.boxOffice) return false;
        if (budget != movie.budget) return false;
        return rating == movie.rating;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (boxOffice ^ (boxOffice >>> 32));
        result = 31 * result + (int) (budget ^ (budget >>> 32));
        result = 31 * result + rating.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "boxOffice=" + boxOffice +
                ", budget=" + budget +
                ", rating=" + rating +
                "} " + super.toString();
    }
}
