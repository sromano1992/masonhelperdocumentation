package it.isislab.masonassisteddocumentation.visitor;

import it.isislab.masonassisteddocumentation.ODD.ODD;
import it.isislab.masonassisteddocumentation.ODD.Submodel;
import it.isislab.masonassisteddocumentation.mason.analizer.GlobalUtility;
import it.isislab.masonassisteddocumentation.mason.analizer.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

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
import org.eclipse.jdt.core.dom.WhileStatement;

/**
 * This visitor will visit Start method
 * and will extract information about schedule
 * and objects locations.
 * @author Romano Simone 0512101343
 *
 */
public class StartMethodVisitor extends ASTVisitor {
	private String information_s;
	private CompilationUnit cu;
	private static Logger log = Logger.getLogger("global"); //$NON-NLS-1$
	
	public StartMethodVisitor(CompilationUnit cu) {
		super();
		this.cu = cu;
		information_s = ""; //$NON-NLS-1$
	}

	public boolean visit(MethodInvocation node) {
        information_s += Messages.Visitor_InvokeMethod + node.getName() +".\n"; //$NON-NLS-2$
        if (node.getName().toString().equals("scheduleRepeating")) //$NON-NLS-1$
        	generateScheduleInformations(node); 
        else if (node.getName().toString().equals("setObjectLocation")) //$NON-NLS-1$
        	generateSetObjectLocationInformations(node);
        else if (node.getName().toString().equals("getObjectLocation")) //$NON-NLS-1$
        	generateGetObjectLocationInformations(node);
        else if (node.getName().toString().equals("multiply")) //$NON-NLS-1$
        	generateMultiplyInformations(node);
        else if (node.getName().toString().equals("nextDouble")) //$NON-NLS-1$
        	generateNextDoubleInformations();
        else if (node.getName().toString().equals("nextInt")) //$NON-NLS-1$
        	generateNextIntInformations();
        else if (node.getName().toString().equals("addEdge")) //$NON-NLS-1$
        	generateAddEdgeInformations(node);
        else if (node.getName().toString().equals("addNode")) //$NON-NLS-1$
        	generateAddNodeInformations(node);
        else if (node.getName().toString().equals("getAllObjects")) //$NON-NLS-1$
        	generateGetAllObjectsInformations(node);
        else{	//add submodel for last ODD phase ("submodels")
        	information_s = information_s + Messages.Visitor_SeeSubmodel;
        	Submodel toAdd = new Submodel(node.getName().toString(), cu);
        	ODD.addSubmodel(toAdd);
        	log.info(Messages.Visitor_SubmodelFound + node.getName() +"'.");        	 //$NON-NLS-2$
        }
        return super.visit(node);
	}
	
	private void generateAddEdgeInformations(MethodInvocation node) {
		List<Expression> argument_s = node.arguments();
		if (argument_s.size() == 3){
			information_s += Messages.Visitor_AddEdgeInformation +node.getExpression() +Messages.Visitor_AddEdgeInfo1 + argument_s.get(0) +Messages.Visitor_AddEdgeInfo2 + argument_s.get(1) +Messages.Visitor_AddEdgeInfo3;
		}
		if (argument_s.size() == 1){
			information_s += Messages.Visitor_AddEdgeInfo4 + argument_s.get(0) + Messages.Visitor_AddEdgeInfo5 + node.getExpression() + "'.\n"; //$NON-NLS-3$
		}
	}

	private void generateNextIntInformations() {
		information_s += Messages.Visitor_IntRandom;		
	}

	private void generateGetObjectLocationInformations(MethodInvocation node) {
		information_s += Messages.Visitor_GetObj1 + node.getExpression() +Messages.Visitor_GetObj2; 		
	}

	private void generateGetAllObjectsInformations(MethodInvocation node) {
		information_s += Messages.Visitor_AllObj1 + node.getExpression() +"'.\n"; //$NON-NLS-2$
	}

	private void generateAddNodeInformations(MethodInvocation node) {
		information_s += Messages.Visitor_AddNode1 + node.getExpression() + ".\n";		 //$NON-NLS-2$
	}

	private void generateNextDoubleInformations() {
		information_s += Messages.Visitor_RadomDouble1;		
	}

	private void generateMultiplyInformations(MethodInvocation node) {
		List<Expression> argument_s = node.arguments();
		if (argument_s.size() == 1)
			information_s = information_s + Messages.Visitor_MutlipliesInfo1 + node.getExpression() +Messages.Visitor_MutliplesInfo2 + argument_s.get(0) +"'.\n"; //$NON-NLS-3$
	}

	/**
	 * Get information about 'setObjectLocation' method.
	 * @param node
	 */
	private void generateSetObjectLocationInformations(MethodInvocation node) {
		List<Expression> argument_s = node.arguments();
		if (argument_s.size() == 3){	//schedule.repeating(3 parameters)
			information_s = information_s + Messages.Visitor_SetObjLoc1 + argument_s.get(0) + Messages.Visitor_SetObjLoc2 + node.getExpression() + "': \n" //$NON-NLS-3$
								+ Messages.Visitor_SetObjLoc3 + argument_s.get(1) + ";\n" //$NON-NLS-2$
								+ Messages.Visitor_SetObjLoc4 + argument_s.get(2) + ".\n\n"; //$NON-NLS-2$
		}
		else if (argument_s.size() == 2){
			information_s = information_s + Messages.Visitor_SetObjLoc1 + argument_s.get(0) + Messages.Visitor_SetObjLoc2 + node.getExpression() + "':\n" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ "In position: " + argument_s.get(1) + ";\n"; //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Get information about schedule.
	 * @param node
	 */
	private void generateScheduleInformations(MethodInvocation node) {
		List<Expression> argument_s = node.arguments();
		if (argument_s.size() == 4){	//schedule.repeating(4 parameters here)
			information_s = information_s + Messages.Visitor_Schedule1;
			information_s = information_s + Messages.Visitor_Schedule2 + argument_s.get(0) +";\n" //$NON-NLS-2$
								+ Messages.Visitor_Schedule3 + argument_s.get(1) + ";\n" //$NON-NLS-2$
								+ Messages.Visitor_Schedule4 + argument_s.get(2) + ";\n" //$NON-NLS-2$
								+ Messages.Visitor_Schedule5 + argument_s.get(3) + ".\n\n"; //$NON-NLS-2$
		}
		
	}

	@Override
	public boolean visit(IfStatement node) {
		information_s += Messages.Visitor_If1 + node.getExpression() + " "; //$NON-NLS-2$
		return super.visit(node);
	}
	
	public void endVisit(IfStatement node){
		information_s += Messages.Visitor_EndIf;
	}

	public boolean visit(WhileStatement f){
		information_s += Messages.Visitor_While1 + f.getExpression() + " "; //$NON-NLS-2$
		return super.visit(f);
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
}