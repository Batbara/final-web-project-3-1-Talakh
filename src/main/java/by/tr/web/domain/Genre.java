package by.tr.web.domain;

import java.io.Serializable;

public class Genre implements Serializable {
    private static final long serialVersionUID = -6433748996330506545L;
    private int genreId;
    private String genreName;

    public Genre() {
    }
    public Genre(int genreId) {
        this.genreId = genreId;
    }
    public Genre(String genreName){
        this.genreName = genreName;
    }

    public Genre(int genreId, String genreName){
        this.genreId = genreId;
        this.genreName = genreName;
    }

    public int getGenreId() {
        return genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Genre genre = (Genre) o;

        return genreName.equals(genre.genreName);
    }

    @Override
    public int hashCode() {
        return genreName.hashCode();
    }

    @Override
    public String toString() {
        return genreName;
    }
}
