package it.isislab.masonhelperdocumentation.mason.wizards;



import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class A_IntroductionPage extends WizardPage {
	private static String introduction = "This wizard will auto-generate ABMs "
							+ "documentation with the standard ODD format. The objectives of \n"
							+ "ODD are to make model descriptions more understandable and "
							+ "complete, thereby making ABMs less \nsubject to criticism for "
							+ "being irreproducible.\n\n"
							+ "Elements of ODD:\n"
							+ "	  1. Purpose\n"
							+ "	  2. Entities, state variables, and scales\n"
							+ "	  3. Process overview and scheduling\n"
							+ "	  4. Design concepts\n"
							+ "	  5. Initialization\n"	
							+ "	  6. Input data\n"
							+ "	  7. Submodels";
	
	/**
	 * Create the wizard.
	 */
	public A_IntroductionPage() {
		super("wizardPage");
		setTitle("ABMs ODD documentation");
		setDescription("Welcome to ABMs ODD wizard");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		
		Label lblIntroduction = new Label(container, SWT.NONE);
		lblIntroduction.setBounds(10, 10, 554, 189);
		lblIntroduction.setText(introduction);
	}
	
	public IWizardPage getNextPage(){ 
		B_ProjectInformationPage nextPage = new B_ProjectInformationPage();
		((MASONDocumentationWizard) super.getWizard()).addPage(nextPage);
		return nextPage;
	}
}
