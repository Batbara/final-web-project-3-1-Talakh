package by.tr.web.controller.command;

import by.tr.web.controller.constant.XmlParameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = XmlParameter.COMMAND_LIST, namespace = XmlParameter.NAMESPACE)
@XmlAccessorType(XmlAccessType.FIELD)
public class CommandList {
    @XmlElement(name = XmlParameter.COMMAND, namespace = XmlParameter.NAMESPACE)
    private List<CommandPrototype> commandList;

    public CommandList() {
    }

    public List<CommandPrototype> getCommandList() {
        return commandList;
    }
}