package by.tr.web.controller.command;

import by.tr.web.controller.constant.XmlParameter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = XmlParameter.COMMAND, propOrder = {
        XmlParameter.COMMAND_NAME,
        XmlParameter.CLASS_NAME
})
public class CommandPrototype {
    @XmlAttribute(name = "scope", required = true)
    private String scope;
    @XmlElement(namespace = XmlParameter.NAMESPACE)
    private String commandName;
    @XmlElement(namespace = XmlParameter.NAMESPACE)
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
