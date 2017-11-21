package by.tr.web.controller;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.command.impl.ChangeLanguageImpl;
import by.tr.web.controller.command.impl.Logination;
import by.tr.web.controller.command.impl.LogoutImpl;
import by.tr.web.controller.command.impl.Registration;
import by.tr.web.controller.command.impl.ShowAccountImpl;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.login, new Logination());
        commands.put(CommandName.register, new Registration());
        commands.put(CommandName.showAccount, new ShowAccountImpl());
        commands.put(CommandName.changeLanguage,new ChangeLanguageImpl());
        commands.put(CommandName.logOut, new LogoutImpl());
    }

    public Command getCommand(String name) {
        CommandName commandName = CommandName.valueOf(name);
        return commands.get(commandName);
    }

}
