package it.isislab.masonassisteddocumentation.mason.wizards;


import it.isislab.masonassisteddocumentation.ODD.ODD;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * This page collect informations about simulation's initial values.
 * @author Romano Simone 0512101343
 *
 */
public class N_InitializationPage extends WizardPage {
	private String pageDescription = "What is the initial state of the model world, i.e., at time t = 0 of a simulation run?";
	private Text textAutoInitialization;
	private Text textUserInitialization;

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
	
		String autoInitialization = clearInitialization();
		textAutoInitialization.setText(autoInitialization);
		if (ODD.getInitialization() != null)
			if (ODD.getInitialization().getUserInitialization() != null)
				textUserInitialization.setText(ODD.getInitialization().getUserInitialization());
	}

	private String clearInitialization() {
		String autoInitialization = ODD.getInitialization().getAutoInitialization();
		autoInitialization = autoInitialization.replace("<br>", "");
		autoInitialization = autoInitialization.replace("</h1>", "");
		autoInitialization = autoInitialization.replace("<h1>", "");
		autoInitialization = autoInitialization.replace("<h2>", "");
		autoInitialization = autoInitialization.replace("</h2>", "");
		return autoInitialization;
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
