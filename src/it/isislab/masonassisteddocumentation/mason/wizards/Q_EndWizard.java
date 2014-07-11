package it.isislab.masonassisteddocumentation.mason.wizards;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import it.isislab.masonassisteddocumentation.ODD.ODD;
import it.isislab.masonassisteddocumentation.ODD.ODDInformationAsString;
import it.isislab.masonassisteddocumentation.mason.analizer.GlobalUtility;
import it.isislab.masonassisteddocumentation.mason.analizer.ProjectAnalizer;
import it.isislab.masonassisteddocumentation.mason.control.ConfigFile;
import it.isislab.masonassisteddocumentation.mason.control.PdfRtfGenerator;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;

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
				generateDocumentation();
			}

			private void generateDocumentation() {
				progressBar.setSelection(0);
				String outputType = ConfigFile.getValue("typeOutput");
				if (outputType.equals("Doxygen")){
					GlobalUtility.rewriteAll();
					doxygenRun();	
				}
				if (outputType.equals("pdf")){
					String pdfMessage = new PdfRtfGenerator().createPdf(ConfigFile.getValue("output") + File.separator + GlobalUtility.getProjectAnalizer().getProjectName() + ".pdf");
					String rtfMessage = new PdfRtfGenerator().createRtf(ConfigFile.getValue("output") + File.separator + GlobalUtility.getProjectAnalizer().getProjectName() + ".rtf");
					if (pdfMessage.equals("done") && rtfMessage.equals("done")){
						progressBar.setVisible(true);
						progressBar.setSelection(100);
						showOutput();
					}
					else{
						if (!pdfMessage.equals("done"))
							JOptionPane.showMessageDialog(null, pdfMessage);
						if (!rtfMessage.equals("done"))
							JOptionPane.showMessageDialog(null, rtfMessage);
					}
				}
				if (outputType.equals("txt")){
					String filename = ConfigFile.getValue("output");
					filename += File.separator + GlobalUtility.getProjectAnalizer().getProjectName() + ".txt";
					File txtFile = new File(filename);
					if (!txtFile.exists())
						try {
							txtFile.createNewFile();							
						} catch (IOException e) {
							log.severe("Error creating .txt file: " + e.getMessage());
							e.printStackTrace();
						}
					FileOutputStream fout;
					try {
						fout = new FileOutputStream(txtFile);
						fout.write(ODDInformationAsString.ODDToString().getBytes(), 0, ODDInformationAsString.ODDToString().length());
						progressBar.setVisible(true);
						progressBar.setSelection(100);
						showOutput();
					} catch (FileNotFoundException e) {
						log.severe("File not found: " + e.getMessage());
						e.printStackTrace();
					} catch (IOException e) {
						log.severe("IOException " + e.getMessage());
						e.printStackTrace();
					}
					
				}	
				
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
				GlobalUtility.removeMADComment();
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
		showOutput();
	}

	private void showOutput() {
		if (btnShowOutput.getSelection()){
			String outputPath = ConfigFile.getValue("output") + File.separator;
			try {
				Desktop.getDesktop().open(new File(outputPath));
			} catch (IOException e) {
				log.severe("Failure opening output directory: " + outputPath); 
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
