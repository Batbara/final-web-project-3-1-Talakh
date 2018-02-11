package by.tr.web.service.show;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.domain.builder.MovieBuilder;
import by.tr.web.domain.builder.ShowBuilder;
import by.tr.web.domain.builder.TvShowBuilder;
import by.tr.web.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ShowBuildService {

    /**
     * Retrieve basic {@link by.tr.web.domain.Show} data from request and setup {@link ShowBuilder}
     *
     * @param request     HttpServletRequest object containing Show data
     * @param showBuilder {@link ShowBuilder} object to fill Show data
     * @return {@link ShowBuilder} with all provided data
     * @throws ServiceException If application error occurs
     * @throws IOException      When I/O error occurs
     * @throws ServletException When HttpServletRequest error occurs
     */
    ShowBuilder retrieveBasicShowInfo(HttpServletRequest request, ShowBuilder showBuilder)
            throws ServiceException, IOException, ServletException;

    /**
     * Retrieve {@link by.tr.web.domain.Show} language dependent information from request and setup {@link ShowBuilder}
     *
     * @param request     HttpServletRequest object containing Show data
     * @param showBuilder {@link ShowBuilder} object to fill Show data
     * @param language    {@link FrontControllerParameter.Language} object specifying client language
     * @return {@link ShowBuilder} with all provided data
     * @throws ServiceException If application error occurs
     * @throws IOException      When I/O error occurs
     * @throws ServletException When HttpServletRequest error occurs
     */
    ShowBuilder retrieveShowInfoTranslation(HttpServletRequest request, ShowBuilder showBuilder,
                                            FrontControllerParameter.Language language)
            throws ServiceException, IOException, ServletException;

    /**
     * Retrieve basic {@link by.tr.web.domain.Movie} data from request and setup {@link MovieBuilder}
     *
     * @param request      HttpServletRequest object containing Movie data
     * @param movieBuilder {@link MovieBuilder} object to fill Movie data
     * @return {@link MovieBuilder} with all provided data
     * @throws ServiceException If application error occurs
     * @throws IOException      When I/O error occurs
     * @throws ServletException When HttpServletRequest error occurs
     */
    MovieBuilder retrieveMovieInfo(HttpServletRequest request, MovieBuilder movieBuilder)
            throws ServiceException, IOException, ServletException;

    /**
     * Retrieve basic {@link by.tr.web.domain.TvShow} data from request and setup {@link TvShowBuilder}
     *
     * @param request       HttpServletRequest object containing TV-Show data
     * @param tvShowBuilder {@link TvShowBuilder} object to fill TV-Show data
     * @return {@link TvShowBuilder} with all provided data
     * @throws ServiceException If application error occurs
     * @throws IOException      When I/O error occurs
     * @throws ServletException When HttpServletRequest error occurs
     */
    TvShowBuilder retrieveTvShowInfo(HttpServletRequest request, TvShowBuilder tvShowBuilder)
            throws ServiceException, IOException, ServletException;
}
