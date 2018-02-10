package by.tr.web.service.show;

import by.tr.web.controller.constant.FrontControllerParameter;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.util.TypeFormatUtil;
import by.tr.web.domain.Country;
import by.tr.web.domain.Genre;
import by.tr.web.domain.TvShow;
import by.tr.web.domain.builder.MovieBuilder;
import by.tr.web.domain.builder.ShowBuilder;
import by.tr.web.domain.builder.TvShowBuilder;
import by.tr.web.service.ServiceException;
import by.tr.web.service.input_validator.DataTypeValidator;
import by.tr.web.service.input_validator.InvalidNumericalInput;
import by.tr.web.service.input_validator.RequestParameterNotFound;
import by.tr.web.service.input_validator.ValidationException;
import by.tr.web.service.input_validator.ValidatorFactory;
import by.tr.web.service.movie.MovieValidator;
import by.tr.web.service.tv_show.TvShowValidator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ShowBuildServiceImpl implements ShowBuildService {
    @Override
    public ShowBuilder retrieveBasicShowInfo(HttpServletRequest request, ShowBuilder builder) throws ServiceException, IOException, ServletException {

        builder = retrieveShowInfoTranslation(request, builder, DataTypeValidator.Language.EN);

        Time showRuntime = retrieveShowRuntimeFromRequest(request);
        builder.addRuntime(showRuntime);

        Date premiereDate = retrievePremiereDateFromRequest(request);
        builder.addPremiereDate(premiereDate);

        int showYear = TypeFormatUtil.getYearFromDate(premiereDate);
        builder.addYear(showYear);

        List<Genre> genreList = retrieveGenreListFromRequest(request);
        builder.addGenres(genreList);

        List<Country> countryList = retrieveCountryListFromRequest(request);
        builder.addCountries(countryList);

        return builder;
    }

    @Override
    public ShowBuilder retrieveShowInfoTranslation(HttpServletRequest request, ShowBuilder showBuilder,
                                                   DataTypeValidator.Language language)
            throws ServiceException, IOException, ServletException {

        try {
            String poster = savePoster(request, language);
            showBuilder.addPoster(poster);
        } catch (RequestParameterNotFound e) {
            showBuilder.addPoster(null);
        }

        String lang = language.toString().toLowerCase();
        showBuilder.addLanguage(lang);

        String title = retrieveShowTitleFromRequest(request, language);
        showBuilder.addTitle(title);

        String synopsis = retrieveShowSynopsisFromRequest(request, language);
        showBuilder.addSynopsis(synopsis);
        return showBuilder;
    }

    @Override
    public MovieBuilder retrieveMovieInfo(HttpServletRequest request, MovieBuilder movieBuilder)
            throws ServiceException, IOException, ServletException {
        MovieValidator movieValidator = (MovieValidator) ValidatorFactory.getInstance().getMovieValidator();
        DataTypeValidator dataTypeValidator = ValidatorFactory.getInstance().getDataTypeValidator();

        String mpaaRating = request.getParameter(JspAttribute.MOVIE_MPAA_RATING);
        if (!mpaaRating.isEmpty()) {
            movieValidator.validateMpaaRating(mpaaRating);
            movieBuilder.addMpaaRating(mpaaRating);
        }

        String budget = request.getParameter(JspAttribute.MOVIE_BUDGET);
        if (!budget.isEmpty()) {
            dataTypeValidator.checkForPositive(budget);
            movieBuilder.addBudget(Long.parseLong(budget));
        }
        String boxOffice = request.getParameter(JspAttribute.MOVIE_BOX_OFFICE);
        if (!boxOffice.isEmpty()) {
            dataTypeValidator.checkForPositive(boxOffice);
            movieBuilder.addBoxOffice(Long.parseLong(boxOffice));
        }
        return movieBuilder;
    }

    @Override
    public TvShowBuilder retrieveTvShowInfo(HttpServletRequest request, TvShowBuilder tvShowBuilder)
            throws ServiceException, IOException, ServletException {
        ValidatorFactory validatorFactory = ValidatorFactory.getInstance();
        TvShowValidator tvShowValidator = (TvShowValidator) validatorFactory.getTvShowValidator();
        DataTypeValidator dataTypeValidator = validatorFactory.getDataTypeValidator();

        String showStatus = request.getParameter(JspAttribute.TV_SHOW_STATUS);
        if (showStatus == null) {
            throw new RequestParameterNotFound("Tv-show status parameter is not in request");
        }
        if (!showStatus.isEmpty()) {
            tvShowValidator.validateShowStatus(showStatus);
            tvShowBuilder.addShowStatus(showStatus);

            if (isShowFinished(showStatus)) {
                addFinishedYear(request, tvShowBuilder);
            }
        }

        String episodesNumber = request.getParameter(JspAttribute.TV_SHOW_EPISODES_NUMBER);
        if (episodesNumber == null) {
            throw new RequestParameterNotFound("Episodes number parameter is not in request");
        }
        if (!episodesNumber.isEmpty()) {
            dataTypeValidator.checkForNotNegative(episodesNumber);
            tvShowBuilder.addEpisodesNum(Integer.parseInt(episodesNumber));
        }

        String seasonsNumber = request.getParameter(JspAttribute.TV_SHOW_EPISODES_NUMBER);
        if (seasonsNumber == null) {
            throw new RequestParameterNotFound("Seasons number parameter is not in request");
        }
        if (!seasonsNumber.isEmpty()) {
            dataTypeValidator.checkForPositive(seasonsNumber);
            tvShowBuilder.addEpisodesNum(Integer.parseInt(seasonsNumber));
        }

        String channel = request.getParameter(JspAttribute.TV_SHOW_CHANNEL);
        if (channel == null) {
            throw new RequestParameterNotFound("Tv channel parameter is not in request");
        }
        if (!channel.isEmpty()) {
            tvShowBuilder.addChannel(channel);
        }
        return tvShowBuilder;
    }

    private boolean isShowFinished(String showStatus) {
        String finished = TvShow.ShowStatus.FINISHED.name();
        return showStatus.equalsIgnoreCase(finished);
    }

    private void addFinishedYear(HttpServletRequest request, TvShowBuilder tvShowBuilder) throws RequestParameterNotFound, InvalidNumericalInput {
        DataTypeValidator dataTypeValidator = ValidatorFactory.getInstance().getDataTypeValidator();
        String finishedYear = request.getParameter(JspAttribute.TV_SHOW_FINISHED_YEAR);

        if (finishedYear == null || finishedYear.isEmpty()) {
            throw new RequestParameterNotFound("Finished parameter is not in request");
        }

        dataTypeValidator.checkForPositive(finishedYear);
        tvShowBuilder.addFinishedYear(Integer.parseInt(finishedYear));

    }

    private String savePoster(HttpServletRequest request, DataTypeValidator.Language lang)
            throws IOException, ServletException, ServiceException {

        Part filePart = retrieveFileFromRequest(request, lang);

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uniqueFileName = TypeFormatUtil.generateUniqueFileName(fileName, lang);

        InputStream fileContent = null;
        OutputStream outStream = null;
        try {
            fileContent = filePart.getInputStream();
            byte[] buffer = new byte[fileContent.available()];
            int size = fileContent.read(buffer);

            if (size == -1) {
                throw new ServiceException("Cannot read uploaded file");
            }
            ServletContext context = request.getServletContext();
            String uploadPath = context.getInitParameter(FrontControllerParameter.POSTER_UPLOAD_FOLDER);

            String path = context.getRealPath(uploadPath) + uniqueFileName;
            File targetFile = new File(path);
            outStream = new FileOutputStream(targetFile);
            outStream.write(buffer);

            return uploadPath + uniqueFileName;
        } finally {
            if (fileContent != null) {
                fileContent.close();
            }
            if (outStream != null) {
                outStream.close();
            }
        }

    }

    private Part retrieveFileFromRequest(HttpServletRequest request, DataTypeValidator.Language lang)
            throws IOException, ServletException, RequestParameterNotFound {

        if (isEnglishLanguage(lang)) {

            return getEnglishPoster(request);
        }

        Part filePart = request.getPart(JspAttribute.SHOW_POSTER_RU);

        if (filePart.getSize() == 0) {
            return getEnglishPoster(request);
        }
        return filePart;
    }

    private Part getEnglishPoster(HttpServletRequest request)
            throws RequestParameterNotFound, IOException, ServletException {
        Part filePart;
        String posterParameter;

        posterParameter = JspAttribute.SHOW_POSTER_EN;
        filePart = request.getPart(posterParameter);

        if (filePart.getSize() == 0) {
            throw new RequestParameterNotFound("No english poster in request");
        }
        return filePart;

    }

    private List<Country> retrieveCountryListFromRequest(HttpServletRequest request) throws ServiceException {
        String[] countries = request.getParameterValues(JspAttribute.SHOW_COUNTRY_ID);
        DataTypeValidator typeValidator = ValidatorFactory.getInstance().getDataTypeValidator();

        List<Country> countryList = new ArrayList<>();
        for (String countryId : countries) {
            typeValidator.checkForPositive(countryId);

            int countryIdNumber = Integer.parseInt(countryId);
            countryList.add(new Country(countryIdNumber));
        }
        return countryList;
    }

    private List<Genre> retrieveGenreListFromRequest(HttpServletRequest request) throws ServiceException {
        String[] genres = request.getParameterValues(JspAttribute.SHOW_GENRE_ID);
        DataTypeValidator typeValidator = ValidatorFactory.getInstance().getDataTypeValidator();

        List<Genre> genreList = new ArrayList<>();
        for (String genreId : genres) {
            typeValidator.checkForPositive(genreId);

            int genreIdNumber = Integer.parseInt(genreId);
            genreList.add(new Genre(genreIdNumber));
        }
        return genreList;
    }

    private Date retrievePremiereDateFromRequest(HttpServletRequest request)
            throws ValidationException {
        String premiereDateString = request.getParameter(JspAttribute.SHOW_PREMIERE_DATE);
        Date premiereDate;
        try {
            premiereDate = TypeFormatUtil.getDateFromString(premiereDateString, FrontControllerParameter.SQL_DATE_PATTERN);
        } catch (ParseException e) {
            throw new ValidationException("Invalid show premiere date", e);
        }
        return premiereDate;
    }

    private Time retrieveShowRuntimeFromRequest(HttpServletRequest request)
            throws ValidationException {
        String runtimeString = request.getParameter(JspAttribute.SHOW_RUNTIME);
        Time showRuntime;
        try {
            showRuntime = TypeFormatUtil.getTimeFromString(runtimeString, FrontControllerParameter.ONLY_TIME_PATTERN);
        } catch (ParseException e) {
            throw new ValidationException("Invalid show runtime value", e);
        }
        return showRuntime;
    }

    private String retrieveShowTitleFromRequest(HttpServletRequest request, DataTypeValidator.Language lang)
            throws ValidationException {
        String englishTitle = request.getParameter(JspAttribute.SHOW_TITLE_EN);
        if (englishTitle == null) {
            throw new ValidationException("Required title is null");
        }

        if (isEnglishLanguage(lang)) {
            return englishTitle;
        } else {
            String russianTitle = request.getParameter(JspAttribute.SHOW_TITLE_RU);
            return russianTitle.isEmpty() ? englishTitle : russianTitle;

        }

    }

    private String retrieveShowSynopsisFromRequest(HttpServletRequest request, DataTypeValidator.Language lang)
            throws ValidationException {
        String englishSynopsis = request.getParameter(JspAttribute.SHOW_SYNOPSIS_EN);
        if (englishSynopsis == null) {
            throw new ValidationException("Required synopsis is null");
        }
        if (isEnglishLanguage(lang)) {
            return englishSynopsis;
        } else {
            String russianSynopsis = request.getParameter(JspAttribute.SHOW_SYNOPSIS_RU);
            return russianSynopsis.isEmpty() ? englishSynopsis : russianSynopsis;
        }

    }

    private boolean isEnglishLanguage(DataTypeValidator.Language lang) {
        return lang == DataTypeValidator.Language.EN;
    }

}
