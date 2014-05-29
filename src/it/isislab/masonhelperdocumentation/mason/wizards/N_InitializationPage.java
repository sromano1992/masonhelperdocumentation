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

/**
 * This page collect informations about simulation's initial values.
 * @author Romano Simone 0512101343
 *
 */
public class N_InitializationPage extends WizardPage {
	private String pageDescription = "What is the initial state of the model world, i.e., at time t = 0 of a simulation run?";
	private Text textAutoInitialization;
	private Text textUserInitialization;
	private Logger log = Logger.getLogger("global");

	public N_InitializationPage() {
		super("wizardPage");
		setTitle("5/7 -Initialization\n");
		setDescription(pageDescription);
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		
		textAutoInitialization = new Text(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		textAutoInitialization.setLocation(0, 0);
		textAutoInitialization.setSize(564, 162);
		textAutoInitialization.setEditable(false);
		
		textUserInitialization = new Text(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		textUserInitialization.setLocation(0, 164);
		textUserInitialization.setSize(564, 108);
	
		textAutoInitialization.setText(ODD.getInitialization().getAutoInitialization());
		if (ODD.getInitialization() != null)
			if (ODD.getInitialization().getUserInitialization() != null)
				textUserInitialization.setText(ODD.getInitialization().getUserInitialization().replace("<br>", ""));
	}

	public IWizardPage getNextPage(){ 
		//there is no necessity to store automatic initialization becouse
		//they are generate at runtime from Variable intial value informations.
		ODD.getInitialization().setUserInitialization(textUserInitialization.getText());
		O_InputDataPage nextPage = new O_InputDataPage();
		((MASONDocumentationWizard) super.getWizard()).addPage(nextPage);
		return nextPage;
	}
}
