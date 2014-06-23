package it.isislab.masonhelperdocumentation.mason.wizards;

import it.isislab.masonhelperdocumentation.ODD.Entity;
import it.isislab.masonhelperdocumentation.ODD.ODD;
import it.isislab.masonhelperdocumentation.ODD.Variable;
import it.isislab.masonhelperdocumentation.analizer.AgentAnalizer;
import it.isislab.masonhelperdocumentation.analizer.GlobalUtility;
import it.isislab.masonhelperdocumentation.analizer.Parameter;
import it.isislab.masonhelperdocumentation.analizer.SimStateAnalizer;
import it.isislab.masonhelperdocumentation.mason.wizards.MASONDocumentationWizard;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.ScrolledComposite;

/**
 * 
 * @author Romano Simone 0512101343
 *	This page collects information about agent's position.
 */
public class E_AgentPositionPage extends WizardPage{	
	private static Logger log = Logger.getLogger("global"); //$NON-NLS-1$
	private AgentAnalizer agentAnalizer;
	private Text text;
	private ArrayList<Text> positionUserDescription;
	private ArrayList<Label> positionAutomaticDescription;
	private String pageDescription = "Here information about agent's position."; //$NON-NLS-1$
	
	
	public E_AgentPositionPage() {
		super("wizardPage"); //$NON-NLS-1$
		agentAnalizer = GlobalUtility.getAgentAnalizer();
		setTitle("2/7 - Entities, state variables, and scales\n" + agentAnalizer.getClassName()); //$NON-NLS-1$
		setDescription(pageDescription);
		positionUserDescription = new ArrayList<Text>();
		positionAutomaticDescription = new ArrayList<Label>();
	}

	
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.verticalSpacing = 9;		
		ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scrolledComposite.heightHint = 255;
		gd_scrolledComposite.widthHint = 543;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		GridLayout gl_composite = new GridLayout();
		gl_composite.numColumns = 2;
		gl_composite.verticalSpacing = 9;
		composite.setLayout(gl_composite);
		
		agentAnalizer = GlobalUtility.getAgentAnalizer();		
		ArrayList<Parameter> positions = agentAnalizer.getPositionsParameter();	
		if (positions != null && positions.size()!=0){
			//creating input for each grid in SimState
			for (int i=0; i<positions.size(); i++){			
				Label lblPositionName = new Label(composite, SWT.NONE);
				lblPositionName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				text = new Text(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
				GridData gd_text = new GridData(SWT.LEFT, SWT.LEFT, true, false, 1, 1);
				gd_text.heightHint = 44;
				gd_text.widthHint = 300;
				text.setLayoutData(gd_text);
				new Label(composite, SWT.NONE);			
				Label lblInitializerDetector = new Label(composite, SWT.NONE);
				Label lblEndGrid = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
				GridData gd_lblEndGrid = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
				gd_lblEndGrid.widthHint = 500;
				lblEndGrid.setLayoutData(gd_lblEndGrid);
				lblEndGrid.setText("end grid"); //$NON-NLS-1$
				
				lblPositionName.setText(positions.get(i).getVariableType() + " " + positions.get(i).getVariableName());	 //$NON-NLS-1$
				lblInitializerDetector.setText(Messages.AgentPositionPhrase_1 + positions.get(i).getVariableType() + " "  //$NON-NLS-2$
										+ positions.get(i).getVariableName() +Messages.AgentPositionPhrase_2
										+ Messages.AgentPositionPhrase_3);	
				if (positions.get(i).getInitializer() != "")	 //$NON-NLS-1$
					lblInitializerDetector.setText(lblInitializerDetector.getText() + Messages.InitialValuePhrase_1 + positions.get(i).getInitializer());
				
				//getting old user comment if exist//
				Entity entity = ODD.getEntity(agentAnalizer.getClassName());
				Variable var = entity.getVariable(positions.get(i).getVariableName());
				if (var != null)
					text.setText(var.getUserComment());
				//**********************************//
				
				positionAutomaticDescription.add(lblInitializerDetector);
				positionUserDescription.add(text);
			}
		}
		setControl(container);
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	private String getOldInformation(String varName) {
		Entity e = ODD.getEntity(agentAnalizer.getClassName());
		Variable var = e.getVariable(varName);
		if (var == null) return ""; //$NON-NLS-1$
		return var.getUserComment();
	}


	@Override
	public boolean canFlipToNextPage() {
		return true;
	}
	
	public boolean isComplete(){
		return true;
	}
	
	public IWizardPage getNextPage(){ 
		ArrayList<Parameter> positions = agentAnalizer.getPositionsParameter();
		//store new autogenerated information
		for (int i=0; i<positions.size(); i++){
			String autoGeneratedComment = GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, positionAutomaticDescription.get(i).getText());
			String backupUserComment = positionUserDescription.get(i).getText();
			String userComment = GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, positionUserDescription.get(i).getText());
			String commentValue = "@ingroup entities\n*\n"; //$NON-NLS-1$
			commentValue = commentValue + userComment + "\n"; //$NON-NLS-1$
			commentValue = commentValue + autoGeneratedComment;
			GlobalUtility.setJavadocToField(agentAnalizer.getRoot(), positions.get(i).getField(), commentValue);
			//store new autogenerated/user information in model description
			ODD.addVariableToEntity(agentAnalizer.getClassName(), new Variable(positions.get(i).getVariableName(), backupUserComment, positionAutomaticDescription.get(i).getText(), positions.get(i).getInitializer()));
			//add this parameter to 'visitedParameters' to exclude them from next wizardPage
			agentAnalizer.getVisitedParameters().add(positions.get(i));
		}
		log.info("Comment added to positions variables and to ODD object..."); 
		F_AgentVariablesPage nextPage = new F_AgentVariablesPage();
		((MASONDocumentationWizard) super.getWizard()).addPage(nextPage);
		return nextPage; 
	}

}