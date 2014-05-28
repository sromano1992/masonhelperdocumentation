package it.isislab.masonhelperdocumentation.mason.wizards;

import it.isislab.masonhelperdocumentation.analizer.GlobalUtility;
import it.isislab.masonhelperdocumentation.analizer.ProjectAnalizer;
import it.isislab.masonhelperdocumentation.mason.control.ConfigFile;
import it.isislab.masonhelperdocumentation.mason.wizards.MASONDocumentationWizard;

import java.util.logging.Logger;

import javax.swing.JFileChooser;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * 
 * @author Romano Simone 0512101343
 * This page show some settings to begin wizard.
 */
public class B_ProjectInformationPage extends WizardPage{
	private Text containerText;	
	private static Logger log = Logger.getLogger("global");
	private Label lblDoxygenPath;
	private Button buttonBrowse;
	private Label labelSimState;
	private Label labelAgent;
	private Label labelGUIState;
	private ProjectAnalizer projectAnalizer;
	private Label labelProjectFound;
	private Label lblImagePath;
	private Text textImgPath;
	private Button btnImgBrowse;
	private Text text;
	private Button btnOutputBrowse;
	private Label labelGraphvizPath;
	private Text textGraphvizPath;
	private Button buttonGraphvizBrowser;
	

	/**
	 * Constructor for Page1.
	 * 
	 * @param javaProject
	 */
	public B_ProjectInformationPage() {
		super("wizardPage");
		setTitle("MASON Helper documentation");
		setDescription("This wizard will extract a documentation for Simulation model using Doxygen.");
		projectAnalizer = GlobalUtility.getProjectAnalizer();
	}

	private void setDoxygenLabelPathFromConfig() {
		this.containerText.setText(ConfigFile.getValue("doxygenPath"));
		log.info("doxygenPath text update\n" + this.containerText.getText());
	}

	/**
	 * Check if Doxygen path is set
	 * @return	true if Doxygen path is set
	 */
	public boolean isDoxygenPathSet(){
		return (ConfigFile.getValue("doxygenPath")!=null);
	}
	
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		lblDoxygenPath = new Label(container, SWT.NULL);
		lblDoxygenPath.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDoxygenPath.setText("Doxygen path:");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		containerText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		buttonBrowse = new Button(container, SWT.PUSH);
		buttonBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser chooser = new JFileChooser();
	            chooser.setCurrentDirectory(new java.io.File("."));
	            chooser.setDialogTitle("Browse the folder to process");
	            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            chooser.setAcceptAllFileFilterUsed(false);
	            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {	                
                	ConfigFile.setProperty("doxygenPath", chooser.getSelectedFile().getAbsolutePath());
			        log.info("Config file updated: " + chooser.getCurrentDirectory().getPath());
					setDoxygenLabelPathFromConfig();
					canFlipToNextPage();
	            }
			}
		});
		buttonBrowse.setText("Browse...");
		
		labelGraphvizPath = new Label(container, SWT.NONE);
		labelGraphvizPath.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelGraphvizPath.setText("Graphviz path:");
		
		textGraphvizPath = new Text(container, SWT.BORDER);
		textGraphvizPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		buttonGraphvizBrowser = new Button(container, SWT.NONE);
		buttonGraphvizBrowser.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser chooser = new JFileChooser();
	            chooser.setCurrentDirectory(new java.io.File("."));
	            chooser.setDialogTitle("Browse the folder to process");
	            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            chooser.setAcceptAllFileFilterUsed(false);
	            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {	                
                	ConfigFile.setProperty("graphvizPath", chooser.getSelectedFile().getAbsolutePath());
			        log.info("Config file updated: " + chooser.getCurrentDirectory().getPath());
					textGraphvizPath.setText(ConfigFile.getValue("graphvizPath"));
					canFlipToNextPage();
	            }
			}
		});
		buttonGraphvizBrowser.setText("Browse...");
		textGraphvizPath.setText(ConfigFile.getValue("graphvizPath"));;
		
		lblImagePath = new Label(container, SWT.NONE);
		lblImagePath.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblImagePath.setText("Image path:");
		
		textImgPath = new Text(container, SWT.BORDER);
		textImgPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnImgBrowse = new Button(container, SWT.NONE);
		btnImgBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser chooser = new JFileChooser();
	            chooser.setCurrentDirectory(new java.io.File("."));
	            chooser.setDialogTitle("Browse the folder to process");
	            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            chooser.setAcceptAllFileFilterUsed(false);
	            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {	                
                	ConfigFile.setProperty("imgPath", chooser.getSelectedFile().getAbsolutePath());
			        log.info("Config file updated: " + chooser.getCurrentDirectory().getPath());
					textImgPath.setText(ConfigFile.getValue("imgPath"));
	            }
			}
		});
		btnImgBrowse.setText("Browse...");
		textImgPath.setText(ConfigFile.getValue("imgPath"));
		
		Label lblOutputPath = new Label(container, SWT.NONE);
		lblOutputPath.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOutputPath.setText("Output path:");
		
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnOutputBrowse = new Button(container, SWT.NONE);
		btnOutputBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser chooser = new JFileChooser();
	            chooser.setCurrentDirectory(new java.io.File("."));
	            chooser.setDialogTitle("Browse the folder to process");
	            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	            chooser.setAcceptAllFileFilterUsed(false);
	            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {	                            
                	ConfigFile.setProperty("output", chooser.getSelectedFile().getAbsolutePath());
			        log.info("Config file updated: " + chooser.getCurrentDirectory().getPath());
					text.setText(ConfigFile.getValue("output"));
	            }
			}
		});
		btnOutputBrowse.setText("Browse...");
		text.setText(ConfigFile.getValue("output"));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		labelProjectFound = new Label(container, SWT.NONE);
		Label label_Message = new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		setControl(container);
		new Label(container, SWT.NONE);
		
		labelSimState = new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		labelAgent = new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		labelGUIState = new Label(container, SWT.NONE);
		labelGUIState.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		new Label(container, SWT.NONE);
		
		setDoxygenLabelPathFromConfig();
		if (!projectAnalizer.isMasonProject())	label_Message.setText("Please select a project that use MASON library!");
		if (isDoxygenPathSet() && projectAnalizer.isMasonProject())	super.setPageComplete(true);
		else	super.setPageComplete(false);
	
		if (projectAnalizer.isMasonProject()){
			setProjectLabel();
		}
		
	}

	private void setProjectLabel() {
		labelProjectFound.setText("Project Found: " + projectAnalizer.getProjectName());
		if (projectAnalizer.getGuiStateCU()!=null)
			if (projectAnalizer.getGuiStateCU().getElementName()!=null){
				labelGUIState.setText("GUISTATE: " + projectAnalizer.getGuiStateCU().getElementName());
			}
		else
			labelGUIState.setText("GUI not found");	
		labelSimState.setText("SIMSTATE: " + projectAnalizer.getSimStateCU().getElementName());
		if (projectAnalizer.getAgentsNum()!=0){
			for (int i=0; i<projectAnalizer.getAgentsNum(); i++)
				labelAgent.setText(labelAgent.getText() + "AGENT: " + projectAnalizer.getAgentCU(i).getElementName() +"\n");
		}
	}

	@Override
	public boolean canFlipToNextPage() {
		return isDoxygenPathSet() && projectAnalizer.isMasonProject();
	}

	@Override
	public boolean isPageComplete(){
		return true;
	}

	public IWizardPage getNextPage(){ 
		C_PurposePage nextPage = new C_PurposePage();
		((MASONDocumentationWizard) super.getWizard()).addPage(nextPage);
		return nextPage;
	}
}