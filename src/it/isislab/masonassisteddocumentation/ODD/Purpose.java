package it.isislab.masonassisteddocumentation.ODD;

import java.io.Serializable;

/**
 * 
 * @author simrom
 * This class represent Purpose element of ODD.
 */
public class Purpose implements Serializable{
	private static final long serialVersionUID = 1;
	private String modelPurpose;
	public static String serializedName = "purpose.ser";
	
	public void setModelPurpose(String modelPurpose){
		this.modelPurpose = modelPurpose;
	}

	public String getModelPurpose() {
		if (modelPurpose == null)	return "";
		return modelPurpose;
	}
}
