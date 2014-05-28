package it.isislab.masonhelperdocumentation.ODD;

import java.io.Serializable;

import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * In processOverview phase of ODD is
 * described who do what. We can have 
 * more entity that do something.
 * This class represent on of this element
 * and ProcessOverviewAndScheduling 
 * contains a list of this type.
 * @author Romano Simone 0512101343
 *
 */
public class ProcessOverviewElement  implements Serializable{
	private static final long serialVersionUID = 1;
	private String elementName;
	private String autoDoWhat, userDoWhat;
	public ProcessOverviewElement(String who, String autoDoWhat,
			String userDoWhat) {
		super();
		this.elementName = who;
		this.autoDoWhat = autoDoWhat;
		this.userDoWhat = userDoWhat;
	}
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String who) {
		this.elementName = who;
	}
	public String getAutoDoWhat() {
		return autoDoWhat;
	}
	public void setAutoDoWhat(String autoDoWhat) {
		this.autoDoWhat = autoDoWhat;
	}
	public String getUserDoWhat() {
		return userDoWhat;
	}
	public void setUserDoWhat(String userDoWhat) {
		this.userDoWhat = userDoWhat;
	}
}
