package by.tr.web.controller.command.command_provider;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = CommandXmlParameter.COMMAND_LIST, namespace = CommandXmlParameter.NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class CommandList {
    @XmlElement(name = CommandXmlParameter.COMMAND, namespace = CommandXmlParameter.NAMESPACE)
    private List<CommandPrototype> commandList;

    public CommandList() {
    }

    public List<CommandPrototype> getCommandList() {
        return commandList;
    }
}