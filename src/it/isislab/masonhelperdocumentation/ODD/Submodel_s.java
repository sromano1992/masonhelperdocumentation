package it.isislab.masonhelperdocumentation.ODD;

import it.isislab.masonhelperdocumentation.analizer.GlobalUtility;

import java.io.Serializable;
import java.util.ArrayList;

public class Submodel_s  implements Serializable{
	private static final long serialVersionUID = 1;
	private ArrayList<Submodel> submodel_s;
	public static String serializedName = "submodel_s.ser";
	public static boolean differentsColors = true;

	public Submodel_s() {
		super();
		this.submodel_s = new ArrayList<Submodel>();
	}
	
	public void addSubmodel(Submodel s){
		submodel_s.add(s);
	}
	
	public ArrayList<Submodel> getSubmodel_s(){
		return this.submodel_s;
	}
	
	public String toString(){
		if (differentsColors){
			String toReturn = "";
			for (Submodel sub : getSubmodel_s()){
				toReturn += GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, sub.toString());
			}
			return toReturn;
		}
		else{
			String toReturn = "";
			for (Submodel sub : getSubmodel_s()){
				toReturn += sub.toString();
			}
			return toReturn;
		}
	}
}
