package by.tr.web.controller.command.impl;

import by.tr.web.controller.InputParameterName;
import by.tr.web.controller.JSPPagePath;
import by.tr.web.controller.command.Command;
import by.tr.web.domain.User;
import by.tr.web.exception.service.IncorrectPasswordException;
import by.tr.web.exception.service.InvalidEMailException;
import by.tr.web.exception.service.InvalidLoginException;
import by.tr.web.exception.service.UserAlreadyExistsException;
import by.tr.web.exception.service.UserServiceException;
import by.tr.web.service.UserService;
import by.tr.web.service.factory.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Registration implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String login = request.getParameter(InputParameterName.LOGIN);
        String password = request.getParameter(InputParameterName.PASSWORD);
        String eMail = request.getParameter(InputParameterName.EMAIL);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        User newUser;
        try {
            newUser = userService.register(login, password, eMail);

            HttpSession session = request.getSession(true);
            session.setAttribute(InputParameterName.USER, newUser);
            session.setAttribute(InputParameterName.USER_STATUS, newUser.getUserStatus());

            response.sendRedirect(JSPPagePath.USER_ACCOUNT_PATH);
        } catch (IncorrectPasswordException ex) {
            showRegisterError(request, response, InputParameterName.PASSWORD);
        }
        catch (InvalidLoginException ex) {
            showRegisterError(request, response, InputParameterName.LOGIN);
        }
        catch (InvalidEMailException ex) {
            showRegisterError(request, response, InputParameterName.EMAIL);
        } catch (UserAlreadyExistsException ex) {
            showRegisterError(request, response, InputParameterName.USER);
        }catch (UserServiceException e) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(JSPPagePath.INTERNAL_ERROR_PAGE);
            dispatcher.forward(request,response);
        }

    }

    private void showRegisterError(HttpServletRequest request, HttpServletResponse response, String errorType)
            throws ServletException, IOException {
        request.setAttribute(InputParameterName.REGISTER_ERROR, errorType);
        request.getRequestDispatcher(JSPPagePath.REGISTER_PAGE).forward(request, response);
    }
}
