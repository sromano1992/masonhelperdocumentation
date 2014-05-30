package it.isislab.masonhelperdocumentation.mason.wizards;

import it.isislab.masonhelperdocumentation.ODD.ODD;
import it.isislab.masonhelperdocumentation.ODD.ProcessOverviewElement;
import it.isislab.masonhelperdocumentation.analizer.GlobalUtility;
import it.isislab.masonhelperdocumentation.analizer.Method;
import it.isislab.masonhelperdocumentation.analizer.SimStateAnalizer;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.ScrolledComposite;


/**
 * 
 * @author Romano Simone 0512101343
 * This page show start SimState's method description.
 */
public class I_StartInformationPage extends WizardPage{
	
	private static Logger log = Logger.getLogger("global");
	private SimStateAnalizer simStateAnalizer;
	private Composite container;
	private ScrolledComposite scrolledComposite;
	private Composite composite;
	private Text textMethodBody;
	private Text textUserDescription;
	private Label lblAutoGeneratedComment;
	private String pageDescription = "Who (i.e., what entity) does what, and in what order?\n";
	
	public I_StartInformationPage() {
		super("wizardPage");
		simStateAnalizer = GlobalUtility.getSimStateAnalizer();
		setTitle("3/7 - Process overview and scheduling\n" + simStateAnalizer.getClassName());
		setDescription(pageDescription);		
	}
	
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		setControl(container);
		container.setLayout(new GridLayout(1,true));
		
		scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scrolledComposite.heightHint = 256;
		gd_scrolledComposite.widthHint = 545;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
		
		textMethodBody = new Text(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_textMethodBody = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textMethodBody.heightHint = 169;
		textMethodBody.setLayoutData(gd_textMethodBody);
		
	
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		myCreateControl();
	}
		
	@Override
	public boolean canFlipToNextPage() {
		return true;
	}

	public void myCreateControl() {
		Method start = GlobalUtility.getStartMethod();
		//**************************************//
		//adding label with autogenerated comment
		lblAutoGeneratedComment = new Label(composite, SWT.NONE);
		lblAutoGeneratedComment.setText(GlobalUtility.getStartMethodInformation(start));
		//**************************************//
		
		Label lblPutYourComment = new Label(composite, SWT.NONE);
		lblPutYourComment.setText("Your comment here:");
		
		textUserDescription = new Text(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		GridData gd_textUserDescription = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_textUserDescription.heightHint = 65;
		textUserDescription.setLayoutData(gd_textUserDescription);
		
		textMethodBody.setText(textMethodBody.getText() +  start.getMethod());

		//getting old user input if exist//
		ProcessOverviewElement p = ODD.getProcessOverviewAndScheduling().getProcessOverviewElement("start");
		if (p != null)
			textUserDescription.setText(p.getUserDoWhat());
		//******************************//
		
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}

	public IWizardPage getNextPage(){ 
		//add start method information on method declaration
		Method start = GlobalUtility.getStartMethod();
		String commentValue = "@ingroup process\n*\n" + GlobalUtility.surroundWithSpan(GlobalUtility.userOutputColor, textUserDescription.getText()) + "\n";
		commentValue = commentValue + GlobalUtility.surroundWithSpan(GlobalUtility.autoOutputColor, lblAutoGeneratedComment.getText());
		GlobalUtility.setJavadocToMethod(GlobalUtility.getSimStateAnalizer().getRoot(), start, commentValue);
		ODD.addProcessOverviewElement("start", lblAutoGeneratedComment.getText(), textUserDescription.getText());
		//in previous pages we have already iterate on agents
		//now first to analyze step method for each agent
		//we reset variable "actualAgent" to start from first agent.
		GlobalUtility.resetActualAgent();
		if (GlobalUtility.getNumAgent_s() > 0 && GlobalUtility.getStepMethod() != null){
			L_StepMethodPage nextPage = new L_StepMethodPage();
			((MASONDocumentationWizard) super.getWizard()).addPage(nextPage);
			return nextPage;
		}
		M_DesignConceptsPage nextPage = new M_DesignConceptsPage();
		((MASONDocumentationWizard) super.getWizard()).addPage(nextPage);
		return nextPage;
	}
}
