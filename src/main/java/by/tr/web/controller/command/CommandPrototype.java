package by.tr.web.controller.command;

import by.tr.web.controller.constant.XMLParameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = XMLParameter.COMMAND, propOrder = {
        XMLParameter.COMMAND_NAME,
        XMLParameter.CLASS_NAME
})
public class CommandPrototype {
    @XmlAttribute(name = "scope", required = true)
    private String scope;
    @XmlElement(namespace = XMLParameter.NAMESPACE)
    private String commandName;
    @XmlElement(namespace = XMLParameter.NAMESPACE)
    private String className;



    public CommandPrototype() {
    }

    public String getCommandName() {
        return commandName;
    }

    public String getClassName() {
        return className;
    }

    public String getScope() {
        return scope;
    }
}
