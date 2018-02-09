package by.tr.web.dao.impl.show;

import by.tr.web.dao.configuration.ConnectionPool;
import by.tr.web.dao.exception.DAOException;
import by.tr.web.dao.show.ShowDAOSqlImpl;
import by.tr.web.domain.Country;
import by.tr.web.domain.Review;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ShowDAOSqlImplTest {
    private ShowDAOSqlImpl showDAO = new ShowDAOSqlImpl();
    private static ConnectionPool connectionPool;


    @BeforeClass
    public static void initConnectionPool() {
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
    }

    @AfterClass
    public static void destroyConnectionPool() {
        connectionPool.dispose();
    }


    @Test
    public void testTakeCountryListSuccessfully() throws DAOException {
        List<Country> countryList = formCountryList();
        String lang = "en";
        List<Country> actualCountryList = showDAO.takeCountryList(lang);

        Assert.assertEquals(countryList, actualCountryList);
    }

    @Test
    public void testTakeShowReviewListSuccessfully() throws DAOException {
        List<Review> expectedReviewList = new ArrayList<>();
        int showId = 29;
        List<Review> actualReviewList = showDAO.takeShowReviewList(0, 5, showId);
        Assert.assertEquals(expectedReviewList, actualReviewList);
    }


    private List<Country> formCountryList() {
        List<Country> countryList = new ArrayList<>();
        String countryNames[] = {"Russia",
                "USA",
                "Australia",
                "Belgium",
                "UK",
                "Germany",
                "India",
                "Ireland",
                "Spain",
                "China",
                "France",
                "Canada",
                "Malta",
                "Taiwan"};

        int countryId = 1;
        for (String name : countryNames) {
            Country country = new Country(countryId, name);
            countryList.add(country);
            countryId++;
        }
        return countryList;
    }

}
