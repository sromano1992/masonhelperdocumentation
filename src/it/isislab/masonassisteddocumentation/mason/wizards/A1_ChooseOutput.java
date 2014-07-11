package it.isislab.masonassisteddocumentation.mason.wizards;

import it.isislab.masonassisteddocumentation.mason.control.ConfigFile;

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
	private Button btnDoxygen, btnPdf, btnTxt, btnAll;
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
				ConfigFile.setProperty("typeOutput", "Doxygen");
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
				ConfigFile.setProperty("typeOutput", "pdf");
			}
		});
		btnPdf.setText("pdf-rtf");
		
		Label lblThisChooiseWill = new Label(container, SWT.NONE);
		lblThisChooiseWill.setText("This choise will generate pdf and rtf documents.");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		btnTxt = new Button(container, SWT.RADIO);
		btnTxt.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ConfigFile.setProperty("typeOutput", "txt");
			}
		});
		btnTxt.setText("txt");
		
		Label lblThisChooseWill = new Label(container, SWT.NONE);
		lblThisChooseWill.setText("This choise will generate a txt document.");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		btnAll = new Button(container, SWT.RADIO);
		btnAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ConfigFile.setProperty("typeOutput", "all");
			}
		});
		btnAll.setText("All");
		
		setSelected();
		
		Label labelAll = new Label(container, SWT.NONE);
		labelAll.setText("This choise will generate html-latex(with Doxygen), pdf-rtf and txt.");
	}

	private void setSelected() {
		String typeOutput = ConfigFile.getValue("typeOutput");
		if (typeOutput.equals("Doxygen"))	btnDoxygen.setSelection(true);
		else if (typeOutput.equals("pdf"))	btnPdf.setSelection(true);
		else if (typeOutput.equals("txt"))	btnTxt.setSelection(true);
		else if (typeOutput.equals("all"))	btnAll.setSelection(true);
		else
			btnDoxygen.setSelection(true);
	}
	
	public IWizardPage getNextPage(){ 
		if (btnDoxygen.getSelection())	ConfigFile.setProperty("typeOutput", "Doxygen");
		if (btnPdf.getSelection())	ConfigFile.setProperty("typeOutput", "pdf");
		if (btnTxt.getSelection())	ConfigFile.setProperty("typeOutput", "txt");
		if (btnAll.getSelection())	ConfigFile.setProperty("typeOutput", "all");
		
		B_ProjectInformationPage nextPage = new B_ProjectInformationPage();
		((MASONDocumentationWizard) super.getWizard()).addPage(nextPage);
		return nextPage;
	}
}
