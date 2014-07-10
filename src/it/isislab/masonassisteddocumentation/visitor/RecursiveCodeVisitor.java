package it.isislab.masonassisteddocumentation.visitor;

import it.isislab.masonassisteddocumentation.mason.analizer.GlobalUtility;
import it.isislab.masonassisteddocumentation.mason.analizer.Method;

import java.awt.List;
import java.util.ArrayList;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;

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
