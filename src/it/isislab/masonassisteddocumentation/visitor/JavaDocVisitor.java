package it.isislab.masonassisteddocumentation.visitor;

import it.isislab.masonassisteddocumentation.mason.analizer.GlobalUtility;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;

/**
 * This class visits a JavaDoc node of a compilation unit
 * and delete it if contains default MAD message.
 * This to clear code from comment add by MAD.
 * @author Simone
 *
 */
public class JavaDocVisitor extends ASTVisitor {
	private CompilationUnit cu;

	public JavaDocVisitor(CompilationUnit cu) {
		this.cu = cu;
	}
	
	public boolean visit(Javadoc node) {
		if (node.toString().contains(GlobalUtility.COMMENT_SIGNATURE)){
			node.delete();
		}	
		return super.visit(node);
	}
}
