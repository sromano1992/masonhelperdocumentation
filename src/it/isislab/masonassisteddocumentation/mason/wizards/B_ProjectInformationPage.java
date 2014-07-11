package it.isislab.masonassisteddocumentation.mason.wizards;

import it.isislab.masonassisteddocumentation.mason.analizer.GlobalUtility;
import it.isislab.masonassisteddocumentation.mason.analizer.ProjectAnalizer;
import it.isislab.masonassisteddocumentation.mason.control.ConfigFile;
import it.isislab.masonassisteddocumentation.mason.wizards.MASONDocumentationWizard;

import java.awt.Color;
import java.util.logging.Logger;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.ScrolledComposite;

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
	private ProjectAnalizer projectAnalizer;
	private Label lblImagePath;
	private Text textImgPath;
	private Button btnImgBrowse;
	private Text text;
	private Button btnOutputBrowse;
	private Label labelGraphvizPath;
	private Text textGraphvizPath;
	private Button buttonGraphvizBrowser;
	private ScrolledComposite scrolledComposite;
	private Composite composite;
	private Label lblProjectFound;
	private Label lblGuiState;
	private Label lblSimState;
	private Label lblAgent;
	private Label label_Message;
	private Composite composite_1;
	private Button btnAutoColor;
	private Label lblAutoColor;
	private Label labelUserColor;
	private Button btnUserColor;
	private Label lblViewAutoColor;
	private Label lblViewUserColor;
	

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
		
		Label lblOutputPath = new Label(container, SWT.NONE);
		lblOutputPath.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblOutputPath.setText("Output path:");
		
		text = new Text(container, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.setText(ConfigFile.getValue("output"));
		
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
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		composite_1 = new Composite(container, SWT.NONE);
		composite_1.setLayout(new GridLayout(3, false));
		GridData gd_composite_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_composite_1.widthHint = 401;
		gd_composite_1.heightHint = 60;
		composite_1.setLayoutData(gd_composite_1);
		
		lblAutoColor = new Label(composite_1, SWT.NONE);
		lblAutoColor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAutoColor.setText("Auto-generated info color:");

		
		final RGB autoRGB = new RGB(32, 1, 205);	//default blue
		if (!ConfigFile.getValue("R_Value_auto").equals("") && !ConfigFile.getValue("G_Value_auto").equals("") && !ConfigFile.getValue("B_Value_auto").equals("")){
			autoRGB.red = Integer.parseInt(ConfigFile.getValue("R_Value_auto"));
			autoRGB.green = Integer.parseInt(ConfigFile.getValue("G_Value_auto"));
			autoRGB.blue = Integer.parseInt(ConfigFile.getValue("B_Value_auto"));		
			GlobalUtility.autoOutputColor[0] = autoRGB.red;
			GlobalUtility.autoOutputColor[1] = autoRGB.green;
			GlobalUtility.autoOutputColor[2] = autoRGB.blue;
		}
		btnAutoColor = new Button(composite_1, SWT.NONE);
		btnAutoColor.setText("Choose");
		lblViewAutoColor = new Label(composite_1, SWT.NONE);
		lblViewAutoColor.setForeground(SWTResourceManager.getColor(autoRGB));
		lblViewAutoColor.setText("AAAAA");
		lblViewAutoColor.setBackground(SWTResourceManager.getColor(autoRGB));
		btnAutoColor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Color c = JColorChooser.showDialog(null, "Choose a Color", null);
			      if (c != null){
			    	  autoRGB.red = c.getRed();
			    	  autoRGB.green = c.getGreen();
			    	  autoRGB.blue = c.getBlue();
			  		  lblViewAutoColor.setForeground(SWTResourceManager.getColor(autoRGB));
					  lblViewAutoColor.setBackground(SWTResourceManager.getColor(autoRGB));
					  GlobalUtility.autoOutputColor[0] = c.getRed();
					  GlobalUtility.autoOutputColor[1] = c.getGreen();
					  GlobalUtility.autoOutputColor[2] = c.getBlue();
					  ConfigFile.setProperty("R_Value_auto", c.getRed()+"");
					  ConfigFile.setProperty("G_Value_auto", c.getGreen()+"");
					  ConfigFile.setProperty("B_Value_auto", c.getBlue()+"");
			      }
			    }
			});
		
		
		labelUserColor = new Label(composite_1, SWT.RIGHT);
		labelUserColor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		labelUserColor.setText("User info color:");
		
		final RGB userRGB = new RGB(0, 0, 0);	//default black
		if (!ConfigFile.getValue("R_Value_user").equals("") && !ConfigFile.getValue("G_Value_user").equals("") && !ConfigFile.getValue("B_Value_user").equals("")){
			userRGB.red = Integer.parseInt(ConfigFile.getValue("R_Value_user"));
			userRGB.green = Integer.parseInt(ConfigFile.getValue("G_Value_user"));
			userRGB.blue = Integer.parseInt(ConfigFile.getValue("B_Value_user"));
			GlobalUtility.userOutputColor[0] = userRGB.red;
			GlobalUtility.userOutputColor[1] = userRGB.green;
			GlobalUtility.userOutputColor[2] = userRGB.blue;
		}
		btnUserColor = new Button(composite_1, SWT.NONE);
		lblViewUserColor = new Label(composite_1, SWT.NONE);
		lblViewUserColor.setText("AAAAA");
		lblViewUserColor.setForeground(SWTResourceManager.getColor(userRGB));
		lblViewUserColor.setBackground(SWTResourceManager.getColor(userRGB));
		btnUserColor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Color c = JColorChooser.showDialog(null, "Choose a Color", null);
			      if (c != null){
			    	  userRGB.red = c.getRed();
			    	  userRGB.green = c.getGreen();
			    	  userRGB.blue = c.getBlue();
			    	  lblViewUserColor.setForeground(SWTResourceManager.getColor(userRGB));
			    	  lblViewUserColor.setBackground(SWTResourceManager.getColor(userRGB));
					  GlobalUtility.userOutputColor[0] = c.getRed();
					  GlobalUtility.userOutputColor[1] = c.getGreen();
					  GlobalUtility.userOutputColor[2] = c.getBlue();
					  ConfigFile.setProperty("R_Value_user", c.getRed()+"");
					  ConfigFile.setProperty("G_Value_user", c.getGreen()+"");
					  ConfigFile.setProperty("B_Value_user", c.getBlue()+"");
			      }
			}
		});
		btnUserColor.setText("Choose");
		
		
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		label_Message = new Label(container, SWT.NONE);
		label_Message.setText("");
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		
		scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scrolledComposite.heightHint = 75;
		gd_scrolledComposite.widthHint = 384;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		if (projectAnalizer.isMasonProject()){
			setProjectLabel();
		}
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		new Label(container, SWT.NONE);
		setControl(container);
		
		setDoxygenLabelPathFromConfig();
		if (!projectAnalizer.isMasonProject())	label_Message.setText("Please select a project that use MASON library!");
		if (isDoxygenPathSet() && projectAnalizer.isMasonProject())	super.setPageComplete(true);
		else	super.setPageComplete(false);
		
		//What we can see?
		String typeOutput = ConfigFile.getValue("typeOutput");
		if (typeOutput.equals("pdf")){
			lblDoxygenPath.setVisible(false);
			labelGraphvizPath.setVisible(false);
			lblAutoColor.setVisible(false);
			labelUserColor.setVisible(false);
			containerText.setVisible(false);
			textGraphvizPath.setVisible(false);
			btnAutoColor.setVisible(false);
			btnUserColor.setVisible(false);
			buttonBrowse.setVisible(false);
			buttonGraphvizBrowser.setVisible(false);
			lblViewAutoColor.setVisible(false);
			lblViewUserColor.setVisible(false);
		}
		if (typeOutput.equals("txt")){
			lblDoxygenPath.setVisible(false);
			labelGraphvizPath.setVisible(false);
			lblAutoColor.setVisible(false);
			labelUserColor.setVisible(false);
			lblImagePath.setVisible(false);
			containerText.setVisible(false);
			textGraphvizPath.setVisible(false);
			textImgPath.setVisible(false);
			btnAutoColor.setVisible(false);
			btnUserColor.setVisible(false);
			btnImgBrowse.setVisible(false);
			buttonBrowse.setVisible(false);
			buttonGraphvizBrowser.setVisible(false);
			lblViewAutoColor.setVisible(false);
			lblViewUserColor.setVisible(false);
		}	
	}

	private void setProjectLabel() {
		lblProjectFound = new Label(composite, SWT.NONE);
		lblProjectFound.setText("Project Found: " + projectAnalizer.getProjectName());
		if (projectAnalizer.getGuiStateCU()!=null)
			if (projectAnalizer.getGuiStateCU().getElementName()!=null){
				lblGuiState = new Label(composite, SWT.NONE);
				lblGuiState.setText("GUISTATE: " + projectAnalizer.getGuiStateCU().getElementName());
			}
		else
			lblGuiState.setText("GUI not found");	
		lblSimState = new Label(composite, SWT.NONE);
		if (projectAnalizer.getSimStateCU() != null)
			lblSimState.setText("SIMSTATE: " + projectAnalizer.getSimStateCU().getElementName());
		else{
			JOptionPane.showMessageDialog(null, "Project doesn't contain SimState Class!");
			log.severe("Select project doesn't contain SimState Class!");
			System.exit(1);;
		}			
		if (projectAnalizer.getAgentsNum()!=0){
			lblAgent = new Label(composite, SWT.NONE);
			for (int i=0; i<projectAnalizer.getAgentsNum(); i++)
				lblAgent.setText(lblAgent.getText() + "AGENT: " + projectAnalizer.getAgentCU(i).getElementName() +"\n");
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