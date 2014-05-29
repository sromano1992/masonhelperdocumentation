package it.isislab.masonhelperdocumentation.analizer;

import it.isislab.masonhelperdocumentation.ODD.ODD;
import it.isislab.masonhelperdocumentation.mason.control.ConfigFile;
import it.isislab.masonhelperdocumentation.visitor.CodeVisitor;
import it.isislab.masonhelperdocumentation.visitor.FieldInitializerVisitor;
import it.isislab.masonhelperdocumentation.visitor.StartMethodVisitor;
import it.isislab.masonhelperdocumentation.visitor.StepMethodVisitor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.internal.compiler.ast.ConstructorDeclaration;

/**
 * This class contains "global" variable
 * @author Romano Simone 0512101343
 *
 */
public class GlobalUtility {
	private static IJavaProject javaProject;
	private static Logger log = Logger.getLogger("global");
	private static ProjectAnalizer projectAnalizer;
	private static SimStateAnalizer simStateAnalizer;
	private static GUIStateAnalizer guiStateAnalizer;
	private static ArrayList<AgentAnalizer> agent_sAnalizer;
	public static int actualAgent = 0;
	
	public GlobalUtility(IJavaProject javaProject){
		GlobalUtility.setJavaProject(javaProject);
		agent_sAnalizer = new ArrayList<AgentAnalizer>();
	}
	
	/**
	 * Return the same istance of SimStateAnalizer 
	 * for each method invocation.
	 * @return	SimStateAnalizer
	 */
	public static SimStateAnalizer getSimStateAnalizer(){
		if (simStateAnalizer!=null)	return simStateAnalizer;
		if (getJavaProject()!=null){
			simStateAnalizer = new SimStateAnalizer(getProjectAnalizer().getSimStateCU());		
			log.severe("New istance of simStateAnalizer returned");
			return simStateAnalizer;
		}
		return null;
	}
	
	/**
	 * Return the same istance of GUIStateAnalizer 
	 * for each method invocation.
	 * @return	GUIStateAnalizer
	 */
	public static GUIStateAnalizer getGUIStateAnalizer(){
		if (guiStateAnalizer!=null)	return guiStateAnalizer;
		if (getJavaProject()!=null){
			guiStateAnalizer = new GUIStateAnalizer(getProjectAnalizer().getGuiStateCU());		
			log.severe("New istance of GUIStateAnalizer returned");
			return guiStateAnalizer;
		}
		return null;
	}
	
	/**
	 * Return the same istance of actual AgentAnalizer 
	 * for each method invocation.
	 * @return	SimStateAnalizer; else null.
	 */
	public static AgentAnalizer getAgentAnalizer() {
		if (agent_sAnalizer.size() != 0)	return agent_sAnalizer.get(actualAgent);
		if (getJavaProject()!=null){
			agent_sAnalizer.add(new AgentAnalizer(getProjectAnalizer().getAgentCU(actualAgent)));
			log.severe("New istance of " + actualAgent + " AgentAnalizer returned");
			return agent_sAnalizer.get(actualAgent);
		}
		return null;
	}
	
	public static int getNumAgent_s(){
		return projectAnalizer.getAgentsNum();
	}
	
	/**
	 * Return the next AgentAnalizer.
	 * @return next AgentAnalizer if exist; else null.
	 */
	public static AgentAnalizer getNextAgentAnalizer(){
		actualAgent++;
		if (getProjectAnalizer().getAgentsNum() <= actualAgent)	return null;
		agent_sAnalizer.add(new AgentAnalizer(getProjectAnalizer().getAgentCU(actualAgent)));
		return agent_sAnalizer.get(actualAgent);		
	}
	
	public static void resetActualAgent(){
		actualAgent = 0;
	}
	
	public static ProjectAnalizer getProjectAnalizer(){
		if (projectAnalizer!=null)	return projectAnalizer;
		projectAnalizer = new ProjectAnalizer(getJavaProject());
		return projectAnalizer;
	}

	public static IJavaProject getJavaProject() {
		return javaProject;
	}

	public static void setJavaProject(IJavaProject javaProject) {
		GlobalUtility.javaProject = javaProject;
	}
	
	/**
	 * Set a javadoc comment to type from AST root
	 * @param root
	 * @param type
	 * @param commentValue
	 */
	public static void setJavadocToType(AST root, TypeDeclaration type, String commentValue){
		type.setJavadoc(createJavadoc(root, commentValue));
	}
	
	public static void setJavadocToField(AST root, FieldDeclaration f, String commentValue){
		f.setJavadoc(createJavadoc(root, commentValue));
	}
	
	public static void setJavadocToParameter(AST root, Parameter p, String commentValue){
		setJavadocToField(root, p.getField(), commentValue);
	}
	
	public static void setJavadocToMethod(AST root, Method m, String commentValue){
		m.getMethod().setJavadoc(createJavadoc(root, commentValue));
	}
	
	/**
	 * Create a Javadoc from String
	 * @param root
	 * @param content
	 * @return
	 */
	public static Javadoc createJavadoc(AST root, String content){
		Javadoc toReturn = root.newJavadoc();
		TagElement comment = root.newTagElement();
		comment.setTagName(content);
		toReturn.tags().add(comment);
		return toReturn;
	}
	
	public static CompilationUnit getCompilationUnit(ICompilationUnit iCompilationUnit){
		ASTParser parser = ASTParser.newParser(AST.JLS4); 
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(iCompilationUnit);
        parser.setResolveBindings(true); // we need bindings later on
    	return (CompilationUnit) parser.createAST(null);   
	}
	
	public static AST getAstFromCompilationUnit(CompilationUnit compilationUnit){
		return compilationUnit.getAST();
	}
	
	public static String getJavadocContent(Javadoc javadoc){
		/**
		 * When I Wrote It, Only God and I Knew the Meaning; Now God Alone Knows
		 */
		if (javadoc == null)	return "";
		String toReturn = "";
		List<TagElement> tags = javadoc.tags();
		for (TagElement t : tags){
			List<ASTNode> fragments = t.fragments();
			for (ASTNode n : fragments){
				if (n instanceof TextElement){	
					toReturn = toReturn + "\n" + ((TextElement) n).getText();				
				}
			}
		}
		return clearString(toReturn);
	}
	
	/**
	 * Delete start '\n' and ' ' from string.
	 * @param s
	 * @return
	 */
	public static String clearString(String s){
		//start
		while(s.startsWith("\n"))	s = s.substring(1);
		while(s.startsWith(" "))	s = s.substring(1);
		while(s.startsWith("\n"))	s = s.substring(1);
		while(s.startsWith(" "))	s = s.substring(1);
		//end
		while(s.endsWith("\n"))	s = s.substring(0, s.length()-1);
		while(s.endsWith(" "))	s = s.substring(0, s.length()-1);
		while(s.endsWith("\n"))	s = s.substring(0, s.length()-1);
		while(s.endsWith(" "))	s = s.substring(0, s.length()-1);
		return s;
	}
	
	/**
	 * Return all parameter in CompilationUnit
	 * @param cu
	 * @return
	 */
	public static ArrayList<Parameter> getAllParameters(CompilationUnit cu){
		ArrayList<Parameter> toReturn = new ArrayList<Parameter>();
		List<TypeDeclaration> types = cu.types();
		TypeDeclaration classOfCU = types.get(0);
		FieldDeclaration[] fields = classOfCU.getFields();
		for (FieldDeclaration f:fields){
			List<FieldDeclaration> fragments = f.fragments();
			for (Object o : fragments){
				if (o instanceof VariableDeclarationFragment){
					VariableDeclarationFragment variable = (VariableDeclarationFragment) o;
					toReturn.add(new Parameter(f, variable));
				}
			}
		}
		return toReturn;
	}
	
	/**
	 * Return Method list from a CompilationUnit.
	 * @param cu	CompilationUnit
	 * @return	ArrayList<Method>
	 */
	public static ArrayList<Method> getAllMethods(CompilationUnit cu){
		ArrayList<Method> toReturn = new ArrayList<Method>();
		List<TypeDeclaration> types = cu.types();
		TypeDeclaration classOfCU = types.get(0);
		MethodDeclaration[] methods = classOfCU.getMethods();
		for (MethodDeclaration m : methods){
			toReturn.add(new Method(m));
		}
		return toReturn;
	}
	 /**
	  * Return the list of constructor input.
	  * Example: grid = new IntGrid2d(width,height,0) -> return {"width","height","0"}.
	  * @param parameter
	  * @return
	  */
	public static List<Expression> getConstructorVariableInput(CompilationUnit whereSearch, Parameter parameter){
		String initialization = parameter.getInitializer();
		FieldDeclaration field = parameter.getField();
		FieldInitializerVisitor visitor = new FieldInitializerVisitor(parameter);
		whereSearch.accept(visitor);	
		log.info("Found constructor parameters for: " + parameter.getVariable() + "->" + visitor.getConstructorParameter_s());
		return visitor.getConstructorParameter_s();
	}
	
	/**
	  * Return the list of constructor input.
	  * Example: grid = new IntGrid2d(width,height,0) -> return {"width","height","0"}.
	  * @param parameter
	  * @return
	  */
	public static String[] getConstructorVariableInput(String initialization){
		String lastPart = initialization.substring(initialization.indexOf("(")+1, initialization.lastIndexOf(")"));
		String[] variables = lastPart.split(",");
		return variables;
	}
	
	/**
	 * Return parameter associated to parameterName if exist; else null;
	 * @param parameterName name of parameter to search.
	 * @param cu CompilationUnit in which will be searched Parameter.
	 * @return Parameter if exist; else null.
	 */
	public static Parameter getParameterFromString(String parameterName, CompilationUnit cu){
		parameterName = parameterName.replace(" ", "");	//contructor should be 'new IntGrid2d( width  , height  );
		parameterName = parameterName.replace("this.", "");	//some variable should be 'this.name'; we will search 'name';
		ArrayList<Parameter> allParameter = getAllParameters(cu);
		for (Parameter p : allParameter){
			if (p.getVariableName().equals(parameterName))	return p;
		}		
		return null;
	}
	
	/**
	 * Add '@ingroup SimulationModel' to p
	 * @param p Parameter
	 */
	public static void addToGroup(Parameter p) {
		if (!(p.getJavadoc().contains("@ingroup SimulationModel")))
			GlobalUtility.setJavadocToField(simStateAnalizer.getRoot(), p.getField(), p.getJavadoc() + " @ingroup SimulationModel");;		
	}
	
	public static String getStartMethodInformation(Method start){
		StartMethodVisitor cv = new StartMethodVisitor(simStateAnalizer.getCompilationUnit());	//start method is in simState
		start.getMethod().getBody().accept(cv);
		return cv.getInformation_s();
	}
	
	/**
	 * Return a list of invocations to method of input MethodDeclaration
	 * @param cu	CompilationUnit of class that contains m
	 * @param m	Method of which will be extract all method invocations
	 * @return	List of method invocations contains in m
	 */
	public static ArrayList<MethodInvocation> getMethodInvocations(CompilationUnit cu, MethodDeclaration m){
		final HashMap<MethodDeclaration, ArrayList<MethodInvocation>> invocationsForMethods = new HashMap<MethodDeclaration, ArrayList<MethodInvocation>>();
        cu.accept(new ASTVisitor() {	//visit all class method and, for each method, store it in
        								// invocationsForMethods and add each methodInvocation
            private MethodDeclaration activeMethod;

            @Override
            public boolean visit(MethodDeclaration node) {
                activeMethod = node;
                return super.visit(node);
            }

            @Override
            public boolean visit(MethodInvocation node) {
                if (invocationsForMethods.get(activeMethod) == null) {
                    invocationsForMethods.put(activeMethod, new ArrayList<MethodInvocation>());
                }
                invocationsForMethods.get(activeMethod).add(node);
                return super.visit(node);
            }
        });
        //invocationsForMethdos.get(m) contains an arraylist of method invocation for method m
        return invocationsForMethods.get(m);
	}

	
	/**
	 * Return information about agent's Step method.
	 * @param step	Step method.
	 * @return	String.
	 */
	public static String getStepMethodInformation(Method step){
		StepMethodVisitor sv = new StepMethodVisitor(getAgentAnalizer().getCompilationUnit());
		if (step != null){
			step.getMethod().accept(sv);
			return sv.getInformation_s();
		}
		return "";
	}
	
	public static String getObjectLocationInfo(Method m) {
		String toReturn = "";
		Block body = m.getMethod().getBody();
		String[] line_s = body.toString().split(";");	//get each line of method
		for (String line : line_s){
			if (line.contains("setObjectLocation")){
				String setObjectLine = clearString(line);
				String[] parameters = getConstructorVariableInput(setObjectLine);
				if (parameters.length == 2){
					String toAdd = "The agent '" + parameters[0] + "' is set to location '" + parameters[1]+ "'.\n";
					toReturn = toReturn + toAdd;
				}
				if (parameters.length == 3){
					String toAdd = "The agent '" + parameters[0] + "' is set to location '" + parameters[1] + "," + parameters[2] +"'.\n";
					toReturn = toReturn + toAdd;
				}
			}
		}
		return toReturn;
	}
	
	/**
	 * Return 'start' method from SimState.
	 * @return Method start.
	 */
	public static Method getStartMethod(){
		ArrayList<Method> simStateMethods = getAllMethods(simStateAnalizer.getCompilationUnit());
		for (Method m : simStateMethods)
			if (m.getName().equals("start"))	return m;
		return null;
	}
	
	public static Method getStepMethod(){
		ArrayList<Method> agentMethods = getAllMethods(agent_sAnalizer.get(actualAgent).getCompilationUnit());
		for (Method m : agentMethods)
			if (m.getName().equals("step"))	return m;
		return null;
	}
	
	/**
	 * This method search method with name "name" in 
	 * compilationUnit "cu" and return a Method object.
	 * @param name
	 * @param cu
	 * @return
	 */
	public static Method getMethod(String name, CompilationUnit cu){
		ArrayList<Method> allMethods = getAllMethods(cu);
		for (Method m : allMethods)
			if (m.getName().equals(name))	return m;
		return null;
	}
	
	/**
	 * Return an instance of Method for methodInvocation gived
	 * in input; null if method not exist in compilationUnit.
	 * @param invocation
	 * @param cu
	 * @return
	 */
	public static Method getMethodFrom(String methodName, CompilationUnit cu){
		return getMethod(methodName, cu);
	}
	
	/**
	 * For GUIState class, search in method m if 
	 * there is a call to 'setObjectLocation'.
	 * @param m
	 * @return
	 */
	public static String getPortrayalInformation(Method m){
		String toReturn = "";
		String body = m.getMethod().getBody().toString();
		String[] line_s = body.split(";");
		for (String line : line_s){
			line = clearString(line);
			if (line.contains(".setField(")){
				String portrayal = line.substring(0, line.indexOf("."));	//gridName.setObjectLocation(... -> get 'gridName'
				String relativeField = line.substring(line.indexOf("("), line.lastIndexOf(")")+1);	//gridName.setObjectLocation(relativeField) -> get 'relativeField'
				toReturn = toReturn + "The '" + portrayal +"' is relative to field '" + relativeField + "'.\n";
			}
		}
		return toReturn;
	}
	
	/**
	 * Add a class represented by CompilationUnit parameter to 
	 * SimulationGroup with description 'classDescription'
	 * @param cu
	 * @param classDescription
	 */
	public static void addClassToGroup(String groupName, CompilationUnit cu, String classDescription){
		List<TypeDeclaration> types = cu.types();		
		TypeDeclaration classType = types.get(0);	//get class definition (element in position 0)
		String toAdd = "@ingroup " + groupName + "\n*" + classDescription;	
		GlobalUtility.setJavadocToType(cu.getAST(), classType, toAdd);
	}
	
	/**
	 * Set all variable to null.
	 * Static variables doesn't lost their value
	 * in same instance of eclipse! Than here 
	 * is forced their delete.
	 */
	public static void setAllToNull() {
		simStateAnalizer = null;
		agent_sAnalizer = null;
		guiStateAnalizer = null;
		projectAnalizer = null;
		javaProject = null;
		actualAgent = 0;
		ODD.setDesignConcepts(null);
		ODD.setEntitie_s(null);
		ODD.setInitialization(null);
		ODD.setInputData(null);
		ODD.setProcess(null);
		ODD.setPurpose(null);
		ODD.setSubmodel_s(null);
		log.info("Clear resources...");
	}

	/**
	 * Write modifications in each source file.
	 */
	public static void rewriteAll(){
		//call rewrite on all project class
		getSimStateAnalizer().rewrite();
		for (AgentAnalizer agent : agent_sAnalizer)	agent.rewrite();
		getGUIStateAnalizer().rewrite();
		log.info("Rewrite all source file...");
	}
	
	/**
	 * create doxygen config file;
	 * run doxygen.
	 * @return 
	 */
	public static Process doxygenRun(){
		String output = ConfigFile.getValue("output");
		String outputDirectory = "";
		String doxygenPath = ConfigFile.getValue("doxygenPath");
		String imgPath = ConfigFile.getValue("imgPath");
		if (imgPath == null)	imgPath = "";
		else	outputDirectory = output;
		//creatingConfigFile
		try {
			FileWriter fstreamWrite = new FileWriter(new File(output + File.separator + getProjectAnalizer().getProjectName() + "_config"));
	    	BufferedWriter out = new BufferedWriter(fstreamWrite);
	    	out.write("INPUT = " + getProjectAnalizer().getSimStateCU().getResource().getRawLocation().toOSString().substring(0, getProjectAnalizer().getSimStateCU().getResource().getRawLocation().toOSString().lastIndexOf(File.separator)) + "\n"
	    			+ "PROJECT_NAME = " + getProjectAnalizer().getProjectName() + "\n"
	    			+ "INLINE_SOURCES = YES" + "\n"
	    			+ "OUTPUT_DIRECTORY = " + outputDirectory + "\n"
	    			+ "EXTRACT_ALL = YES" + "\n"
	    			+ "EXTRACT_PRIVATE = YES" + "\n"
	    			+ "EXTRACT_STATIC = YES" + "\n"
	    			+ "GENERATE_PERLMOD = YES" + "\n"
	    			+ "USE_PDFLATEX = YES" + "\n"
	    			+ "PERLMOD_LATEX = YES" + "\n"
	    			+ "REFERENCED_BY_RELATION = YES" + "\n"
	    			+ "REFERENCES_RELATION = YES" + "\n"
	    			+ "GENERATE_TREEVIEW = YES" + "\n"
	    			+ "#DOT configuration#" +"\n"	//will be optional
	    			+ "CLASS_DIAGRAMS = YES" +"\n"
	    			+ "HAVE_DOT = YES" + "\n"
	    			+ "DOT_NUM_THREADS = 0" + "\n"
	    			+ "DOT_FONTNAME = Helvetica" + "\n"
	    			+ "DOT_FONTSIZE = 10" + "\n"
	    			+ "DOT_FONTPATH = " + ConfigFile.getValue("graphvizPath") + "\n"
	    			+ "DOT_PATH = " + ConfigFile.getValue("graphvizPath") + "\n"
	    			+ "CLASS_GRAPH = YES" +"\n"
	    			+ "COLLABORATION_GRAPH = YES" + "\n"
	    			+ "GROUP_GRAPHS = YES" + "\n"
	    			+ "UML_LOOK = YES" + "\n"
	    			+ "UML_LIMIT_NUM_FIELDS = 10" +"\n"
	    			+ "INCLUDE_GRAPH = YES" + "\n"
	    			+ "CALL_GRAPH = YES" + "\n"
	    			+ "INCLUDED_BY_GRAPH = YES" + "\n"
	    			+ "CALLER_GRAPH = YES" +"\n"
	    			+ "GRAPHICAL_HIERARCHY = YES" + "\n"
	    			+ "DIRECTORY_GRAPH = YES" + "\n"
	    			+ "DOT_IMAGE_FORMAT = png" + "\n"
	    			+ "DOT_PATH = \"C:/Program Files (x86)/Graphviz2.34\"" +"\n"
	    			+ "DOT_CLEANUP = YES");
	    	log.info("Make config file at: " + output);
	    	out.flush();
			out.close();
			String doxygenExecutable = ConfigFile.getValue("doxygenPath") + File.separator + "doxygen.exe";
			log.info("Executing: " + "\"" + doxygenExecutable + "\" " + output + File.separator + getProjectAnalizer().getProjectName() + "_config");
			
			Runtime runtime = Runtime.getRuntime() ;
			Process shellProcess = runtime.exec("\"" + doxygenExecutable + "\" " + output + File.separator + getProjectAnalizer().getProjectName() + "_config") ;
			return shellProcess;
		} catch (IOException e) {
			log.severe("IOException creating doxygen config file");
			e.printStackTrace();
		}
		return null;		
	}
	
	public static boolean containsSameWords(String first, String second){
		int tolerance = 5;
		int differences = 0;
		String[] wordsInFirst = first.split("[^a-zA-Z']+");
		String[] wordsInSecond = second.split("[^a-zA-Z']+");
		for (int i=0; i<wordsInFirst.length; i++){
			if (wordsInSecond.length <= i)	return false;
			if (!(wordsInFirst[i].equals(wordsInSecond[i]))){
				differences++;
			}
		}
		return differences<tolerance;
	}

}
