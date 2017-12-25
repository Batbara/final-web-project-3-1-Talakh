package by.tr.web.controller.command;

import by.tr.web.exception.controller.CommandProviderException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "commandProvider")
public class CommandProvider {
    private Map<String, Command> commands = new HashMap<>();
    private static CommandProvider instance = new CommandProvider();

    private CommandProvider() {
    }

    public static CommandProvider getInstance() {
        return instance;
    }

    public void initCommandProvider() throws CommandProviderException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CommandList.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            CommandList commandList = (CommandList) unmarshaller.unmarshal(new File(getSourcePath("xml/commandProvider.xml")));
            List<CommandPrototype> commands = commandList.getCommandList();
            for (CommandPrototype command : commands) {
                Class commandClass = Class.forName(command.getClassName());
                Object commandClassInstance = commandClass.newInstance();

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
        // CommandName commandName = CommandName.valueOf(name.toUpperCase());
        return commands.get(name);
    }

    private String getSourcePath(String path) {

        ClassLoader classLoader = getClass().getClassLoader();
        URL sourceURL = classLoader.getResource(path);
        assert sourceURL != null;
        String rawPath = sourceURL.getPath();

        String incorrectDriveLetterRegEx = "^/(.:/)";
        String capturedGroup = "$1";
        String correctPath = rawPath.replaceFirst(incorrectDriveLetterRegEx, capturedGroup);

        return correctPath;

    }

}
