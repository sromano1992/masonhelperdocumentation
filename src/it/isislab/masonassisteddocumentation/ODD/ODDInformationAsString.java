package it.isislab.masonassisteddocumentation.ODD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * Return element printable from ODD.
 * @author Romano Simone 0512101343
 *
 */
public class ODDInformationAsString {
	
	/**
	 * Return an hash map here each entry contains a key for
	 * element's title and value for its content.
	 * First element is page title.
	 * @return
	 */
	public static LinkedHashMap<String, String> getODDDescritpion(){
		String documentDescription_0 = ODD.getStandardDefinition;
		String documentDescription_1 = "This documentation contains information on the simulation model. These information are partially self-generated and partially entered."
				+ "The information is grouped according to the protocol ODD. "
				+ "Each section of the protocol answers some questions. In particular: \n";
		
		LinkedHashMap<String, String> ODD_description = new LinkedHashMap<String, String>(); // key is title; value is
														// description
		ODD_description.put("ODD Description\n", documentDescription_0 + documentDescription_1) ;
		ODD_description.put("Purpose\n", "What is the purpose of the model?\n");
		ODD_description.put("Entities, state variables and scales\n",
						"What kinds of entities are in the model?bBy what state variables, or attributes, are these entities characterized?\n");
		ODD_description.put("Process overview and scheduling\n",
				"Who (i.e., what entity) does what, and in what order?\n");
		ODD_description.put("Design concepts\n",
						"Which general concepts, theories, hypotheses, or modeling approaches are underlying the model's design?"
								+ "What key results or outputs of the model are modeled as emerging from the adaptive traits, or behaviors, of individuals?"
								+ "What adaptive traits do the individuals have? What rules do they have for making decisions or changing behavior in response to changes in themselves or their environment "
								+ "If adaptive traits explicitly act to increase some measure of theindividual's success at meeting some objective, what exactly is that objective and how is it measured? "
								+ "Many individuals or agents (but also organizations and institutions change their adaptive traits over time as a consequence of their experience? If so, how? "
								+ "Prediction is fundamental to successful decision-making; if an agent's adaptive traits or learning procedures are based on estimating future consequences of decisions,howdo agents predict the future conditions (either environmental or internal) they will experience? "
								+ "What internal and environmental state variables are individuals assumed to sense and consider in their decisions? "
								+ "What kinds of interactions among agents are assumed? Are there direct interactions in which individuals encounter and affect others, or are interactions indirect, e.g., via competition for amediating resource? "
								+ "What processes are modeled by assuming they are random or partly random? "
								+ "Do the individuals form or belong to aggregations that affect, and are affected by, the individuals? "
								+ "What data are collected from the ABM for testing, understanding, and analyzing it, and how and when are they collected?\n");
		ODD_description.put("Initialization\n",
						"What is the initial state of the model world, i.e., at time t = 0 of a simulation run?\n");
		ODD_description.put("Input data\n",
						"Does the model use input from external sources such as data files or other models to represent processes that change over time?\n");
		ODD_description.put("Submodels\n",
						"Information about submodels identified in seciont 'Process overview and scheduling'.\n");
		return ODD_description;
	}
	
	/**
	 * Return model purpose as linekdHasMap where key is title of element and 
	 * value is model purpose.
	 * @return
	 */
	public static LinkedHashMap<String, String> getPurpose(){
		LinkedHashMap<String, String> purpose = new LinkedHashMap<String, String>();
		if (ODD.getPurpose().getModelPurpose().equals(""))
			purpose.put("Purpose\n", "No purpose defined.");
		else
			purpose.put("Purpose\n", ODD.getPurpose().getModelPurpose());
		return purpose;
	}
	
	/**
	 * Return an ArrayList of LinkedHashMasp. Each element of arraylist
	 * is an HashMap for an entity.
	 * @return
	 */
	public static ArrayList<LinkedHashMap<String, String>> getEntities(){
		ArrayList<LinkedHashMap<String, String>> toReturn = new ArrayList<LinkedHashMap<String,String>>();
		
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		title.put("Entities,  variables, and scales", "");
		toReturn.add(title);
		
		ArrayList<Entity> entitie_s = ODD.getEntitie_s().getEntitie_s();
		for (Entity e : entitie_s){
			LinkedHashMap<String, String> entity = new LinkedHashMap<String, String>();
			entity.put("Entity: " + e.getName()+"_subTitle\n", e.getDescription()+"\n");
			ArrayList<Variable> variable_s = e.getVariable_s();
			for (Variable v : variable_s){
				entity.put(v.getName()+"\n", v.toString());
			}
			toReturn.add(entity);
		}
		return toReturn;
	}
	
	/**
	 * Return an HashMap of Process element
	 * @return
	 */
	public static LinkedHashMap<String, String> getProcessElement(){
		LinkedHashMap<String, String> toReturn = new LinkedHashMap<String, String>();

		toReturn.put("Process, overview and schedule\n", "");
		
		ArrayList<ProcessOverviewElement> processe_s = ODD.getProcessOverviewAndScheduling().getProcessOverviewElement_s();
		for (ProcessOverviewElement p : processe_s){
			toReturn.put(p.getElementName()+"\n", p.toString()+"\n");
		}
		return toReturn;
	}
	
	/**
	 * Return an HashMap of Process element
	 * @return
	 */
	public static LinkedHashMap<String, String> getDesignConcpets(){
		LinkedHashMap<String, String> toReturn = new LinkedHashMap<String, String>();

		toReturn.put("Design concepts\n", "");
		if (ODD.designConcepts.getAutoBasicPrinciples().equals("") && ODD.designConcepts.getUserBasicPrinciples().equals(""))
			toReturn.put("Basic principles\n", "No Basic principles defined.\n");
		else
			toReturn.put("Basic principles\n", ODD.designConcepts.getAutoBasicPrinciples() + "\n" + ODD.designConcepts.getUserBasicPrinciples() + "\n");
		if (ODD.designConcepts.getAutoEmergence().equals("") && ODD.designConcepts.getUserEmergence().equals(""))
			toReturn.put("Emergence\n", "No Emergence defined.\n");
		else
			toReturn.put("Emergence\n", ODD.designConcepts.getAutoEmergence() + "\n" + ODD.designConcepts.getUserEmergence() + "\n");
		if (ODD.designConcepts.getAutoAdaption().equals("") && ODD.designConcepts.getUserAdaption().equals(""))
			toReturn.put("Adaption\n", "No Adaption defined.\n");
		else
			toReturn.put("Adaption\n", ODD.designConcepts.getAutoAdaption() + "\n" + ODD.designConcepts.getUserAdaption() + "\n");
		if (ODD.designConcepts.getAutoObjectives().equals("") && ODD.designConcepts.getUserObjectives().equals(""))
			toReturn.put("Objectives\n", "No Objectives defined.\n");
		else
			toReturn.put("Objectives\n", ODD.designConcepts.getAutoObjectives() + "\n" + ODD.designConcepts.getUserObjectives() + "\n");
		if (ODD.designConcepts.getAutoLearning().equals("") && ODD.designConcepts.getUserLearning().equals(""))
			toReturn.put("Learning\n", "No Learning defined.\n");
		else
			toReturn.put("Learning\n", ODD.designConcepts.getAutoLearning() + "\n" + ODD.designConcepts.getUserLearning() + "\n");
		if (ODD.designConcepts.getAutoPrediction().equals("") && ODD.designConcepts.getUserPrediction().equals(""))
			toReturn.put("Prediction\n", "No Prediction defined.\n");
		else
			toReturn.put("Prediction\n", ODD.designConcepts.getAutoPrediction() + "\n" + ODD.designConcepts.getUserPrediction() + "\n");
		if (ODD.designConcepts.getAutoSensing().equals("") && ODD.designConcepts.getUserSensing().equals(""))
			toReturn.put("Sensing\n", "No Sensing defined.\n");
		else		
			toReturn.put("Sensing\n", ODD.designConcepts.getAutoSensing() + "\n" + ODD.designConcepts.getUserSensing() + "\n");
		if (ODD.designConcepts.getAutoInteraction().equals("") && ODD.designConcepts.getUserInteraction().equals(""))
			toReturn.put("Interaction\n", "No Interaction defined.\n");
		else
			toReturn.put("Interaction\n", ODD.designConcepts.getAutoInteraction() + "\n" + ODD.designConcepts.getUserInteraction() + "\n");
		if (ODD.designConcepts.getAutoStochasticity().equals("") && ODD.designConcepts.getUserStochasticity().equals(""))
			toReturn.put("Stochasticity\n", "No Stochasticity defined.\n");
		else
			toReturn.put("Stochasticity\n", ODD.designConcepts.getAutoStochasticity() + "\n" + ODD.designConcepts.getUserStochasticity() + "\n");
		if (ODD.designConcepts.getAutoCollectives().equals("") && ODD.designConcepts.getUserCollectives().equals(""))
			toReturn.put("Collectives\n", "No Collectives defined.\n");
		else
			toReturn.put("Collectives\n", ODD.designConcepts.getAutoCollectives() + "\n" + ODD.designConcepts.getUserCollectives() + "\n");
		if (ODD.designConcepts.getAutoObservation().equals("") && ODD.designConcepts.getUserObservation().equals(""))
			toReturn.put("Observation\n", "No Observation defined.\n");
		else
			toReturn.put("Observation\n", ODD.designConcepts.getAutoObservation() + "\n" + ODD.designConcepts.getUserObservation() + "\n");
		
		return toReturn;
	}
	
	/**
	 * Return an HashMap of initialization
	 * @return
	 */
	public static LinkedHashMap<String, String> getInitialization(){
		LinkedHashMap<String, String> toReturn = new LinkedHashMap<String, String>();

		String init_0 = ODD.getInitialization().getAutoInitialization();
		String init_1 = ODD.getInitialization().getUserInitialization();
		init_0 = init_0.replace("<h1>", "");
		init_0 = init_0.replace("</h1>", "\n");
		init_0 = init_0.replace("<h2>", "\n");
		init_0 = init_0.replace("</h2>", "");
		init_0 = init_0.replace("<br>", "");
		init_1 = init_1.replace("<h1>", "");
		init_1 = init_1.replace("</h1>", "\n");
		init_1 = init_1.replace("<h2>", "\n");
		init_1 = init_1.replace("</h2>", "");
		init_1 = init_1.replace("<br>", "");
		toReturn.put("Initialization\n", init_0 + "\n" + init_1);
		
		return toReturn;
	}
	
	/**
	 * Return an HashMap of inputData
	 * @return
	 */
	public static LinkedHashMap<String, String> getInputData(){
		LinkedHashMap<String, String> toReturn = new LinkedHashMap<String, String>();

		String inputData = ODD.inputData;
		if (inputData == null || inputData == "")
			toReturn.put("Input Data\n", "No input data defined.\n");
		else
			toReturn.put("Input Data\n", inputData + "\n");
		
		return toReturn;
	}
	
	/**
	 * Return an HashMap of Submodels
	 * @return
	 */
	public static LinkedHashMap<String, String> getSubmodels(){
		LinkedHashMap<String, String> toReturn = new LinkedHashMap<String, String>();

		ArrayList<Submodel> submodel_s = ODD.getSubmodel_s().getSubmodel_s();
		toReturn.put("Submodels\n", "");
		for (Submodel s : submodel_s)
			toReturn.put(s.getName() + "\n", s.getDescription());		
		
		return toReturn;
	}
	
	public static String ODDToString(){
		String toReturn = "";		
		toReturn = getString(toReturn, getODDDescritpion().entrySet());
		toReturn = getString(toReturn, getPurpose().entrySet());
		for (LinkedHashMap<String, String> entitie_s : getEntities())
			getString(toReturn, entitie_s.entrySet());
		toReturn = getString(toReturn, getProcessElement().entrySet());
		toReturn = getString(toReturn, getDesignConcpets().entrySet());
		toReturn = getString(toReturn, getInitialization().entrySet());
		toReturn = getString(toReturn, getInputData().entrySet());
		toReturn = getString(toReturn, getSubmodels().entrySet());
		return toReturn;
	}

	private static String getString(String toReturn, Set<Entry<String, String>> tmpEntrySet) {
		for (Entry<String, String> e : tmpEntrySet){
			toReturn += e.getKey() + e.getValue();
		}
		return toReturn;
	}
}
