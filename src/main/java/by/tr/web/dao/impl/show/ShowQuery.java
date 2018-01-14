package by.tr.web.dao.impl.show;

public final class ShowQuery {
    public final static String GENRE_OF_SHOW_QUERY =
            "SELECT genre_name" +
            " FROM genre AS g" +
            "     INNER JOIN show_genres AS sg" +
            "       ON (sg.genre_id = g.genre_id)" +
            "  WHERE sg.show_id = ?" +
            "   AND g.genre_lang = ?";
    public final static String COUNTRY_OF_SHOW_QUERY =
            "SELECT country_name" +
            " FROM country AS c" +
            "     INNER JOIN show_country AS sc" +
            "       ON (sc.country_id = c.country_id)" +
            "  WHERE sc.show_id = ?" +
            "   AND c.country_lang = ?";
    public final static String REVIEWS_OF_SHOW_QUERY =
            "SELECT r.user_id, u.user_name,"+
            "      review_rate, review_content, "+
            "      review_date"+
            "  FROM review AS r"+
            "   INNER JOIN `user` AS u"+
            "       ON (r.user_id = u.user_id)"+
            " WHERE show_id = ?";
    private ShowQuery(){}
}
