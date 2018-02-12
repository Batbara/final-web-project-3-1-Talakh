package by.tr.web.controller.command.admin;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.constant.JspAttribute;
import by.tr.web.controller.constant.JspPagePath;
import by.tr.web.domain.Show;
import by.tr.web.service.input_validator.ValidationException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddShowImpl implements Command {

    private static final Logger logger = Logger.getLogger(AddShowImpl.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            Show.ShowType showType = getShowType(request);

            String addShowPath = JspPagePath.getShowAddingAddress(showType);
            request.getRequestDispatcher(addShowPath).forward(request, response);
          //  response.sendRedirect(addShowPath);
        } catch (ValidationException e) {
            logger.error("Error while redirecting to adding show page", e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    private Show.ShowType getShowType(HttpServletRequest request) throws ValidationException {
        String showType = request.getParameter(JspAttribute.TYPE);
        if (showType == null) {
            throw new ValidationException("Type parameter is not in request");
        }
        for (Show.ShowType type : Show.ShowType.values()) {
            String typeName = type.name();
            if (typeName.equalsIgnoreCase(showType.replace("-", "_"))) {
                return type;
            }
        }
        throw new ValidationException("There is no such show type as " + showType);
    }
}
