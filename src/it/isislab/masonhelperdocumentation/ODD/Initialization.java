package it.isislab.masonhelperdocumentation.ODD;

import it.isislab.masonhelperdocumentation.analizer.GlobalUtility;

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
	private static boolean differentsColor = true;

	public String getAutoInitialization() {
		String autoInitialization = "<h1>At time t=0:</h1>\n";
		Entitie_s entitie_s = ODD.getEntitie_s();
		for (Entity e: entitie_s.getEntitie_s()){
			ArrayList<Variable> variable_s = e.getVariable_s();		
			if (variable_s.size() != 0){
				autoInitialization += "<h2>Entity " + e.getName() + "</h2>\n";
				for (Variable v :variable_s)
					autoInitialization += "variable '" + v.getName() + "' has initial value = " + v.getInitialValue() + ";<br>";
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
		if (differentsColor){
			String toReturn = GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, getAutoInitialization()) + "\n";
			if (userInitialization != null || !userInitialization.equals(""))
				return toReturn + "<h2>Other</h2><br>" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, userInitialization);
			return toReturn;
		}
		else{
			String toReturn = getAutoInitialization() + "\n";
			if (userInitialization != null || !userInitialization.equals(""))
				return toReturn + "<h2>Other</h2><br>" + userInitialization;
			return toReturn;
		}
	}
}
