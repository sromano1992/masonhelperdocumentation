package it.isislab.masonassisteddocumentation.visitor;

import it.isislab.masonassisteddocumentation.mason.analizer.GlobalUtility;
import it.isislab.masonassisteddocumentation.mason.analizer.Method;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodInvocation;

/**
 * This class represent a visitor for method.
 * Purpose of this class is extract a description
 * of method in natural language.
 * @author Romano Simone 0512101343
 *
 */
public class RecursiveCodeVisitor extends CodeVisitor{
	private CompilationUnit cu;
	
	public RecursiveCodeVisitor(CompilationUnit cu){
		super(cu);
		this.cu = cu;
		information_s = "";
	}

	public boolean visit(MethodInvocation node) {
		boolean visitedSubmetod = false;
        information_s += "Invoke method " + node.getName();
        ArrayList<Method> method_s = GlobalUtility.getAllMethods(cu);
        for (Method m : method_s){
        	if (m.getName().toString().equals(node.getName().toString())){
        		visitedSubmetod = true;
        		information_s += " that: ";
        		m.getMethod().getBody().accept(new RecursiveCodeVisitor(cu));
        	}
        }
        if (!visitedSubmetod) information_s += ".\n";
        return true;
    }
}
