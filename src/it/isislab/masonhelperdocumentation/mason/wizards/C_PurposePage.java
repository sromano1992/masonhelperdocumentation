package it.isislab.masonhelperdocumentation.mason.wizards;

import it.isislab.masonhelperdocumentation.ODD.ODD;
import it.isislab.masonhelperdocumentation.ODD.Purpose;
import it.isislab.masonhelperdocumentation.analizer.GlobalUtility;
import it.isislab.masonhelperdocumentation.analizer.SimStateAnalizer;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.ui.actions.GlobalBuildAction;

public class C_PurposePage extends WizardPage {
	private static String pageDescription = "What is the purpose of the model?";
	private Text text;
	private SimStateAnalizer simState;

	public C_PurposePage() {
		super("wizardPage");
		setTitle("1/7 - Purpose");
		setDescription(pageDescription);
		simState = GlobalUtility.getSimStateAnalizer();
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(1, false));		
		text = new Text(container, SWT.BORDER | SWT.MULTI);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text.heightHint = 169;
		text.setLayoutData(gd_text);
		getOldInformation();
	}

	/**
	 * This method get old model description (if exist).
	 */
	private void getOldInformation() {
		text.setText(ODD.getPurpose().getModelPurpose());
	}
	
	public IWizardPage getNextPage(){ 
		//******store information******//
		ODD.getPurpose().setModelPurpose(text.getText());
		//****selecting right page****//
		if (GlobalUtility.getAgentAnalizer() != null){
			D_AgentDescriptionPage nextPage = new D_AgentDescriptionPage();
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
