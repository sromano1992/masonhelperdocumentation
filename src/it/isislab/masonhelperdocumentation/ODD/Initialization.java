package it.isislab.masonhelperdocumentation.ODD;

import java.io.Serializable;
import java.util.ArrayList;

public class Initialization  implements Serializable{
	private static final long serialVersionUID = 1;
	public static String serializedName = "initialization.ser";
	/**
	 * AutoInitialization is generated from Entities's variables.
	 * Will be return a concatenation of variables initial value.
	 */
	private String autoInitialization, userInitialization;

	public String getAutoInitialization() {
		String autoInitialization = "At time t=0:\n";
		Entitie_s entitie_s = ODD.getEntitie_s();
		for (Entity e: entitie_s.getEntitie_s()){
			ArrayList<Variable> variable_s = e.getVariable_s();		
			if (variable_s.size() != 0){
				autoInitialization += "Entity '" + e.getName() + "':\n";
				for (Variable v :variable_s)
					autoInitialization += "variable '" + v.getName() + "' has initial value = " + v.getInitialValue() + ";\n";
				}
		}
		return autoInitialization;
	}

	public void setAutoInitialization(String autoInitialization) {
		this.autoInitialization = autoInitialization;
	}

	public String getUserInitialization() {
		return userInitialization;
	}

	public void setUserInitialization(String userInitialization) {
		this.userInitialization = userInitialization;
	}
	
	public String toString(){
		return getAutoInitialization() + "\n" + userInitialization;
	}
}
