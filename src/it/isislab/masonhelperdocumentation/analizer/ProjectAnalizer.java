package it.isislab.masonhelperdocumentation.analizer;

import java.lang.instrument.ClassDefinition;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;

/**
 * This class will get information on 
 * MASON project given input.
 * @author Romano Simone 0512101343
 *
 */
public class ProjectAnalizer {
	private IJavaProject javaProject;
	private ICompilationUnit simStateCU;
	private ArrayList<ICompilationUnit> agent_s_CU;
	private ICompilationUnit guiStateCompilationUnit;
	private static Logger log = Logger.getLogger("global");
	
	public ProjectAnalizer(IJavaProject javaProject){
		this.javaProject = javaProject;
		if (isMasonProject())	getSimStateAndAgent();
	}

	private void getSimStateAndAgent() {
		try{
			agent_s_CU = new ArrayList<ICompilationUnit>();
			IPackageFragment[] packages = javaProject.getPackageFragments();
			for (IPackageFragment mypackage : packages) {
		      if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
		    	  for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
		    		  IType[] allTypes = unit.getAllTypes();
		    		  for(IType type:allTypes){
		    			  if (type.getSuperclassName()!=null){	//class should not extends others class (type.getSuperlassName will be null)
		    				  if (type.getSuperclassName().equalsIgnoreCase("SimState"))	this.simStateCU = unit;
		    				  if (type.getSuperclassName().equalsIgnoreCase("GUIState"))	this.guiStateCompilationUnit = unit;
		    			  }
		    			  String[] interfaces = type.getSuperInterfaceNames();
		    			  for (String interfaceName : interfaces){
		    				  if (interfaceName.equalsIgnoreCase("steppable")){
		    					  this.agent_s_CU.add(unit);
		    				  }
		    			  }
		    		  }
	    	    }	
		      }
			}
		}catch(JavaModelException e){
			log.severe("Exception getting SimState, Agent and GUIState\n" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Check if select project is a Mason project
	 * @return true if Project use a mason.*.jar library
	 */
	public boolean isMasonProject() {
		try {
			IPackageFragmentRoot[] elements = javaProject.getPackageFragmentRoots();
			for (IPackageFragmentRoot p:elements){
				if (p.getElementName().contains("mason")){
					log.info("Selected project uses MASON library");
					return true;
				}
			}
		} catch (JavaModelException e) {
			log.severe("Exception on getPackageFragmentRoots(); message: " + e.getMessage());
			e.printStackTrace();
		}		
		return false;
	}
	
	public IJavaProject getJavaProject() {
		return javaProject;
	}

	public void setJavaProject(IJavaProject javaProject) {
		this.javaProject = javaProject;
	}

	public ICompilationUnit getSimStateCU() {
		return simStateCU;
	}

	/**
	 * Project should have many agents. Then
	 * there is a list of agents and this method
	 * return agent in position 'number'.
	 * @param number of agent that method return.
	 * @return	ICompilationUnit of agent in 'number' position.
	 */
	public ICompilationUnit getAgentCU(int number) {
		return agent_s_CU.get(number);
	}

	public ICompilationUnit getGuiStateCU() {
		return guiStateCompilationUnit;
	}	
	
	public String getProjectName(){
		return javaProject.getElementName();
	}
	
	public int getAgentsNum(){
		return agent_s_CU.size();
	}
}
