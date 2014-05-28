package it.isislab.masonhelperdocumentation.visitor;

import it.isislab.masonhelperdocumentation.analizer.GlobalUtility;
import it.isislab.masonhelperdocumentation.analizer.Method;

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
public class CodeVisitor extends ASTVisitor{
	private CompilationUnit cu;
	private String information_s;
	
	public CodeVisitor(CompilationUnit cu){
		this.cu = cu;
		information_s = "";
	}

	@Override
	public boolean visit(IfStatement node) {
		information_s += "If " + node.getExpression() + " ";
		return super.visit(node);
	}
	
	public void endVisit(IfStatement node){
		information_s += "End if.\n";
	}

	public boolean visit(ForStatement f){
		information_s += "There is a for loop that while " + f.getExpression() + " ";
		return super.visit(f);
	}

	public void endVisit(ForStatement node){
		information_s += "End for.\n";
	}
	
	public boolean visit (DoStatement d){
		information_s += "While " + d.getExpression() + " ";
		return super.visit(d);
	}

	public void endVisit(DoStatement node){
		information_s += "After this do-while statement:\n";
	}
	
	public boolean visit(SwitchStatement s){
		information_s += "There is a switch on " + s.getExpression() + " ";
		return super.visit(s);
	}

	public void endVisit(SwitchStatement node){
		information_s += "After this switch-statement:\n";
	}
	
	public boolean visit(TypeDeclaration t){
		information_s += "New type declaration: " + t.getName();
		return super.visit(t);
	}

	public void endVisit(TypeDeclaration node){
		information_s += "After this type declaration:\n";
	}
	
	public boolean visit(Assignment node){
		information_s += "There is an assignment for " + node.getLeftHandSide() + " to value " + node.getRightHandSide() + " ";
		return super.visit(node);
	}

	public void endVisit(Assignment node){
		information_s += "After this assignment:\n";
	}
	
	public boolean visit(MethodInvocation node) {
		boolean visitedSubmetod = false;
        information_s += "Invoke method " + node.getName();
        ArrayList<Method> method_s = GlobalUtility.getAllMethods(cu);
        for (Method m : method_s){
        	if (m.getName().toString().equals(node.getName().toString())){
        		visitedSubmetod = true;
        		information_s += " that: ";
        		m.getMethod().getBody().accept(new CodeVisitor(cu));
        	}
        }
        if (!visitedSubmetod) information_s += ".\n";
        return super.visit(node);
    }

	public void endVisit(MethodInvocation node){
		information_s += "After invocation of " + node.getName() + " method:\n";
	}

	public String getInformation_s() {
		return information_s + " this method terminates.";
	}
	
}
