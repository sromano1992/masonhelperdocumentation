package it.isislab.masonhelperdocumentation.mason.wizards;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import it.isislab.masonhelperdocumentation.ODD.ODD;
import it.isislab.masonhelperdocumentation.analizer.GlobalUtility;
import it.isislab.masonhelperdocumentation.mason.control.ConfigFile;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Q_EndWizard extends WizardPage {
	private Text shelltext;
	private String pageDescription = "Click Generate button to generate documentation.";
	private ProgressBar progressBar;
	private Button btnShowOutput;
	private static Logger log = Logger.getLogger("global");
	
	/**
	 * Create the wizard.
	 */
	public Q_EndWizard() {
		super("wizardPage");
		setTitle("End wizard");
		setDescription(pageDescription);
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		
		shelltext = new Text(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.CENTER | SWT.MULTI);
		shelltext.setText("Show Output");
		shelltext.setEditable(false);
		shelltext.setBounds(10, 77, 554, 171);
		shelltext.setVisible(false);
		
		progressBar = new ProgressBar(container, SWT.NONE);
		progressBar.setBounds(10, 31, 554, 17);
		progressBar.setVisible(false);
		
		Button btnGenerate = new Button(container, SWT.NONE);
		btnGenerate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ODD.serialize();
				GlobalUtility.rewriteAll();
				doxygenRun();
			}
		});
		btnGenerate.setBounds(10, 0, 75, 25);
		btnGenerate.setText("Generate");
		
		btnShowOutput = new Button(container, SWT.CHECK);
		btnShowOutput.setBounds(103, 9, 93, 16);
		btnShowOutput.setText("Show Output");
	}

	private void doxygenRun() {
		int progressValue = 0;
		shelltext.setText("");
		progressBar.setVisible(true);
		shelltext.setVisible(true);
		Process doxygenProcess = GlobalUtility.doxygenRun();
		if (doxygenProcess != null){
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(doxygenProcess.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(doxygenProcess.getErrorStream()));

			// read the output from the command
			String s = null;
			try {
				while ((s = stdInput.readLine()) != null) {
					s = splitWithNewLine(s);
					shelltext.setText(shelltext.getText() + "\n" + s);
					progressBar.setSelection(progressValue);
					if (progressValue <80)
						progressValue++;
				}
				progressBar.setSelection(100);
			} catch (IOException e) {
				e.printStackTrace();
			}
			// read any errors from the attempted command
			try {
				while ((s = stdError.readLine()) != null) {					
					log.severe("doxygenError: " + s);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//show html
		if (btnShowOutput.getSelection()){
			String htmlPath = ConfigFile.getValue("output") + File.separator + "html";
			try {
				Desktop.getDesktop().open(new File(htmlPath));
			} catch (IOException e) {
				log.severe("Failure opening output directory: " + htmlPath); 
				e.printStackTrace();
			}
		}
	}

	private String splitWithNewLine(String s) {
		String toReturn = "";
		while(s.length()>100){
			toReturn = toReturn + s.substring(0, 100) + "\n";
			s = s.substring(100, s.length());
		}
		return toReturn + s + "\n";
	}
}
