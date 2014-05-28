package it.isislab.masonhelperdocumentation.mason.wizards;

import java.util.ArrayList;
import java.util.logging.Logger;

import it.isislab.masonhelperdocumentation.ODD.Entitie_s;
import it.isislab.masonhelperdocumentation.ODD.Entity;
import it.isislab.masonhelperdocumentation.ODD.ODD;
import it.isislab.masonhelperdocumentation.ODD.Variable;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.ScrolledComposite;

public class O_InputDataPage extends WizardPage {
	private String pageDescription = "Does the model use input from external sources such\nas data files or other models to represent processes that change\nover time?";	
	private Text textUserInputData;
	private Logger log = Logger.getLogger("global");

	public O_InputDataPage() {
		super("wizardPage");
		setTitle("6/7 - Input data\n");
		setDescription(pageDescription);
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		
		textUserInputData = new Text(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		textUserInputData.setLocation(0, 10);
		textUserInputData.setSize(564, 142);
		if (ODD.inputData != null)
			textUserInputData.setText(ODD.inputData);
	}

	public IWizardPage getNextPage(){ 
		//there is no necessity to store automatic initialization becouse
		//they are generate at runtime from Variable intial value informations.
		ODD.inputData = textUserInputData.getText();
		P_SubmodelsPage nextPage = new P_SubmodelsPage();
		((MASONDocumentationWizard) super.getWizard()).addPage(nextPage);
		return nextPage;
	}
}
