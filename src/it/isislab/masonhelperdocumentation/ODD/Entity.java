package it.isislab.masonhelperdocumentation.ODD;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * @author Romano Simone 0512101343
 * This class represent an Entity of 
 * ODD protocol - 3rd phase.
 */
public class Entity  implements Serializable{
	private static final long serialVersionUID = 1;
	private String name;
	private String description;	
	/**
	 * Contains entity's variables 
	 */
	private ArrayList<Variable> variable_s;
	
	public Entity(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		this.variable_s = new ArrayList<Variable>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		if (description == null)	return "";
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void addVariable(Variable v){
		variable_s.add(v);
	}

	public ArrayList<Variable> getVariable_s() {
		HashMap<String, Variable> tmp = new HashMap<String, Variable>();
		for (Variable v : variable_s){
			if (!tmp.containsKey(v.getName()))
				tmp.put(v.getName(), v);
		}
		Collection<Variable> collectionOfVariable = tmp.values();
		ArrayList<Variable> toReturn = new ArrayList<Variable>();
		for (Variable v : collectionOfVariable)
			toReturn.add(v);
		return toReturn;
	}	
	
	public Variable getVariable(String varName){
		for (Variable v : getVariable_s()){
			if (v.getName().equals(varName))	return v;
		}
		return null;
	}
}
