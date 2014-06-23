package it.isislab.masonhelperdocumentation.mason.wizards;

import it.isislab.masonhelperdocumentation.mason.control.ConfigFile;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class A1_ChooseOutput extends WizardPage {
	private Button btnDoxygen, btnPdf, btnTxt;
	/**
	 * Create the wizard.
	 */
	public A1_ChooseOutput() {
		super("wizardPage");
		setTitle("Output");
		setDescription("Choose output format");
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(3, false));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		btnDoxygen = new Button(container, SWT.RADIO);
		btnDoxygen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ConfigFile.setProperty("outputType", "Doxygen");
			}
		});
		btnDoxygen.setText("Doxygen");
		
		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setText("This require Doxygen installation. Output will contain html and latex.");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		btnPdf = new Button(container, SWT.RADIO);
		btnPdf.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ConfigFile.setProperty("outputType", "pdf");
			}
		});
		btnPdf.setText("pdf");
		
		Label lblThisChooiseWill = new Label(container, SWT.NONE);
		lblThisChooiseWill.setText("This choise will generate a pdf document.");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		btnTxt = new Button(container, SWT.RADIO);
		btnTxt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ConfigFile.setProperty("outputType", "txt");
			}
		});
		btnTxt.setText("txt");
		
		String outputType = ConfigFile.getValue("outputType");
		if (outputType.equals("Doxygen"))	btnDoxygen.setSelection(true);
		else if (outputType.equals("pdf"))	btnPdf.setSelection(true);
		else if (outputType.equals("txt"))	btnTxt.setSelection(true);
		else
			btnDoxygen.setSelection(true);
		
		Label lblThisChooseWill = new Label(container, SWT.NONE);
		lblThisChooseWill.setText("This choise will generate a txt document.");
	}
	
	public IWizardPage getNextPage(){ 
		if (btnDoxygen.getSelection())	ConfigFile.setProperty("outputType", "Doxygen");
		if (btnPdf.getSelection())	ConfigFile.setProperty("outputType", "pdf");
		if (btnTxt.getSelection())	ConfigFile.setProperty("outputType", "txt");
		
		B_ProjectInformationPage nextPage = new B_ProjectInformationPage();
		((MASONDocumentationWizard) super.getWizard()).addPage(nextPage);
		return nextPage;
	}
}
