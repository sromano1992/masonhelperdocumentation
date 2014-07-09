package it.isislab.masonassisteddocumentation.mason.analizer;

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
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class AgentAnalizer implements Analizer{
	private ICompilationUnit agentICompilationUnit;
	private CompilationUnit compilationUnit;
	private AST root;
	private ArrayList<Parameter> visitedParameters;
	public static String[] positionClass = {"Int2D", "Double2D", "Int3D", "Double3D",};
	public static String steppableDescription = "By being Steppable, this class represent an agent that can be\n"
			+ "on the Schedule to have its step method called at various times\n"
			+ "int the future. This graduates this agent from being a mere object\n"
			+ " in the simulation to being something potentially approximating a real agent.\n"
			+ "When this agent is stepped, it is passed the SimState.\n";
	private static Logger log = Logger.getLogger("global");
	
	public AgentAnalizer(ICompilationUnit agentCU) {
		agentICompilationUnit = agentCU;
        compilationUnit = GlobalUtility.getCompilationUnit(agentCU); 
        root = GlobalUtility.getAstFromCompilationUnit(compilationUnit);
        visitedParameters = new ArrayList<Parameter>();
        log.info("AgentAnalizer created");
	}


	public void rewrite() {
		File sourceFile = new File(agentICompilationUnit.getResource().getRawLocation().toOSString());
		FileOutputStream fooStream;
		try {
			fooStream = new FileOutputStream(sourceFile, false);
			String code = compilationUnit.toString();
			byte[] myBytes = code.getBytes();
			fooStream.write(myBytes);
			fooStream.close();
		} catch (FileNotFoundException e) {
			log.severe("Agent file not found to: " + agentICompilationUnit.getResource().getRawLocation().toOSString() + ".");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}


	/**
	 * This method return the descriprion of this Agent.
	 * @return	JavadocComment if there is a description; else null.
	 */
	public Javadoc getModelDescription(){
		List<TypeDeclaration> types = compilationUnit.types();
		TypeDeclaration agent = types.get(0);
		return agent.getJavadoc();
	}
	
	/**
	 * This method set the description of model
	 * to modelDescription value.
	 * @param modelDescription
	 */
	public void setClassDescription(String modelDescription){
		List<TypeDeclaration> types = compilationUnit.types();		
		TypeDeclaration agent = types.get(0);	//get class definition (element in position 0)
		modelDescription = "@ingroup entities\n*\n*" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, modelDescription);	
		GlobalUtility.setJavadocToType(root, agent, modelDescription);
	}
	
	/**
	 * Return a list of visited parameter (visited means 
	 * that parameters are already in SimulationGroup).
	 * @return
	 */
	public ArrayList<Parameter> getVisitedParameters() {
		return visitedParameters;
	}

	/**
	 * This method extract a list of VariableDeclarationFragment
	 * for positions variables.
	 * @return	ArrayList of positions as VariableDeclarationFragment
	 */
	public ArrayList<Parameter> getPositionsParameter(){
		if (GlobalUtility.getAllParameters(compilationUnit) == null)	return null;
		ArrayList<Parameter> toReturn = new ArrayList<Parameter>();
		for (Parameter p : GlobalUtility.getAllParameters(compilationUnit)){
				for (int i=0; i<positionClass.length; i++){
					if (p.getVariableType().equalsIgnoreCase(positionClass[i]))	toReturn.add(p);
				}				
		}
		return toReturn;
	}	
	
	/**
	 * Retirn CompilationUnit.
	 * @return
	 */
	public CompilationUnit getCompilationUnit(){
		return compilationUnit;
	}
	
	public AST getRoot(){
		return root;
	}
	
	/**
	 * Return a string representation of positionClass array.
	 * @return
	 */
	public String getPositionClass(){
		String toReturn = "";
		for (int i=0; i<positionClass.length; i++){
			toReturn = toReturn + positionClass[i] + " - ";
		}
		return toReturn.substring(0, toReturn.length()-3);
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
	
	/**
	 * Return parameter_s that are not visited until now.
	 * @return
	 */
	public ArrayList<Parameter> getNotVisitedParameter_s(){
		ArrayList<Parameter> remainingParameters = new ArrayList<Parameter>();
		ArrayList<Parameter> allParameters = GlobalUtility.getAllParameters(getCompilationUnit());
		for (Parameter p : allParameters)
			if (!(alreadyVisited(p)))	remainingParameters.add(p);	
		return remainingParameters;
	}
	
	/**
	 * Return class name of this agent.
	 * @return
	 */
	public String getClassName(){
		return agentICompilationUnit.getElementName();
	}
	
	public String toString(){
		return compilationUnit.toString();
	}
}
