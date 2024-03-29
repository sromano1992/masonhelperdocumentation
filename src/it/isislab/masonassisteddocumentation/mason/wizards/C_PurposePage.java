package it.isislab.masonassisteddocumentation.mason.wizards;

import it.isislab.masonassisteddocumentation.ODD.ODD;
import it.isislab.masonassisteddocumentation.mason.analizer.GlobalUtility;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.ScrolledComposite;

public class C_PurposePage extends WizardPage {
	private static String pageDescription = "What is the purpose of the model?";
	private Text text;

	public C_PurposePage() {
		super("wizardPage");
		setTitle("1/7 - Purpose");
		setDescription(pageDescription);
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(1, false));		
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		text = new Text(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text.setLocation(0, 0);
		text.setSize(562, 269);
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
