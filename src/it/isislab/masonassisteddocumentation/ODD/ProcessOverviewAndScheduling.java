package it.isislab.masonassisteddocumentation.ODD;

import java.io.Serializable;
import java.util.ArrayList;


public class ProcessOverviewAndScheduling  implements Serializable{
	private static final long serialVersionUID = 1;
	private ArrayList<ProcessOverviewElement> whoDoWhat;
	public static String serializedName = "process.ser";

	public ProcessOverviewAndScheduling() {
		super();
		this.whoDoWhat = new ArrayList<ProcessOverviewElement>();
	}
	
	public void addProcessOverviewElement(String elementName, String autoDoWhat, String userDoWhat){
		whoDoWhat.add(new ProcessOverviewElement(elementName, autoDoWhat, userDoWhat));
	}
	
	public ProcessOverviewElement getProcessOverviewElement(String elementName){
		for (ProcessOverviewElement p : whoDoWhat){
			if (p.getElementName().equals(elementName))
				return p;
		}
		return null;
	}
	
	public ArrayList<ProcessOverviewElement> getProcessOverviewElement_s(){
		return whoDoWhat;
	}
}
