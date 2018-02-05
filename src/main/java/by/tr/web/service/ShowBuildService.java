package by.tr.web.service;

import by.tr.web.domain.builder.MovieBuilder;
import by.tr.web.domain.builder.ShowBuilder;
import by.tr.web.exception.service.common.ServiceException;
import by.tr.web.service.validation.DataTypeValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ShowBuildService {

    ShowBuilder retrieveBasicShowInfo(HttpServletRequest request, ShowBuilder showBuilder)
            throws ServiceException, IOException, ServletException;

    ShowBuilder retrieveShowInfoTranslation(HttpServletRequest request, ShowBuilder showBuilder,
                                            DataTypeValidator.Language language)
            throws ServiceException, IOException, ServletException;

    MovieBuilder retrieveMovieInfo(HttpServletRequest request, MovieBuilder movieBuilder)
            throws ServiceException, IOException, ServletException;
}
