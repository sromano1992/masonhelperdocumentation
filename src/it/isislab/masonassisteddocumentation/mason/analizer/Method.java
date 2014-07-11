package it.isislab.masonassisteddocumentation.mason.analizer;

import org.eclipse.jdt.core.dom.MethodDeclaration;

/**
 * This class represent a Method. It helps to
 * get information about method more easily.
 * @author Romano Simone 0512101343
 *
 */
public class Method {
	private MethodDeclaration method;

	public Method(MethodDeclaration method) {
		this.method = method;
	}
	
	public MethodDeclaration getMethod() {
		return method;
	}

	public String getJavadoc(){
		return GlobalUtility.getJavadocContent(method.getJavadoc());
	}

	public String getName(){
		return method.getName().toString();
	}
	
	public String toString(){
		return "[Method name: " + getName() + "-" + "Method declaration: " + getMethod() + "Method javadoc: " + getJavadoc() +"]";
	}
}
