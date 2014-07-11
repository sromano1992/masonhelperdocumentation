package it.isislab.masonassisteddocumentation.ODD;

import java.io.Serializable;

import it.isislab.masonassisteddocumentation.mason.analizer.GlobalUtility;
import it.isislab.masonassisteddocumentation.mason.analizer.Method;
import it.isislab.masonassisteddocumentation.visitor.RecursiveCodeVisitor;

import org.eclipse.jdt.core.dom.CompilationUnit;

public class Submodel  implements Serializable{
	private static final long serialVersionUID = 1;
	private String action;
	private CompilationUnit cu;

	public Submodel(String action, CompilationUnit cu) {
		super();
		this.action = action;
		this.cu = cu;
	}
	
	public String getDescription(){
		RecursiveCodeVisitor cv = new RecursiveCodeVisitor(cu);
		Method method = GlobalUtility.getMethodFrom(action, cu);
		if (method != null){
			method.getMethod().getBody().accept(cv);
			return cv.getInformation_s();
		}
		return "";
	}
	
	public String getName(){
		return action;
	}
	
	public String toString(){
		if (!getDescription().equals(""))
			return "<h1>" + getName() + "</h1>\n" + getDescription() + "\n";
		return "";
	}
	
	public boolean equals(Submodel s){
		if (s.getDescription().equals(this.getDescription()) && s.getName().equals(this.getName()))
			return true;
		return false;
	}
}
