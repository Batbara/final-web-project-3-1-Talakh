package by.tr.web.controller.command;

import by.tr.web.controller.constant.XMLParameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = XMLParameter.COMMAND_LIST, namespace = XMLParameter.NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class CommandList {
    @XmlElement(name= XMLParameter.COMMAND, namespace = XMLParameter.NAMESPACE)
    private List<CommandPrototype> commandList;

    public List<CommandPrototype> getCommandList() {
        return commandList;
    }
}