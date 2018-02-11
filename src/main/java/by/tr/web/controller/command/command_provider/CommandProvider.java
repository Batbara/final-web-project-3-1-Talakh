package by.tr.web.controller.command.command_provider;

import by.tr.web.controller.command.Command;
import by.tr.web.controller.util.TypeFormatUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "commandProvider")
public class CommandProvider {
    private Map<String, Command> commands = new HashMap<>();
    private List<String> adminCommands = new ArrayList<>();
    private static CommandProvider instance = new CommandProvider();

    private CommandProvider() {
    }

    public static CommandProvider getInstance() {
        return instance;
    }

    /**
     * Binds command names defined in commandProvider.xml with their implementation
     *
     * Using JAXB {@link Unmarshaller}, {@link List} of {@link CommandPrototype} is created.
     * After that instances of {@link Command} are dynamically created and put in Map, so they could be accessed by name.
     *
     * @throws CommandProviderException
     * is thrown if
     */
    public void initCommandProvider() throws CommandProviderException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CommandList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            String sourcePath = TypeFormatUtil.getSourcePath(CommandXmlParameter.COMMAND_PROVIDER_XML_PATH);
            CommandList commandList = (CommandList) unmarshaller.unmarshal(new File(sourcePath));

            List<CommandPrototype> commands = commandList.getCommandList();

            for (CommandPrototype command : commands) {
                Class commandClass = Class.forName(command.getClassName());
                Object commandClassInstance = commandClass.newInstance();
                if (command.checkScope(CommandXmlParameter.ADMIN_SCOPE)) {
                    adminCommands.add(command.getCommandName());
                }
                this.commands.put(command.getCommandName(), (Command) commandClassInstance);
            }
        } catch (JAXBException e) {
            throw new CommandProviderException("Unmarshalling error", e);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new CommandProviderException("Can't instantiate command implementation class", e);
        } catch (ClassNotFoundException e) {
            throw new CommandProviderException("Command implementation class not found", e);
        }
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }

    /**
     * Checks if command can be invoked only by administrator
     *
     * @param name
     * Command name to check access
     * @return true - if command is administrative; false - otherwise
     */
    public boolean isAdminCommand(String name) {
        for (String adminCommand : adminCommands) {
            if (adminCommand.equals(name)) {
                return true;
            }
        }
        return false;
    }

}
