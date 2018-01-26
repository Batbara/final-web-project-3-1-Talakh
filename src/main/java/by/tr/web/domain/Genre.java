package by.tr.web.domain;

import java.io.Serializable;

public class Genre implements Serializable {
    private static final long serialVersionUID = -6433748996330506545L;
    private String genreName;

    public Genre() {
    }
    public Genre(String genreName){
        this.genreName = genreName;
    }
    public String getGenreName() {
        return genreName;
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
