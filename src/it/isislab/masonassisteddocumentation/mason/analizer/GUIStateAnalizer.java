package it.isislab.masonassisteddocumentation.mason.analizer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * Represent analizer for GUIState class.
 * @author Romano Simone 0512101343
 *
 */
public class GUIStateAnalizer implements Analizer{
	private ICompilationUnit GUIStateICompilationUnit;
	private CompilationUnit compilationUnit;
	private AST root;
	private String absolutePath;
	public static String[] gridPortrayal = {"SparseGridPortrayal3D", "parseGrid2DPortrayal3D", "ContinuousPortrayal3D",
								"ObjectGridPortrayal3D", "ValueGridPortrayal3D.", "ValueGridPortrayal3D", "ValueGrid2DPortrayal3D",
								"NetworkPortrayal3D", "FastValueGridPortrayal2D", "FastObjectGridPortrayal2D", "SparseGridPortrayal2D",
								"ContinuousPortrayal2D", "SpatialNetwork2D"};
	private static Logger log = Logger.getLogger("global");
	
	public GUIStateAnalizer(ICompilationUnit gUIStateCU) {
		GUIStateICompilationUnit = gUIStateCU;
		this.absolutePath = GUIStateICompilationUnit.getResource().getRawLocation().toString();
		compilationUnit = GlobalUtility.getCompilationUnit(GUIStateICompilationUnit);
        root = GlobalUtility.getAstFromCompilationUnit(compilationUnit);
        log.info("SimStateAnalizer created");
	}

	/**
	 * Return CompilationUnit of GUIState
	 * @return	CompilationUnit
	 */
	public CompilationUnit getCompilationUnit(){
		return compilationUnit;
	}
	
	/**
	 * Return ICompilationUnit of GUIState class.
	 * @return	ICompilationUnit
	 */
	public ICompilationUnit getICompilationUnit(){
		return GUIStateICompilationUnit;
	}
	
	@Override
	public void rewrite() {
		File sourceFile = new File(GUIStateICompilationUnit.getResource().getRawLocation().toOSString());
		FileOutputStream fooStream;
		try {
			fooStream = new FileOutputStream(sourceFile, false);
			String code = compilationUnit.toString();
			byte[] myBytes = code.getBytes();
			fooStream.write(myBytes);
			fooStream.close();
		} catch (FileNotFoundException e) {
			log.severe("SimState file not found to: " + GUIStateICompilationUnit.getResource().getRawLocation().toOSString() + ".");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * Return true if input parameter is a portrayal;
	 * else false.
	 * @param p	Parameter
	 * @return	Boolean
	 */
	public boolean isPortrayal(Parameter p){
		for (int i=0; i<gridPortrayal.length; i++)
			if (p.getVariableType().equals(gridPortrayal[i]))	return true;
		return false;
	}
	
	/**
	 * Return root of CompilationUnit.
	 * @return	AST
	 */
	public AST getRoot(){
		return root;
	}
	
	/**
	 * Return class name of GUIState
	 * @return
	 */
	public String getClassName(){
		return GUIStateICompilationUnit.getElementName();
	}
}
