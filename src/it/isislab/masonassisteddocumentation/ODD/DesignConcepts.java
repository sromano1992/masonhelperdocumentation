package it.isislab.masonassisteddocumentation.ODD;

import it.isislab.masonassisteddocumentation.mason.analizer.GlobalUtility;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * This class groups DesignConcepts of ODD protocol.
 * @author Romano Simone 0512101343
 *
 */
public class DesignConcepts  implements Serializable{
	private static final long serialVersionUID = 1;
	private String autoBasicPrinciples, userBasicPrinciples;
	private String autoEmergence, userEmergence;
	private String autoAdaption, userAdaption;
	private String autoObjectives, userObjectives;
	private String autoLearning, userLearning;
	private String autoPrediction, userPrediction;
	private String autoSensing, userSensing;
	private String autoInteraction, userInteraction;
	private String autoStochasticity, userStochasticity;
	private String autoCollectives, userCollectives;
	private String autoObservation, userObservation;
	private static boolean differentsColor = true;
	public static String serializedName = "designConcept_s.ser";
	private static Logger log = Logger.getLogger("global");
	
	public String getAutoBasicPrinciples() {
		if (autoBasicPrinciples == null)	return "";
		return autoBasicPrinciples;
	}
	public void setAutoBasicPrinciples(String autoBasicPrinciples) {
		this.autoBasicPrinciples = autoBasicPrinciples;
	}
	public String getUserBasicPrinciples() {
		if (userBasicPrinciples == null)	return "";
		return userBasicPrinciples;
	}
	public void setUserBasicPrinciples(String userBasicPrinciples) {
		this.userBasicPrinciples = userBasicPrinciples;
	}
	public String getAutoEmergence() {
		if (autoEmergence == null)	return "";
		return autoEmergence;
	}
	public void setAutoEmergence(String autoEmergence) {
		this.autoEmergence = autoEmergence;
	}
	public String getUserEmergence() {
		if (userEmergence == null)	return "";
		return userEmergence;
	}
	public void setUserEmergence(String userEmergence) {
		this.userEmergence = userEmergence;
	}
	public String getAutoAdaption() {
		if (autoAdaption == null)	return "";
		return autoAdaption;
	}
	public void setAutoAdaption(String autoAdaption) {
		this.autoAdaption = autoAdaption;
	}
	public String getUserAdaption() {
		if (userAdaption == null)	return "";
		return userAdaption;
	}
	public void setUserAdaption(String userAdaption) {
		this.userAdaption = userAdaption;
	}
	public String getAutoObjectives() {
		if (autoObjectives == null)	return "";
		return autoObjectives;
	}
	public void setAutoObjectives(String autoObjectives) {
		this.autoObjectives = autoObjectives;
	}
	public String getUserObjectives() {
		if (userObjectives == null)	return "";
		return userObjectives;
	}
	public void setUserObjectives(String userObjectives) {
		this.userObjectives = userObjectives;
	}
	public String getAutoLearning() {
		if (autoLearning == null)	return "";
		return autoLearning;
	}
	public void setAutoLearning(String autoLearning) {
		this.autoLearning = autoLearning;
	}
	public String getAutoSensing() {
		if (autoSensing == null)	return "";
		return autoSensing;
	}
	public void setAutoSensing(String autoSensing) {
		this.autoSensing = autoSensing;
	}
	public String getUserSensing() {
		if (userSensing == null)	return "";
		return userSensing;
	}
	public void setUserSensing(String userSensing) {
		this.userSensing = userSensing;
	}
	public String getAutoInteraction() {
		if (autoInteraction == null)	return "";
		return autoInteraction;
	}
	public void setAutoInteraction(String autoInteraction) {
		this.autoInteraction = autoInteraction;
	}
	public String getUserInteraction() {
		if (userInteraction == null)	return "";
		return userInteraction;
	}
	public void setUserInteraction(String userInteraction) {
		this.userInteraction = userInteraction;
	}
	public String getAutoStochasticity() {
		if (autoStochasticity == null)	return "";
		return autoStochasticity;
	}
	public void setAutoStochasticity(String autoStochasticity) {
		this.autoStochasticity = autoStochasticity;
	}
	public String getUserStochasticity() {
		if (userStochasticity == null)	return "";
		return userStochasticity;
	}
	public void setUserStochasticity(String userStochasticity) {
		this.userStochasticity = userStochasticity;
	}
	public String getAutoCollectives() {
		if (autoCollectives == null)	return "";
		return autoCollectives;
	}
	public void setAutoCollectives(String autoCollectives) {
		this.autoCollectives = autoCollectives;
	}
	public String getUserCollectives() {
		if (userCollectives == null)	return "";
		return userCollectives;
	}
	public void setUserCollectives(String userCollectives) {
		this.userCollectives = userCollectives;
	}
	public String getAutoObservation() {
		if (autoObservation == null)	return "";
		return autoObservation;
	}
	public void setAutoObservation(String autoObservation) {
		this.autoObservation = autoObservation;
	}
	public String getUserObservation() {
		if (userObservation == null)	return "";
		return userObservation;
	}
	public void setUserObservation(String userObservation) {
		this.userObservation = userObservation;
	}
	public String getAutoPrediction() {
		if (autoPrediction == null)	return "";
		return autoPrediction;
	}
	public void setAutoPrediction(String autoPrediction) {
		this.autoPrediction = autoPrediction;
	}
	public void setUserLearning(String userLearning) {
		this.userLearning = userLearning;
	}
	public String getUserPrediction() {
		if (userPrediction == null)	return "";
		return userPrediction;
	}
	public void setUserPrediction(String userPrediction) {
		this.userPrediction = userPrediction;
	}
	public String getUserLearning() {
		if (userLearning == null)	return "";
		return userLearning;
	}	
	public String toString(){
		if (differentsColor ){
			String toReturn = "<h1>Basic principles</h1>\n";
			toReturn += GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, getAutoBasicPrinciples()) + "\n" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, getUserBasicPrinciples());
			toReturn += "<h1>Emergence</h1>\n";
			toReturn += GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, getAutoEmergence()) + "\n" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, getUserEmergence());
			toReturn += "<h1>Adaption</h1>\n";
			toReturn += GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, getAutoAdaption()) + "\n" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, getUserAdaption());
			toReturn += "<h1>Objectives</h1>\n";
			toReturn += GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, getAutoObjectives()) + "\n" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, getUserObjectives());
			toReturn += "<h1>Learning</h1>\n";
			toReturn += GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, getAutoLearning()) + "\n" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, getUserLearning());
			toReturn += "<h1>Prediction</h1>\n";
			toReturn += GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, getAutoPrediction()) + "\n" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, getUserPrediction());
			toReturn += "<h1>Sensing</h1>\n";
			toReturn += GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, getAutoSensing()) + "\n" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, getUserSensing());
			toReturn += "<h1>Interaction</h1>\n";
			toReturn += GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, getAutoInteraction()) + "\n" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, getUserInteraction());
			toReturn += "<h1>Stochasticity</h1>\n";
			toReturn += GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, getAutoStochasticity()) + "\n" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, getUserStochasticity());
			toReturn += "<h1>Collectives</h1>\n";
			toReturn += GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, getAutoCollectives()) + "\n" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, getUserCollectives());
			toReturn += "<h1>Observation</h1>\n";
			toReturn += GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, getAutoObservation()) + "\n" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, getUserObservation());
			log.info("Returning string: " + toReturn);
			return toReturn;
		}
		else{
			String toReturn = "<h1>Basic principles</h1>\n";
			toReturn += getAutoBasicPrinciples() + "\n" + getUserBasicPrinciples();
			toReturn += "<h1>Emergence</h1>\n";
			toReturn += getAutoEmergence() + "\n" + getUserEmergence();
			toReturn += "<h1>Adaption</h1>\n";
			toReturn += getAutoAdaption() + "\n" + getUserAdaption();
			toReturn += "<h1>Objectives</h1>\n";
			toReturn += getAutoObjectives() + "\n" + getUserObjectives();
			toReturn += "<h1>Learning</h1>\n";
			toReturn += getAutoLearning() + "\n" + getUserLearning();
			toReturn += "<h1>Prediction</h1>\n";
			toReturn += getAutoPrediction() + "\n" + getUserPrediction();
			toReturn += "<h1>Sensing</h1>\n";
			toReturn += getAutoSensing() + "\n" + getUserSensing();
			toReturn += "<h1>Interaction</h1>\n";
			toReturn += getAutoInteraction() + "\n" + getUserInteraction();
			toReturn += "<h1>Stochasticity</h1>\n";
			toReturn += getAutoStochasticity() + "\n" + getUserStochasticity();
			toReturn += "<h1>Collectives</h1>\n";
			toReturn += getAutoCollectives() + "\n" + getUserCollectives();
			toReturn += "<h1>Observation</h1>\n";
			toReturn += getAutoObservation() + "\n" + getUserObservation();
			log.info("Returning string: " + toReturn);
			return toReturn;
		}
	}
}
