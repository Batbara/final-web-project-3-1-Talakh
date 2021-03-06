package by.tr.web.controller.command.command_provider;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = CommandXmlParameter.COMMAND, propOrder = {
        CommandXmlParameter.COMMAND_NAME,
        CommandXmlParameter.CLASS_NAME
})
public class CommandPrototype {
    @XmlAttribute(name = "scope", required = true)
    private String scope;
    @XmlElement(namespace = CommandXmlParameter.NAMESPACE)
    private String commandName;
    @XmlElement(namespace = CommandXmlParameter.NAMESPACE)
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

    public boolean checkScope(String scope) {
        return this.scope != null && scope != null && this.scope.equals(scope);
    }
}
