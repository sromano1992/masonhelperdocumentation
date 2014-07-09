package it.isislab.masonassisteddocumentation.mason.wizards;

import it.isislab.masonassisteddocumentation.ODD.Entitie_s;
import it.isislab.masonassisteddocumentation.ODD.Entity;
import it.isislab.masonassisteddocumentation.ODD.ODD;
import it.isislab.masonassisteddocumentation.mason.analizer.AgentAnalizer;
import it.isislab.masonassisteddocumentation.mason.analizer.GlobalUtility;
import it.isislab.masonassisteddocumentation.mason.analizer.ProjectAnalizer;
import it.isislab.masonassisteddocumentation.mason.analizer.SimStateAnalizer;
import it.isislab.masonassisteddocumentation.mason.wizards.MASONDocumentationWizard;

import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;


/**
 * 
 * @author Romano Simone 0512101343
 * This page show agent description.
 */
public class D_AgentDescriptionPage extends WizardPage{
	
	private static Logger log = Logger.getLogger("global");
	private AgentAnalizer agentAnalizer;
	private Text textAgentDescription;
	private static String pageDescription = "What kinds of entities are in the model?\n"
										  + "By what state variables, or attributes, are these entities characterized?\n"
										  + "What are the temporal and spatial resolutions and extents of the model?";
	
	/**
	 * Constructor for AgentDescriptionPage.
	 * @param javaProject
	 */
	public D_AgentDescriptionPage() {
		super("wizardPage");
		agentAnalizer = GlobalUtility.getAgentAnalizer();
		setTitle("2/7 - Entities, state variables, and scales\n" + agentAnalizer.getClassName());
		setDescription(pageDescription);
	}
	
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(1,true));
		
		Label lblAgentDefinition = new Label(container, SWT.NONE);
		GridData gd_lblAgentDefinition = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_lblAgentDefinition.widthHint = 567;
		lblAgentDefinition.setLayoutData(gd_lblAgentDefinition);
		lblAgentDefinition.setText("Description for entities: " + agentAnalizer.getClassName());

		textAgentDescription = new Text(container, SWT.BORDER | SWT.MULTI);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text.heightHint = 93;
		textAgentDescription.setLayoutData(gd_text);

		
		getOldInformation();
		
	}

	
	private void getOldInformation() {
		Entity agent = ODD.getEntity(agentAnalizer.getClassName());
		if (agent != null)
			textAgentDescription.setText(agent.getDescription());
	}
		
	@Override
	public boolean canFlipToNextPage() {
		return true;
	}

	public IWizardPage getNextPage(){ 
		//store new autogenerated information
		agentAnalizer.setClassDescription(textAgentDescription.getText());
		/**
		 * Add this agent to Entities list
		 * (entities represent the Entites of ODD protocol - section 2)
		 * @param agentAnalizer
		 */
		ODD.addEntity(new Entity(agentAnalizer.getClassName(), textAgentDescription.getText()));	
		if (agentAnalizer.getPositionsParameter().size() != 0){
			E_AgentPositionPage nextPage = new E_AgentPositionPage();
			((MASONDocumentationWizard) super.getWizard()).addPage(nextPage); 
			return nextPage; 
		}
		else if (agentAnalizer.getNotVisitedParameter_s().size() > 0){
			F_AgentVariablesPage nextPage = new F_AgentVariablesPage();
			((MASONDocumentationWizard) super.getWizard()).addPage(nextPage);
			return nextPage; 
		}
		else{
			G_GridsCellPage nextPage = new G_GridsCellPage();
			((MASONDocumentationWizard) super.getWizard()).addPage(nextPage);
			return nextPage; 
		}
	}

}