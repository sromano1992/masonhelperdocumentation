package it.isislab.masonassisteddocumentation.ODD;

import it.isislab.masonassisteddocumentation.mason.analizer.GlobalUtility;
import it.isislab.masonassisteddocumentation.mason.control.ConfigFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Logger;

import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * 
 * @author Romano Simone 0512101343
 * This class collect all the informations about
 * model, grouped by ODD logic.
 */
public class ODD implements Serializable{
	private static final long serialVersionUID = 1;
	public static Purpose purpose;
	public static Entitie_s entitie_s;
	public static ProcessOverviewAndScheduling process;
	public static Submodel_s submodel_s;
	public static DesignConcepts designConcepts;
	public static Initialization initialization;
	public static String inputData;
	private static Logger log = Logger.getLogger("global");
	public static String getStandardDefinition = "The model description follows the ODD(Overview, 	Design concepts, Details) protocol (Grimm et al., 2006, this work).";
	private static boolean differentsColors = true;

	public static Purpose getPurpose(){
		if (purpose == null)
			purpose = new Purpose();
		return purpose;
	}

	public static Entitie_s getEntitie_s(){
		if (entitie_s == null)
			entitie_s = new Entitie_s();
		return entitie_s;
	}

	public static ProcessOverviewAndScheduling getProcessOverviewAndScheduling(){
		if (process == null)
			process = new ProcessOverviewAndScheduling();
		return  process;
	}

	public static Submodel_s getSubmodel_s(){
		if (submodel_s == null)
			submodel_s = new Submodel_s();
		return clearSubmodel_s();
	}
	

	/**
	 * Remove void submodels (without description) and duplicate submodels.
	 * @param submodel_s2
	 * @return submodel_s
	 */
	private static Submodel_s clearSubmodel_s() {
		//***********removing void submodel_s***********//
		ArrayList<Submodel> voidSubmodel_s = new ArrayList<Submodel>();
		for (Submodel s : submodel_s.getSubmodel_s()){
			if (s.getDescription().equals(""))	voidSubmodel_s.add(s);
		}
		for (Submodel s : voidSubmodel_s)	submodel_s.getSubmodel_s().remove(s);
		
		//***********removing duplicate submodel_s***********//
		//to remove duplicate entry I create an hashmap with 
		//key = description+name of submodel
		//and entry = submodel. Submodels with equals 
		//description+name will be rewrite (delete).
		HashMap<String,Submodel> hashSubmodel_s = new HashMap<String,Submodel>();
		for (Submodel s : submodel_s.getSubmodel_s()){
			hashSubmodel_s.put(s.getDescription() + s.getName(), s);
		}
		ArrayList<Submodel> newSubmodel_s = new ArrayList<Submodel>();
		Collection<Submodel> collectionSubmodel_s = hashSubmodel_s.values();
		for (Submodel s : collectionSubmodel_s)
			newSubmodel_s.add(s);
		submodel_s.setSubmodel_s(newSubmodel_s);
		return submodel_s;
	}

	public static DesignConcepts getDesignConcepts(){
		if (designConcepts == null)
			designConcepts = new DesignConcepts();
		return designConcepts;
	}

	public static Initialization getInitialization(){
		if (initialization == null)
			initialization = new Initialization();
		return initialization;
	}

	public static void setPurpose(Purpose purpose) {
		ODD.purpose = purpose;
	}

	public static void setEntitie_s(Entitie_s entitie_s) {
		ODD.entitie_s = entitie_s;
	}

	public static void setProcess(ProcessOverviewAndScheduling process) {
		ODD.process = process;
	}

	public static void setSubmodel_s(Submodel_s submodel_s) {
		ODD.submodel_s = submodel_s;
	}

	public static void setDesignConcepts(DesignConcepts designConcepts) {
		ODD.designConcepts = designConcepts;
	}

	public static void setInitialization(Initialization initialization) {
		ODD.initialization = initialization;
	}

	public static void setInputData(String inputData) {
		ODD.inputData = inputData;
	}

	/**
	 * Add entity e in entite_s collection. First there is 
	 * a control to check if Entity is already in collection.
	 * This has sense because also at first run of program
	 * some entities could be get from ODD serialized files. 
	 * @param e
	 */
	public static void addEntity(Entity e){
		boolean alreadyIn = false;
		ArrayList<Entity> entitie_s = getEntitie_s().getEntitie_s();
		for (Entity entity : entitie_s){
			if (entity.getName().equals(e.getName())){
				entity.setDescription(e.getDescription());
				alreadyIn = true;
			}
		}
		if (!alreadyIn)
			getEntitie_s().add(e);
	}

	public static Entity getEntity(String name){
		return getEntitie_s().getEntity(name);
	}

	public static void addVariableToEntity(String entityName, Variable v){
		if (getEntity(entityName) == null)
			addEntity(new Entity(entityName, ""));
		getEntity(entityName).addVariable(v);		
	}

	public static void addProcessOverviewElement(String who, String autoDoWhat, String userDoWhat){
		getProcessOverviewAndScheduling().addProcessOverviewElement(who, autoDoWhat, userDoWhat);
	}

	public static void addSubmodel(Submodel s){
		getSubmodel_s().addSubmodel(s);
	}
	
	/**
	 * This method serialize all objects that it contains
	 * calling "serializeObj" method.
	 */
	public static void serialize(){
		try {
			serializeObj(Purpose.serializedName, getPurpose());
			serializeObj(Entitie_s.serializedName, getEntitie_s());
			serializeObj(ProcessOverviewAndScheduling.serializedName, 
					getProcessOverviewAndScheduling());
			//serializeObj(Submodel_s.serializedName, getSubmodel_s());
			serializeObj(DesignConcepts.serializedName, getDesignConcepts());
			serializeObj(Initialization.serializedName, getInitialization());
			serializeObj("inputData.ser", inputData);
			log.info("ODD object Serialized");
		} catch (IOException e) {
			log.severe("Error serializing ODD objects: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * This method deserialize all objects that it contains
	 * calling "deserializeObj" method.
	 */
	public static void deserialize(){
		try {
			ODD.setPurpose((Purpose) deserializeObj(Purpose.serializedName));
			ODD.setEntitie_s((Entitie_s)deserializeObj(Entitie_s.serializedName));
			ODD.setProcess((ProcessOverviewAndScheduling)deserializeObj(ProcessOverviewAndScheduling.serializedName));
			//ODD.setSubmodel_s((Submodel_s) deserializeObj(Submodel_s.serializedName));
			ODD.setDesignConcepts((DesignConcepts)deserializeObj(DesignConcepts.serializedName));
			ODD.setInitialization((Initialization)deserializeObj(Initialization.serializedName));
			ODD.setInputData((String) deserializeObj("inputData.ser"));
			log.info("ODD object deserialized");
		} catch (IOException e) {
			log.severe("IOException deserializing ODD objects: " + e.getMessage());
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			log.severe("ClassNotFound: " + e.getMessage());
		}
	}

	/**
	 * This method serialize object give in input with name
	 * objName in path "ConfigFile.getODDPath".
	 * @param objName	name to give to file
	 * @param toSerialize	object to serialize
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void serializeObj(String objName, Object toSerialize) throws FileNotFoundException, IOException {
		String serializedObjPath = ConfigFile.gettODDPath() + objName;
		Files.deleteIfExists(Paths.get(serializedObjPath));
		FileOutputStream fileOut = new FileOutputStream(serializedObjPath);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(toSerialize);
		out.close();
		fileOut.close();
		log.info("Serialized: " + objName);
	}

	/**
	 * This method return object read from "ConfigFile.getODDPath() + objname".
	 * @param objName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static Object deserializeObj(String objName) throws FileNotFoundException,IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(ConfigFile.gettODDPath() + objName);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object toReturn = ois.readObject();
		ois.close();
		log .info("Deserialized: " + objName);
		return toReturn;
	}
	
	/**
	 * Return inputData if it isn't null;
	 * else return void String.
	 * @return
	 */
	public static String getInputData(){
		if (differentsColors){
			if (inputData == null)	return "";
			return GlobalUtility.surroundWithSpan
				(GlobalUtility.userOutputColor, inputData);
		}
		else{
			if (inputData == null)	return "";
			return inputData;
		}
	}
}
