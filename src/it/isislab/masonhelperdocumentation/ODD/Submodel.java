package it.isislab.masonhelperdocumentation.ODD;

import java.io.Serializable;

import it.isislab.masonhelperdocumentation.analizer.GlobalUtility;
import it.isislab.masonhelperdocumentation.analizer.Method;
import it.isislab.masonhelperdocumentation.visitor.CodeVisitor;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodInvocation;

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
		CodeVisitor cv = new CodeVisitor(cu);
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
		return "<h1>" + getName() + "</h1>\n" + getDescription() + "\n";
	}
}
