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
	private static Logger log = Logger.getLogger("global");
	
	public StartMethodVisitor(CompilationUnit cu) {
		super();
		this.cu = cu;
		information_s = "";
	}

	public boolean visit(MethodInvocation node) {
        information_s += "Invoke method " + node.getName() +".\n";
        if (node.getName().toString().equals("scheduleRepeating"))
        	generateScheduleInformations(node); 
        else if (node.getName().toString().equals("setObjectLocation"))
        	generateSetObjectLocationInformations(node);
        else if (node.getName().toString().equals("getObjectLocation"))
        	generateGetObjectLocationInformations(node);
        else if (node.getName().toString().equals("multiply"))
        	generateMultiplyInformations(node);
        else if (node.getName().toString().equals("nextDouble"))
        	generateNextDoubleInformations();
        else if (node.getName().toString().equals("nextInt"))
        	generateNextIntInformations();
        else if (node.getName().toString().equals("addEdge"))
        	generateAddEdgeInformations(node);
        else if (node.getName().toString().equals("addNode"))
        	generateAddNodeInformations(node);
        else if (node.getName().toString().equals("getAllObjects"))
        	generateGetAllObjectsInformations(node);
        else{	//add submodel for last ODD phase ("submodels")
        	information_s = information_s + " (Submodels section for more info).\n";
        	Submodel toAdd = new Submodel(node.getName().toString(), cu);
        	ODD.addSubmodel(toAdd);
        	log.info("New submodel found: '" + node.getName() +"'.");        	
        }
        return super.visit(node);
	}
	
	private void generateAddEdgeInformations(MethodInvocation node) {
		List<Expression> argument_s = node.arguments();
		if (argument_s.size() == 3){
			information_s += "Adds an edge to the network '" +node.getExpression() +"' connecting the '" + argument_s.get(0) +"' node to the '" + argument_s.get(1) +"' node.\n";
		}
		if (argument_s.size() == 1){
			information_s += "Adds edge '" + argument_s.get(0) + "' to network '" + node.getExpression() + "'.\n";
		}
	}

	private void generateNextIntInformations() {
		information_s += "Is generate a random integer.\n";		
	}

	private void generateGetObjectLocationInformations(MethodInvocation node) {
		information_s += "The position of '" + node.getExpression() +"' is recovered.\n"; 		
	}

	private void generateGetAllObjectsInformations(MethodInvocation node) {
		information_s += "Returns all objects stored in the field '" + node.getExpression() +"'.\n";
	}

	private void generateAddNodeInformations(MethodInvocation node) {
		information_s += "Adds a node to the Network " + node.getExpression() + ".\n";		
	}

	private void generateNextDoubleInformations() {
		information_s += "Is generate a random double.\n";		
	}

	private void generateMultiplyInformations(MethodInvocation node) {
		List<Expression> argument_s = node.arguments();
		if (argument_s.size() == 1)
			information_s = information_s + "Multiplies '" + node.getExpression() +"' against the scalar '" + argument_s.get(0) +"'.\n";
	}

	/**
	 * Get information about 'setObjectLocation' method.
	 * @param node
	 */
	private void generateSetObjectLocationInformations(MethodInvocation node) {
		List<Expression> argument_s = node.arguments();
		if (argument_s.size() == 3){	//schedule.repeating(3 parameters)
			information_s = information_s + "Move agent '" + argument_s.get(0) + "' on grid '" + node.getExpression() + "': \n"
								+ "In x position: " + argument_s.get(1) + ";\n"
								+ "And y position: " + argument_s.get(2) + ".\n\n";
		}
		else if (argument_s.size() == 2){
			information_s = information_s + "Move agent '" + argument_s.get(0) + "' on grid '" + node.getExpression() + "':\n"
					+ "In position: " + argument_s.get(1) + ";\n";
		}
	}

	/**
	 * Get information about schedule.
	 * @param node
	 */
	private void generateScheduleInformations(MethodInvocation node) {
		List<Expression> argument_s = node.arguments();
		if (argument_s.size() == 4){	//schedule.repeating(4 parameters here)
			information_s = information_s + "Here an agent is added to schedule: ";
			information_s = information_s + "Time of scheduling: " + argument_s.get(0) +";\n"
								+ "Order of scheduling: " + argument_s.get(1) + ";\n"
								+ "Agent to schedule: " + argument_s.get(2) + ";\n"
								+ "Interval of scheduling: " + argument_s.get(3) + ".\n\n";
		}
		
	}

	@Override
	public boolean visit(IfStatement node) {
		information_s += "If " + node.getExpression() + " ";
		return super.visit(node);
	}
	
	public void endVisit(IfStatement node){
		information_s += "End if.";
	}

	public boolean visit(ForStatement f){
		information_s += "There is a for loop that while " + f.getExpression() + " ";
		return super.visit(f);
	}

	public void endVisit(ForStatement node){
		information_s += "End for.";
	}
	
	public boolean visit (DoStatement d){
		information_s += "While " + d.getExpression() + " ";
		return super.visit(d);
	}

	public void endVisit(DoStatement node){
		information_s += "After this do-while statement: ";
	}
	
	public boolean visit(SwitchStatement s){
		information_s += "There is a switch on " + s.getExpression() + " ";
		return super.visit(s);
	}

	public void endVisit(SwitchStatement node){
		information_s += "After this switch-statement:";
	}
	
	public boolean visit(TypeDeclaration t){
		information_s += "New type declaration: " + t.getName();
		return super.visit(t);
	}

	public void endVisit(TypeDeclaration node){
		information_s += "After this type declaration:";
	}
	
	public boolean visit(Assignment node){
		information_s += "There is an assignment for " + node.getLeftHandSide() + " to value " + node.getRightHandSide() + " ";
		return super.visit(node);
	}

	public void endVisit(Assignment node){
		information_s += "After this assignment:";
	}

	public void endVisit(MethodInvocation node){
		information_s += "After invocation of " + node.getName() + " method:";
	}
	
	public String getInformation_s() {
		return information_s + "method terminates.\n";
	}
}
