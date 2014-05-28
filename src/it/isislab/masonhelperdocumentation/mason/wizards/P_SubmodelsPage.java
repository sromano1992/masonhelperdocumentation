package it.isislab.masonhelperdocumentation.mason.wizards;

import it.isislab.masonhelperdocumentation.ODD.ODD;
import it.isislab.masonhelperdocumentation.ODD.Submodel;
import it.isislab.masonhelperdocumentation.ODD.Submodel_s;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

/**
 * This page collects informations about submodels.
 * @author Romano Simone 0512101343
 *
 */
public class P_SubmodelsPage extends WizardPage {
	private String pageDescription = "Here informations about submodels";
	
	public P_SubmodelsPage() {
		super("wizardPage");
		setTitle("7/7 - Submodels");
		setDescription(pageDescription);
	}


	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setSize(564, 272);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		
		Submodel_s submodel_s = ODD.getSubmodel_s();
		for (Submodel submodel : submodel_s.getSubmodel_s()){
			
			Label lblSubmodelName = new Label(composite, SWT.NONE);
			lblSubmodelName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblSubmodelName.setText(submodel.getName());
			
			Text text = new Text(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
			text.setEditable(false);
			GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
			gd_text.heightHint = 81;
			text.setLayoutData(gd_text);
			text.setText(submodel.getDescription());
		}
		
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	public IWizardPage getNextPage(){ 
		Q_EndWizard nextPage = new Q_EndWizard();
		((MASONDocumentationWizard) super.getWizard()).addPage(nextPage);
		return nextPage;
	}
	
}
