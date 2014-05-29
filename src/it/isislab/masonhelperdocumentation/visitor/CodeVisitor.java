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
		information_s += Messages.StartMethodVisitor_If1 + node.getExpression() + " "; //$NON-NLS-2$
		return super.visit(node);
	}
	
	public void endVisit(IfStatement node){
		information_s += Messages.StartMethodVisitor_EndIf;
	}

	public boolean visit(ForStatement f){
		information_s += Messages.StartMethodVisitor_While1 + f.getExpression() + " "; //$NON-NLS-2$
		return super.visit(f);
	}

	public void endVisit(ForStatement node){
		information_s += Messages.StartMethodVisitor_EndFor; //$NON-NLS-2$;
	}
	
	public boolean visit (DoStatement d){
		information_s += Messages.StartMethodVisitor_Do1 + d.getExpression() + " "; //$NON-NLS-2$
		return super.visit(d);
	}

	public void endVisit(DoStatement node){
		information_s += Messages.StartMethodVisitor_DoWhileEnd;
	}
	
	public boolean visit(SwitchStatement s){
		information_s += Messages.StartMethodVisitor_SwitchInfo + s.getExpression() + " "; //$NON-NLS-2$
		return super.visit(s);
	}

	public void endVisit(SwitchStatement node){
		information_s += Messages.StartMethodVisitor_EndSwitch;
	}
	
	public boolean visit(TypeDeclaration t){
		information_s += Messages.StartMethodVisitor_TypeDeclaration + t.getName();
		return super.visit(t);
	}

	public void endVisit(TypeDeclaration node){
		information_s += Messages.StartMethodVisitor_EndTypeDeclaration;
	}
	
	public boolean visit(Assignment node){
		information_s += Messages.StartMethodVisitor_Assignment + node.getLeftHandSide() + Messages.StartMethodVisitor_Assignemnt1 + node.getRightHandSide() + " "; //$NON-NLS-3$
		return super.visit(node);
	}

	public void endVisit(Assignment node){
		information_s += Messages.StartMethodVisitor_EndAssignent;
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
		information_s += Messages.StartMethodVisitor_EndInvocation + node.getName() + Messages.StartMethodVisitor_EndInvocation1;
	}
	
	public String getInformation_s() {
		return information_s + Messages.StartMethodVisitor_EndMethod;
	}
	
}
