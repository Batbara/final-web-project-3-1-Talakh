package by.tr.web.controller.command.impl;

import by.tr.web.controller.Parameter;
import by.tr.web.controller.Path;
import by.tr.web.controller.command.Command;
import by.tr.web.domain.User;
import by.tr.web.exception.service.IncorrectPasswordException;
import by.tr.web.exception.service.InvalidEMailException;
import by.tr.web.exception.service.InvalidLoginException;
import by.tr.web.exception.service.UserAlreadyExistsException;
import by.tr.web.exception.service.UserServiceException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Registration implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.getSession().setAttribute(Parameter.LOCALE, request.getParameter(Parameter.LOCALE));

        String login = request.getParameter(Parameter.LOGIN);
        String password = request.getParameter(Parameter.PASSWORD);
        String eMail = request.getParameter(Parameter.EMAIL);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        User newUser;
        try {
            newUser = userService.register(login, password, eMail);

            HttpSession session = request.getSession(true);
            session.setAttribute(Parameter.USER, newUser);
            session.setAttribute(Parameter.USER_STATUS, newUser.getUserStatus());

            response.sendRedirect(Path.SHOW_ACCOUNT_QUERY);
        } catch (IncorrectPasswordException ex) {
            showRegisterError(request, response, Parameter.PASSWORD);
        }
        catch (InvalidLoginException ex) {
            showRegisterError(request, response, Parameter.LOGIN);
        }
        catch (InvalidEMailException ex) {
            showRegisterError(request, response, Parameter.EMAIL);
        } catch (UserAlreadyExistsException ex) {
            showRegisterError(request, response, Parameter.USER);
        }catch (UserServiceException e) {
            //TODO: log exception and redirect to unknown error page
        }

    }

    private void showRegisterError(HttpServletRequest request, HttpServletResponse response, String errorType)
            throws ServletException, IOException {
        request.setAttribute(Parameter.REGISTER_ERROR, errorType);
        request.getRequestDispatcher(Path.REGISTER_PAGE).forward(request, response);
    }
}
