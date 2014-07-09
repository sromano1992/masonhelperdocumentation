package it.isislab.masonassisteddocumentation.mason.wizards;


import java.util.logging.Logger;

import it.isislab.masonassisteddocumentation.ODD.DesignConcepts;
import it.isislab.masonassisteddocumentation.ODD.ODD;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * This page contains informations about concept design
 * category of ODD protocol.
 * @author Romano Simone 0512101343
 *
 */
public class M_DesignConceptsPage extends WizardPage {
	private String pageDescription = "Here can insert informations about design concepts for next categories:";
	private Text textUserBasicPrinciples;
	private Text txtBasicPrinciplesHelp;
	private Text txtEmergenceHelp;
	private Text textUserEmergence;
	private Text txtAdaptionHelp;
	private Text textUserAdaption;
	private Text txtObjectivesHelp;
	private Text textUserObjectives;
	private Text txtLearningHelp;
	private Text textUserLearning;
	private Text txtPredictionHelp;
	private Text textUserPrediction;
	private Text txtSensingHelp;
	private Text textUserSensing;
	private Text txtInteractionHelp;
	private Text textUserInteraction;
	private Text txtStochasticityHelp;
	private Text textUserStochasticity;
	private Text txtCollectivesHelp;
	private Text textUserCollectives;
	private Text txtObservationHelp;
	private Text textUserObservation;
	private static Logger log = Logger.getLogger("global");
	
	public M_DesignConceptsPage() {
		super("wizardPage");
		setTitle("4/7 - Design concepts\n");
		setDescription(pageDescription);
	}

	/**
	 * Create contents of the wizard.
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setBounds(0, 0, 574, 282);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		GridLayout gl_composite = new GridLayout();
		gl_composite.numColumns = 2;
		gl_composite.verticalSpacing = 9;
		composite.setLayout(gl_composite);
		
		//Basic principles
		Label lblBasicPrinciples = new Label(composite, SWT.NONE);
		lblBasicPrinciples.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblBasicPrinciples.setText("Basic principles");
		
		txtBasicPrinciplesHelp = new Text(composite, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		txtBasicPrinciplesHelp.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtBasicPrinciplesHelp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txtBasicPrinciplesHelp.setText("Which general concepts, theories, hypotheses, or modeling approaches\r\n are underlying the model\u2019s design?");
		txtBasicPrinciplesHelp.setEditable(false);
		GridData gd_txtBasicPrinciplesHelp = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtBasicPrinciplesHelp.heightHint = 37;
		txtBasicPrinciplesHelp.setLayoutData(gd_txtBasicPrinciplesHelp);
		new Label(composite, SWT.NONE);
		
		/*textAutoBasicPrinciples = new Text(composite, SWT.BORDER | SWT.MULTI);
		textAutoBasicPrinciples.setEditable(false);
		GridData gd_textAutoBasicPrinciples = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textAutoBasicPrinciples.heightHint = 31;
		textAutoBasicPrinciples.setLayoutData(gd_textAutoBasicPrinciples);
		new Label(composite, SWT.NONE);*/
		
		textUserBasicPrinciples = new Text(composite, SWT.BORDER | SWT.MULTI);
		GridData gd_textUserBasicPrinciples = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textUserBasicPrinciples.heightHint = 55;
		textUserBasicPrinciples.setLayoutData(gd_textUserBasicPrinciples);
		if (ODD.getDesignConcepts().getUserBasicPrinciples() != null)
			textUserBasicPrinciples.setText(ODD.getDesignConcepts().getUserBasicPrinciples());
		//end basic principles
		
		//Emergence
		Label lblEmergence = new Label(composite, SWT.NONE);
		lblEmergence.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEmergence.setText("Emergence");

		txtEmergenceHelp = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		txtEmergenceHelp.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtEmergenceHelp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txtEmergenceHelp.setText("What key results or outputs of the model are modeled as emerging\nfrom the adaptive traits, or behaviors, of individuals?");
		txtEmergenceHelp.setEditable(false);
		GridData gd_txtEmergenceHelp = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtEmergenceHelp.heightHint = 37;
		txtEmergenceHelp.setLayoutData(gd_txtEmergenceHelp);
		new Label(composite, SWT.NONE);

		/*textAutoEmergence = new Text(composite, SWT.BORDER | SWT.MULTI);
		textAutoEmergence.setEditable(false);
		GridData gd_textAutoEmergence = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textAutoEmergence.heightHint = 31;
		textAutoEmergence.setLayoutData(gd_textAutoEmergence);
		new Label(composite, SWT.NONE);*/

		textUserEmergence = new Text(composite, SWT.BORDER | SWT.MULTI);
		GridData gd_textUserEmergence = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textUserEmergence.heightHint = 55;
		textUserEmergence.setLayoutData(gd_textUserEmergence);
		if (ODD.getDesignConcepts().getUserEmergence() != null)
			textUserEmergence.setText(ODD.getDesignConcepts().getUserEmergence());
		//end Emergence
		
		//Adaption
		Label lblAdaption = new Label(composite, SWT.NONE);
		lblAdaption.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAdaption.setText("Adaption");

		txtAdaptionHelp = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		txtAdaptionHelp.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtAdaptionHelp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txtAdaptionHelp.setText("What adaptive traits do the individuals have? What rules do\nthey have for making decisions or changing behavior in response to\nchanges in themselves or their environment");
		txtAdaptionHelp.setEditable(false);
		GridData gd_txtAdaptionHelp = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtAdaptionHelp.heightHint = 37;
		txtAdaptionHelp.setLayoutData(gd_txtAdaptionHelp);
		new Label(composite, SWT.NONE);

		/*textAutoAdaption = new Text(composite, SWT.BORDER | SWT.MULTI);
		textAutoAdaption.setEditable(false);
		GridData gd_textAutoAdaption = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textAutoAdaption.heightHint = 31;
		textAutoAdaption.setLayoutData(gd_textAutoAdaption);
		new Label(composite, SWT.NONE);
*/
		textUserAdaption = new Text(composite, SWT.BORDER | SWT.MULTI);
		GridData gd_textUserAdaption = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textUserAdaption.heightHint = 55;
		textUserAdaption.setLayoutData(gd_textUserAdaption);
		if (ODD.getDesignConcepts().getUserAdaption() != null)
			textUserAdaption.setText(ODD.getDesignConcepts().getUserAdaption());
		//endAdaption
		
		//Objectives
		Label lblObjectives = new Label(composite, SWT.NONE);
		lblObjectives.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblObjectives.setText("Objectives");

		txtObjectivesHelp = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		txtObjectivesHelp.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtObjectivesHelp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txtObjectivesHelp.setText("If adaptive traits explicitly act to increase some measure of the\nindividual’s success at meeting some objective, what exactly is\nthat objective and how is it measured?");
		txtObjectivesHelp.setEditable(false);
		GridData gd_txtObjectivesHelp = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtObjectivesHelp.heightHint = 37;
		txtObjectivesHelp.setLayoutData(gd_txtObjectivesHelp);
		new Label(composite, SWT.NONE);

		/*textAutoObjectives = new Text(composite, SWT.BORDER | SWT.MULTI);
		textAutoObjectives.setEditable(false);
		GridData gd_textAutoObjectives = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textAutoObjectives.heightHint = 31;
		textAutoObjectives.setLayoutData(gd_textAutoObjectives);
		new Label(composite, SWT.NONE);*/

		textUserObjectives = new Text(composite, SWT.BORDER | SWT.MULTI);
		GridData gd_textUserObjectives = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textUserObjectives.heightHint = 55;
		textUserObjectives.setLayoutData(gd_textUserObjectives);
		if (ODD.getDesignConcepts().getUserObjectives() != null)
			textUserObjectives.setText(ODD.getDesignConcepts().getUserObjectives());
		//end Objectives
		
		//Learning
		Label lblLearning = new Label(composite, SWT.NONE);
		lblLearning.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLearning.setText("Learning");

		txtLearningHelp = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		txtLearningHelp.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtLearningHelp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txtLearningHelp.setText("Many individuals or agents (but also organizations and institutions\nchange their adaptive traits over time as a consequence of\ntheir experience? If so, how?");
		txtLearningHelp.setEditable(false);
		GridData gd_txtLearningHelp = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtLearningHelp.heightHint = 37;
		txtLearningHelp.setLayoutData(gd_txtLearningHelp);
		new Label(composite, SWT.NONE);

		/*textAutoLearning = new Text(composite, SWT.BORDER | SWT.MULTI);
		textAutoLearning.setEditable(false);;
		GridData gd_textAutoLearning = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textAutoLearning.heightHint = 31;
		textAutoLearning.setLayoutData(gd_textAutoLearning);
		new Label(composite, SWT.NONE);*/

		textUserLearning = new Text(composite, SWT.BORDER | SWT.MULTI);
		GridData gd_textUserLearning = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textUserLearning.heightHint = 55;
		textUserLearning.setLayoutData(gd_textUserLearning);
		if (ODD.getDesignConcepts().getUserLearning() != null)
			textUserLearning.setText(ODD.getDesignConcepts().getUserLearning());
		//end Learning
		
		//Prediction
		Label lblPrediction = new Label(composite, SWT.NONE);
		lblPrediction.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblPrediction.setText("Prediction");

		txtPredictionHelp = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		txtPredictionHelp.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtPredictionHelp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txtPredictionHelp.setText("Prediction is fundamental to successful decision-making; if an\nagent’s adaptive traits or learning procedures are based on estimating\nfuture consequences of decisions,howdo agents predict the\nfuture conditions (either environmental or internal) they will experience?");
		txtPredictionHelp.setEditable(false);
		GridData gd_txtPredictionHelp = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtPredictionHelp.heightHint = 37;
		txtPredictionHelp.setLayoutData(gd_txtPredictionHelp);
		new Label(composite, SWT.NONE);

		/*textAutoPrediction = new Text(composite, SWT.BORDER | SWT.MULTI);
		textAutoPrediction.setEditable(false);
		GridData gd_textAutoPrediction = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textAutoPrediction.heightHint = 31;
		textAutoPrediction.setLayoutData(gd_textAutoPrediction);
		new Label(composite, SWT.NONE);*/

		textUserPrediction = new Text(composite, SWT.BORDER | SWT.MULTI);
		GridData gd_textUserPrediction = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textUserPrediction.heightHint = 55;
		textUserPrediction.setLayoutData(gd_textUserPrediction);
		if (ODD.getDesignConcepts().getUserPrediction() != null)
			textUserPrediction.setText(ODD.getDesignConcepts().getUserPrediction());
		//end Prediction
		
		//Sensing
		Label lblSensing = new Label(composite, SWT.NONE);
		lblSensing.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblSensing.setText("Sensing");

		txtSensingHelp = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		txtSensingHelp.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtSensingHelp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txtSensingHelp.setText("What internal and environmental state variables are individuals\nassumed to sense and consider in their decisions?");
		txtSensingHelp.setEditable(false);
		GridData gd_txtSensingHelp = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSensingHelp.heightHint = 37;
		txtSensingHelp.setLayoutData(gd_txtSensingHelp);
		new Label(composite, SWT.NONE);

		/*textAutoSensing = new Text(composite, SWT.BORDER | SWT.MULTI);
		textAutoSensing.setEditable(false);
		GridData gd_textAutoSensing = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textAutoSensing.heightHint = 31;
		textAutoSensing.setLayoutData(gd_textAutoSensing);
		new Label(composite, SWT.NONE);*/

		textUserSensing = new Text(composite, SWT.BORDER | SWT.MULTI);
		GridData gd_textUserSensing = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textUserSensing.heightHint = 55;
		textUserSensing.setLayoutData(gd_textUserSensing);
		if (ODD.getDesignConcepts().getUserSensing() != null)
			textUserSensing.setText(ODD.getDesignConcepts().getUserSensing());
		//end Sensing
		
		//Interaction
		Label lblInteraction = new Label(composite, SWT.NONE);
		lblInteraction.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInteraction.setText("Interaction");

		txtInteractionHelp = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		txtInteractionHelp.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtInteractionHelp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txtInteractionHelp.setText("What kinds of interactions among agents are assumed? Are\nthere direct interactions in which individuals encounter and affect\nothers, or are interactions indirect, e.g., via competition for amediating\nresource?");
		txtInteractionHelp.setEditable(false);
		GridData gd_txtInteractionHelp = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtInteractionHelp.heightHint = 37;
		txtInteractionHelp.setLayoutData(gd_txtInteractionHelp);
		new Label(composite, SWT.NONE);

		/*textAutoInteraction = new Text(composite, SWT.BORDER | SWT.MULTI);
		textAutoInteraction.setEditable(false);
		GridData gd_textAutoInteraction = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textAutoInteraction.heightHint = 31;
		textAutoInteraction.setLayoutData(gd_textAutoInteraction);
		new Label(composite, SWT.NONE);*/

		textUserInteraction = new Text(composite, SWT.BORDER | SWT.MULTI);
		GridData gd_textUserInteraction = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textUserInteraction.heightHint = 55;
		textUserInteraction.setLayoutData(gd_textUserInteraction);
		if (ODD.getDesignConcepts().getUserInteraction() != null)
			textUserInteraction.setText(ODD.getDesignConcepts().getUserInteraction());
		//end Interaction
		
		//Stochasticity
		Label lblStochasticity = new Label(composite, SWT.NONE);
		lblStochasticity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStochasticity.setText("Stochasticity");

		txtStochasticityHelp = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		txtStochasticityHelp.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtStochasticityHelp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txtStochasticityHelp.setText("What processes are modeled by assuming they are random or\npartly random?");
		txtStochasticityHelp.setEditable(false);
		GridData gd_txtStochasticityHelp = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtStochasticityHelp.heightHint = 37;
		txtStochasticityHelp.setLayoutData(gd_txtStochasticityHelp);
		new Label(composite, SWT.NONE);

		/*textAutoStochasticity = new Text(composite, SWT.BORDER | SWT.MULTI);
		textAutoStochasticity.setEditable(false);
		GridData gd_textAutoStochasticity = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textAutoStochasticity.heightHint = 31;
		textAutoStochasticity.setLayoutData(gd_textAutoStochasticity);
		new Label(composite, SWT.NONE);*/

		textUserStochasticity = new Text(composite, SWT.BORDER | SWT.MULTI);
		GridData gd_textUserStochasticity = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textUserStochasticity.heightHint = 55;
		textUserStochasticity.setLayoutData(gd_textUserStochasticity);
		if (ODD.getDesignConcepts().getUserStochasticity() != null)
			textUserStochasticity.setText(ODD.getDesignConcepts().getUserStochasticity());
		//end Stochasticity
		
		//Collectives
		Label lblCollectives = new Label(composite, SWT.NONE);
		lblCollectives.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblCollectives.setText("Collectives");

		txtCollectivesHelp = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		txtCollectivesHelp.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtCollectivesHelp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txtCollectivesHelp.setText("Do the individuals form or belong to aggregations that affect,\nand are affected by, the individuals?");
		txtCollectivesHelp.setEditable(false);
		GridData gd_txtCollectivesHelp = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtCollectivesHelp.heightHint = 37;
		txtCollectivesHelp.setLayoutData(gd_txtCollectivesHelp);
		new Label(composite, SWT.NONE);

		/*textAutoCollectives = new Text(composite, SWT.BORDER | SWT.MULTI);
		textAutoCollectives.setEditable(false);
		GridData gd_textAutoCollectives = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textAutoCollectives.heightHint = 31;
		textAutoCollectives.setLayoutData(gd_textAutoCollectives);
		new Label(composite, SWT.NONE);*/

		textUserCollectives = new Text(composite, SWT.BORDER | SWT.MULTI);
		GridData gd_textUserCollectives = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textUserCollectives.heightHint = 55;
		textUserCollectives.setLayoutData(gd_textUserCollectives);
		if (ODD.getDesignConcepts().getUserCollectives() != null)
			textUserCollectives.setText(ODD.getDesignConcepts().getUserCollectives());
		//end Collectives
		
		//Observation
		Label lblObservation = new Label(composite, SWT.NONE);
		lblObservation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblObservation.setText("Observation");

		txtObservationHelp = new Text(composite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		txtObservationHelp.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		txtObservationHelp.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.ITALIC));
		txtObservationHelp.setText("What data are collected from the ABM for testing, understanding,\nand analyzing it, and how and when are they collected?");
		txtObservationHelp.setEditable(false);
		GridData gd_txtObservationHelp = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtObservationHelp.heightHint = 37;
		txtObservationHelp.setLayoutData(gd_txtObservationHelp);
		new Label(composite, SWT.NONE);

		/*textAutoObservation = new Text(composite, SWT.BORDER | SWT.MULTI);
		textAutoObservation.setEditable(false);
		GridData gd_textAutoObservation = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textAutoObservation.heightHint = 31;
		textAutoObservation.setLayoutData(gd_textAutoObservation);
		new Label(composite, SWT.NONE);*/

		textUserObservation = new Text(composite, SWT.BORDER | SWT.MULTI);
		GridData gd_textUserObservation = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textUserObservation.heightHint = 55;
		textUserObservation.setLayoutData(gd_textUserObservation);
		if (ODD.getDesignConcepts().getUserObservation() != null)
			textUserObservation.setText(ODD.getDesignConcepts().getUserObservation());
		//end Observation
		
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	public IWizardPage getNextPage(){ 
		storeDesignConcepts();
		N_InitializationPage nextPage = new N_InitializationPage();
		((MASONDocumentationWizard) super.getWizard()).addPage(nextPage);
		return nextPage;
	}

	private void storeDesignConcepts() {
		DesignConcepts designConcepts = ODD.getDesignConcepts();
		designConcepts.setUserBasicPrinciples(textUserBasicPrinciples.getText());
		designConcepts.setUserEmergence(textUserEmergence.getText());
		designConcepts.setUserAdaption(textUserAdaption.getText());
		designConcepts.setUserObjectives(textUserObjectives.getText());
		designConcepts.setUserLearning(textUserLearning.getText());
		designConcepts.setUserSensing(textUserSensing.getText());
		designConcepts.setUserInteraction(textUserInteraction.getText());
		designConcepts.setUserStochasticity(textUserStochasticity.getText());
		designConcepts.setUserCollectives(textUserCollectives.getText());
		designConcepts.setUserObservation(textUserObservation.getText());
		log.info("Design concepts stored");
	}
}
