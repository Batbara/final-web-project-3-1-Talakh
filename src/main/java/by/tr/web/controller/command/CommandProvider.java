package by.tr.web.controller.command;

import by.tr.web.controller.command.impl.ChangeLanguageImpl;
import by.tr.web.controller.command.impl.Logination;
import by.tr.web.controller.command.impl.LogoutImpl;
import by.tr.web.controller.command.impl.Registration;
import by.tr.web.controller.command.impl.TakeAccountImpl;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private Map<CommandName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(CommandName.LOGIN, new Logination());
        commands.put(CommandName.REGISTER, new Registration());
        commands.put(CommandName.SHOW_ACCOUNT, new TakeAccountImpl());
        commands.put(CommandName.CHANGE_LANGUAGE,new ChangeLanguageImpl());
        commands.put(CommandName.LOGOUT, new LogoutImpl());
    }

    public Command getCommand(String name) {
        CommandName commandName = CommandName.valueOf(name.toUpperCase());
        return commands.get(commandName);
    }

}
