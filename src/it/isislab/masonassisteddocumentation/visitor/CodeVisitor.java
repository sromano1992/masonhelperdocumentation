package it.isislab.masonassisteddocumentation.visitor;


import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public abstract class CodeVisitor extends ASTVisitor {
	private CompilationUnit cu;
	protected String information_s;
	
	public CodeVisitor(CompilationUnit cu){
		this.cu = cu;
		information_s = "";
	}

	@Override
	public boolean visit(IfStatement node) {
		information_s += Messages.Visitor_If1 + node.getExpression() + " "; //$NON-NLS-2$
		return super.visit(node);
	}
	
	public void endVisit(IfStatement node){
		information_s += Messages.Visitor_EndIf;
	}

	public boolean visit(ForStatement f){
		information_s += Messages.Visitor_While1 + f.getExpression() + " "; //$NON-NLS-2$
		return super.visit(f);
	}

	public void endVisit(ForStatement node){
		information_s += Messages.Visitor_EndFor; //$NON-NLS-2$;
	}
	
	public boolean visit (DoStatement d){
		information_s += Messages.Visitor_Do1 + d.getExpression() + " "; //$NON-NLS-2$
		return super.visit(d);
	}

	public void endVisit(DoStatement node){
		information_s += Messages.Visitor_DoWhileEnd;
	}
	
	public boolean visit(SwitchStatement s){
		information_s += Messages.Visitor_SwitchInfo + s.getExpression() + " "; //$NON-NLS-2$
		return super.visit(s);
	}

	public void endVisit(SwitchStatement node){
		information_s += Messages.Visitor_EndSwitch;
	}
	
	public boolean visit(TypeDeclaration t){
		information_s += Messages.Visitor_TypeDeclaration + t.getName();
		return super.visit(t);
	}

	public void endVisit(TypeDeclaration node){
		information_s += Messages.Visitor_EndTypeDeclaration;
	}
	
	public boolean visit(Assignment node){
		information_s += Messages.Visitor_Assignment + node.getLeftHandSide() + Messages.Visitor_Assignemnt1 + node.getRightHandSide() + " "; //$NON-NLS-3$
		return super.visit(node);
	}

	public void endVisit(Assignment node){
		information_s += Messages.Visitor_EndAssignent;
	}
	

	public void endVisit(MethodInvocation node){
		information_s += Messages.Visitor_EndInvocation + node.getName() + Messages.Visitor_EndInvocation1;
	}
	
	public String getInformation_s() {
		return information_s + Messages.Visitor_EndMethod;
	}

	public abstract boolean visit(MethodInvocation node);
}
