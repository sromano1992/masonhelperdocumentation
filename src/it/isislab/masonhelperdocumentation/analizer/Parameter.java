package it.isislab.masonhelperdocumentation.analizer;

import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

/**
 * This class contains information about Parameter.
 * It helps to get parameters informations.
 * @author Romano Simone 0512101343
 *
 */
public class Parameter {
	private FieldDeclaration field;
	private VariableDeclarationFragment variable;
	
	public Parameter(FieldDeclaration field,
			VariableDeclarationFragment variable) {
		this.field = field;
		this.variable = variable;
	}

	public FieldDeclaration getField() {
		return field;
	}

	public void setField(FieldDeclaration field) {
		this.field = field;
	}

	public VariableDeclarationFragment getVariable() {
		return variable;
	}

	public void setVariable(VariableDeclarationFragment variable) {
		this.variable = variable;
	}
	
	public String getVariableName(){
		return variable.getName().toString();
	}
	
	public String getVariableType(){
		return this.field.getType().toString();
	}
	
	public String getJavadoc(){
		return GlobalUtility.getJavadocContent(field.getJavadoc());
	}
	
	public String getInitializer(){
		if (variable.getInitializer()!=null)
			return variable.getInitializer().toString();
		return "";
	}
	
	public boolean equals(Parameter p){
		return (p.getInitializer().equals(this.getInitializer()) &&
				p.getVariableName().equals(this.getVariableName()) &&
				p.getVariableType().equals(this.getVariableType()));
	}
	
	public String toString(){
		return this.getVariableType() + " - " +this.getVariableName();
	}
}
