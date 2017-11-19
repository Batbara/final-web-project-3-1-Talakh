package by.tr.web.controller;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.command.impl.LoginationImpl;
import by.tr.web.controller.command.impl.RegistrationImpl;
import by.tr.web.controller.command.impl.ShowAccount;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.login, new LoginationImpl());
        commands.put(CommandName.register, new RegistrationImpl());
        commands.put(CommandName.showAccount, new ShowAccount());
    }

    public Command getCommand(String name) {
        CommandName commandName = CommandName.valueOf(name);
        return commands.get(commandName);
    }

}
