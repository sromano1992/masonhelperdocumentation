package it.isislab.masonhelperdocumentation.visitor;

import it.isislab.masonhelperdocumentation.ODD.ODD;
import it.isislab.masonhelperdocumentation.ODD.Submodel;
import it.isislab.masonhelperdocumentation.analizer.GlobalUtility;
import it.isislab.masonhelperdocumentation.analizer.Method;

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
        information_s += Messages.StartMethodVisitor_InvokeMethod + node.getName() +".\n"; //$NON-NLS-2$
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
        	information_s = information_s + Messages.StartMethodVisitor_SeeSubmodel;
        	Submodel toAdd = new Submodel(node.getName().toString(), cu);
        	ODD.addSubmodel(toAdd);
        	log.info(Messages.StartMethodVisitor_SubmodelFound + node.getName() +"'.");        	 //$NON-NLS-2$
        }
        return super.visit(node);
	}
	
	private void generateAddEdgeInformations(MethodInvocation node) {
		List<Expression> argument_s = node.arguments();
		if (argument_s.size() == 3){
			information_s += Messages.StartMethodVisitor_AddEdgeInformation +node.getExpression() +Messages.StartMethodVisitor_AddEdgeInfo1 + argument_s.get(0) +Messages.StartMethodVisitor_AddEdgeInfo2 + argument_s.get(1) +Messages.StartMethodVisitor_AddEdgeInfo3;
		}
		if (argument_s.size() == 1){
			information_s += Messages.StartMethodVisitor_AddEdgeInfo4 + argument_s.get(0) + Messages.StartMethodVisitor_AddEdgeInfo5 + node.getExpression() + "'.\n"; //$NON-NLS-3$
		}
	}

	private void generateNextIntInformations() {
		information_s += Messages.StartMethodVisitor_IntRandom;		
	}

	private void generateGetObjectLocationInformations(MethodInvocation node) {
		information_s += Messages.StartMethodVisitor_GetObj1 + node.getExpression() +Messages.StartMethodVisitor_GetObj2; 		
	}

	private void generateGetAllObjectsInformations(MethodInvocation node) {
		information_s += Messages.StartMethodVisitor_AllObj1 + node.getExpression() +"'.\n"; //$NON-NLS-2$
	}

	private void generateAddNodeInformations(MethodInvocation node) {
		information_s += Messages.StartMethodVisitor_AddNode1 + node.getExpression() + ".\n";		 //$NON-NLS-2$
	}

	private void generateNextDoubleInformations() {
		information_s += Messages.StartMethodVisitor_RadomDouble1;		
	}

	private void generateMultiplyInformations(MethodInvocation node) {
		List<Expression> argument_s = node.arguments();
		if (argument_s.size() == 1)
			information_s = information_s + Messages.StartMethodVisitor_MutlipliesInfo1 + node.getExpression() +Messages.StartMethodVisitor_MutliplesInfo2 + argument_s.get(0) +"'.\n"; //$NON-NLS-3$
	}

	/**
	 * Get information about 'setObjectLocation' method.
	 * @param node
	 */
	private void generateSetObjectLocationInformations(MethodInvocation node) {
		List<Expression> argument_s = node.arguments();
		if (argument_s.size() == 3){	//schedule.repeating(3 parameters)
			information_s = information_s + Messages.StartMethodVisitor_SetObjLoc1 + argument_s.get(0) + Messages.StartMethodVisitor_SetObjLoc2 + node.getExpression() + "': \n" //$NON-NLS-3$
								+ Messages.StartMethodVisitor_SetObjLoc3 + argument_s.get(1) + ";\n" //$NON-NLS-2$
								+ Messages.StartMethodVisitor_SetObjLoc4 + argument_s.get(2) + ".\n\n"; //$NON-NLS-2$
		}
		else if (argument_s.size() == 2){
			information_s = information_s + Messages.StartMethodVisitor_SetObjLoc1 + argument_s.get(0) + Messages.StartMethodVisitor_SetObjLoc2 + node.getExpression() + "':\n" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
			information_s = information_s + Messages.StartMethodVisitor_Schedule1;
			information_s = information_s + Messages.StartMethodVisitor_Schedule2 + argument_s.get(0) +";\n" //$NON-NLS-2$
								+ Messages.StartMethodVisitor_Schedule3 + argument_s.get(1) + ";\n" //$NON-NLS-2$
								+ Messages.StartMethodVisitor_Schedule4 + argument_s.get(2) + ";\n" //$NON-NLS-2$
								+ Messages.StartMethodVisitor_Schedule5 + argument_s.get(3) + ".\n\n"; //$NON-NLS-2$
		}
		
	}

	@Override
	public boolean visit(IfStatement node) {
		information_s += Messages.StartMethodVisitor_If1 + node.getExpression() + " "; //$NON-NLS-2$
		return super.visit(node);
	}
	
	public void endVisit(IfStatement node){
		information_s += Messages.StartMethodVisitor_EndIf;
	}

	public boolean visit(WhileStatement f){
		information_s += Messages.StartMethodVisitor_While1 + f.getExpression() + " "; //$NON-NLS-2$
		return super.visit(f);
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

	public void endVisit(MethodInvocation node){
		information_s += Messages.StartMethodVisitor_EndInvocation + node.getName() + Messages.StartMethodVisitor_EndInvocation1;
	}
	
	public String getInformation_s() {
		return information_s + Messages.StartMethodVisitor_EndMethod;
	}
}
