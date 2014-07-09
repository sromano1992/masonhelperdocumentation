package it.isislab.masonassisteddocumentation.mason.analizer;


import it.isislab.masonassisteddocumentation.ODD.ODD;
import it.isislab.masonassisteddocumentation.mason.control.ConfigFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.TypeDeclaration;


/**
 * Get information about SimState class from
 * ICompilationUnit
 * @author Romano Simone 0512101343
 */
public class SimStateAnalizer implements Analizer{
	private static String[] simFiledGrid = {"AbstractGrid2D","AbstractGrid3D", "DenseGrid2D",
											"DenseGrid3D", "DoubleGrid2D", "DoubleGrid3D",
											"IntGrid2D", "IntGrid3D", "ObjectGrid2D", "ObjectGrid3D",
											"SparseGrid2D", "SparseGrid3D", "Continuous2D"};
	public static String simStateDescription = "SimState encapsule entire model of simulation. It contains a discrete event schedule on which will be schedule\n"
					+"various agents to be called at some time in the future. This facility is a MASON model's representation\n" 
					+"of time. Additionally, the model should contains one or more fields to represent space. A field is nothing more\n"
					+"than an arbitrary data structure relating various objects or values together. MASON provides a number of \n"
					+"built-in fields, such as networks, continuous space, and grids.";
	private static Logger log = Logger.getLogger("global");
	private CompilationUnit compilationUnit;
	private ICompilationUnit simStateICompilationUnit;
	private AST root;
	private ArrayList<Parameter> visitedParameters;
	/**
	 * This variable will be update in each step of wizard.
	 * At the end will contain all collected information.
	 */
	private String modelDescription;	 

	/**
	 * Constructor of SimStateAnalizer
	 * @param simStateCU ICompilationUnit to get absolute path and
	 *	to create CompilationUnit(JavaParser library).
	 *  ICompilationUnit can be recovered from ProjectAnalizer.
	 * @throws FileNotFoundException 
	 * @throws ParseException 
	 */
	public SimStateAnalizer(ICompilationUnit simStateCU){
        simStateICompilationUnit = simStateCU;
        compilationUnit = GlobalUtility.getCompilationUnit(simStateICompilationUnit); 
        root = GlobalUtility.getAstFromCompilationUnit(compilationUnit);
        visitedParameters = new ArrayList<Parameter>();
        log.info("SimStateAnalizer created");
	}
	
	/**
	 * Return the CompilationUnit 
	 * @return
	 */
	public CompilationUnit getCompilationUnit(){
		return compilationUnit;
	}	
	
	/**
	 * This method return the descriprion of Simulation Model
	 * that is placed on the SimState class definition.
	 * @return	JavadocComment if there is a description; else null.
	 */
	public Javadoc getModelDescription(){
		List<TypeDeclaration> types = compilationUnit.types();
		TypeDeclaration simState = types.get(0);
		return simState.getJavadoc();
	}
	
	/**
	 * Return description placed on class definition
	 * deleting "purpose" string
	 * @return	model description
	 */
	public String getModelDescriptionAsString(){
		String toReturn = GlobalUtility.getJavadocContent(getModelDescription());
		return toReturn.replace("purpose", "");
	}
	
	/**
	 * This method set the description of model
	 * to modelDescription value.
	 * @param modelDescription
	 */
	public void setModelDescription(String modelDescription){
		this.modelDescription = modelDescription;	//to use it in rewrite method
		List<TypeDeclaration> types = compilationUnit.types();		
		TypeDeclaration simState = types.get(0);	//get class definition (element in position 0)
		GlobalUtility.setJavadocToType(root, simState, modelDescription);
	}
	
	/**
	 * This method add information to 'modelDescription' variable.
	 * The idea is that for each new information extract from code,
	 * we add this information to 'modelDescription' string to have,
	 * at the end of wizard, all collected information in simulationModel
	 * detailed description section in documentation.
	 * @param newInformation
	 */
	public void addToModeldescription(String newInformation){
		modelDescription = modelDescription + "\n" + newInformation;
		log.info("Add information: " + newInformation);
	}
	
	/**
	 * This method extract a list of VariableDeclarationFragment
	 * for grids variables.
	 * @return	ArrayList of grids as VariableDeclarationFragment
	 */
	public ArrayList<Parameter> getFieldsParameters(){
		if (GlobalUtility.getAllParameters(compilationUnit) == null)	return null;
		ArrayList<Parameter> toReturn = new ArrayList<Parameter>();
		for (Parameter p : GlobalUtility.getAllParameters(compilationUnit)){
				for (int i=0; i<simFiledGrid.length; i++){
					if (p.getVariableType().equalsIgnoreCase(simFiledGrid[i]))	toReturn.add(p);
				}				
		}
		return toReturn;
	}	
	
	@Override
	/**
	 * Write new CompilationUnit to .java source file
	 */
	public void rewrite() {	
		File sourceFile = new File(simStateICompilationUnit.getResource().getRawLocation().toOSString());
		FileOutputStream fileStream;
		try {
			fileStream = new FileOutputStream(sourceFile, false);
			//imageSelected?
			String imgDirPath = ConfigFile.getValue("imgPath");
			ArrayList<String> screenShot_s = imgInDirectory(imgDirPath);
			String imageImport = "";
			if (screenShot_s != null){
				for (String s : screenShot_s){
					if (s.endsWith(".png") || s.endsWith("jpg"))
						imageImport = imageImport + "\\image html " + s + "\n";
				}
			}
			String group_sDefinition_s =   "@defgroup SimulationModel Simulation Model \n *" + ODD.getStandardDefinition +"<br>\n" + GlobalUtility.documentDescription + "<br>\n" + imageImport + "\n"
					+ "@defgroup purpose Purpose \n *" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, ODD.getPurpose().getModelPurpose()) + "\n@ingroup SimulationModel\n"
					+ "@defgroup entities Entities, state variables, and scales\n@ingroup SimulationModel\n"
					+ "@defgroup process Process, overview and schedule\n@ingroup SimulationModel\n"
					+ "@defgroup design Design Concepts\n *" + ODD.getDesignConcepts().toString() +"\n@ingroup SimulationModel\n"
					+ "@defgroup initialization Initialization\n *" + ODD.getInitialization().toString() + "\n@ingroup SimulationModel\n"
					+ "@defgroup input Input data\n *" +ODD.getInputData() + "\n@ingroup SimulationModel\n"
					+ "@defgroup submodels Submodels\n *" + ODD.getSubmodel_s().toString() +"\n@ingroup SimulationModel\n";

			setModelDescription(group_sDefinition_s);
			String code = compilationUnit.toString();
			byte[] myBytes = code.getBytes();
			fileStream.write(myBytes);
			fileStream.close();
			log.info("SimState rewrite");
		} catch (FileNotFoundException e) {
			log.severe("SimState file not found to: " + simStateICompilationUnit.getResource().getRawLocation().toOSString() + ".");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	private ArrayList<String> imgInDirectory(String path){
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> toReturn = new ArrayList<String>();
		if (listOfFiles != null){
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        toReturn.add(listOfFiles[i].getAbsolutePath());
		      }
		    }
		    return toReturn;
		}
		return null;
	}
	
	/**
	 * Return AST of SimState.
	 * @return AST
	 */
	public AST getRoot() {
		return root;
	}
	
	/**
	 * Return the list of parameters visited at moment.
	 * For 'visited' I mean that I already added this parameters
	 * to SimulationGroup in some part of wizard
	 * @return
	 */
	public ArrayList<Parameter> getVisitedParameters() {
		return visitedParameters;
	}
	
	/**
	 * Return true if parameter p has been already visited.
	 * For 'visited' I mean that I already added this parameters
	 * to SimulationGroup in some part of wizard
	 * @param p
	 * @return
	 */
	public boolean alreadyVisited(Parameter p){
		for (Parameter par : visitedParameters){
			if (par.equals(p))	return true;
		}
		return false;
	}
	
	public void test(){	

		//this.absolutePath = simStateICompilationUnit.getResource().getRawLocation().toOSString();
		
		List<TypeDeclaration> types = compilationUnit.types();
		TypeDeclaration simState = types.get(0);
		FieldDeclaration[] fields = simState.getFields();
		for (FieldDeclaration f:fields){			
			System.out.println(f.getJavadoc());
			f.setJavadoc(GlobalUtility.createJavadoc(root, "prova"));
		}
	}

	/**
	 * Return a string representation of simFieldGrid array.
	 * @return
	 */
	public String getFieldsString() {
		String toReturn = "";
		for (int i=0; i<simFiledGrid.length; i++){
			toReturn = toReturn + simFiledGrid[i] + " - ";
		}
		return toReturn.substring(0, toReturn.length()-3);
	}
	
	public String getClassName(){
		return simStateICompilationUnit.getElementName();
	}
	
	public String toString(){
		return compilationUnit.toString();
	}
}
