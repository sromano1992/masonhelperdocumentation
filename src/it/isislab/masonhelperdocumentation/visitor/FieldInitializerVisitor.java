package it.isislab.masonhelperdocumentation.visitor;

import java.util.List;

import it.isislab.masonhelperdocumentation.analizer.Parameter;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;

/**
 * This visitor will get information about variables constructor.
 * Implici parameter is the compilation unit of class that 
 * contains the variable. Explicit parameter is the parameter
 * that we search in compilation Unit.
 * ES. simStateCU.accept(new FieldInitializerVisitor(myParameter))
 * will search all parameters in simStateCU and when myParameter
 * is found will extract a list of constructor arguments.
 * @author Romano Simone 0512101343
 *
 */
public class FieldInitializerVisitor extends ASTVisitor {
	private String information_s;
	private Parameter parameter;
	private List<Expression> constructorParameter_s;
	
	public FieldInitializerVisitor(Parameter parameter){
		this.parameter = parameter;
	}
	
	/**
 	 * Here we will extract parameters of field 
	 * constructors. 
	 * (Ex: new Grid(width,length...)->extract width,length,...).
	 */
	public boolean visit(Assignment node){
		if (node.getRightHandSide() instanceof ClassInstanceCreation){
			ClassInstanceCreation constructor = (ClassInstanceCreation) node.getRightHandSide();
			if (node.getLeftHandSide().toString().equals(parameter.getVariableName()))
				constructorParameter_s = constructor.arguments();
		}
		return super.visit(node);
	}

	public String getInformation_s() {
		return information_s;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public List<Expression> getConstructorParameter_s() {
		return constructorParameter_s;
	}
	
}
