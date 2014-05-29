package it.isislab.masonhelperdocumentation.mason.wizards;

import it.isislab.masonhelperdocumentation.ODD.Entity;
import it.isislab.masonhelperdocumentation.ODD.ODD;
import it.isislab.masonhelperdocumentation.ODD.Variable;
import it.isislab.masonhelperdocumentation.analizer.GlobalUtility;
import it.isislab.masonhelperdocumentation.analizer.Parameter;
import it.isislab.masonhelperdocumentation.analizer.SimStateAnalizer;
import it.isislab.masonhelperdocumentation.mason.wizards.MASONDocumentationWizard;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.ScrolledComposite;

/**
 * 
 * @author Romano Simone 0512101343
 *	This page collects information about grids of Simulation.
 */
public class G_GridsCellPage extends WizardPage{
	
	private static Logger log = Logger.getLogger("global"); //$NON-NLS-1$
	private SimStateAnalizer simStateAnalizer;
	private Text text;
	private ArrayList<Text> fieldUserDescription;
	private ArrayList<Label> fieldAutomaticDescription;
	private String pageDescription = "Here information about grids:"; //$NON-NLS-1$
	
	/**
	 * Constructor for Page3.
	 * 
	 * @param javaProject
	 */
	public G_GridsCellPage() {
		super("wizardPage"); //$NON-NLS-1$
		simStateAnalizer = GlobalUtility.getSimStateAnalizer();
		setTitle("2/7 - Entities, state variables, and scales\n" + simStateAnalizer.getClassName()); //$NON-NLS-1$
		setDescription(pageDescription);
		fieldUserDescription = new ArrayList<Text>();
		fieldAutomaticDescription = new ArrayList<Label>();
	}

	
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.verticalSpacing = 9;		
		ScrolledComposite scrolledComposite = new ScrolledComposite(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		GridData gd_scrolledComposite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_scrolledComposite.heightHint = 255;
		gd_scrolledComposite.widthHint = 543;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);		
		Composite composite = new Composite(scrolledComposite, SWT.NONE);
		GridLayout gl_composite = new GridLayout();
		gl_composite.numColumns = 2;
		gl_composite.verticalSpacing = 9;
		composite.setLayout(gl_composite);
		
		ArrayList<Parameter> grids = simStateAnalizer.getFieldsParameters();	
		if (grids != null && grids.size()!=0){
			//creating input for each grid in SimState
			for (int i=0; i<grids.size(); i++){			
				Label lblGridname = new Label(composite, SWT.NONE);
				lblGridname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
				text = new Text(composite, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
				GridData gd_text = new GridData(SWT.LEFT, SWT.LEFT, true, false, 1, 1);
				gd_text.heightHint = 44;
				gd_text.widthHint = 300;
				text.setLayoutData(gd_text);
				new Label(composite, SWT.NONE);			
				Label lblInitializerDetector = new Label(composite, SWT.NONE);
				Label lblEndGrid = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
				GridData gd_lblEndGrid = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
				gd_lblEndGrid.widthHint = 500;
				lblEndGrid.setLayoutData(gd_lblEndGrid);
				lblEndGrid.setText("end grid"); //$NON-NLS-1$
				
				lblGridname.setText(grids.get(i).getVariableType() + " " + grids.get(i).getVariableName());		 //$NON-NLS-1$
				List<Expression> constructorInput = GlobalUtility.getConstructorVariableInput(simStateAnalizer.getCompilationUnit(), grids.get(i));
				setConstructorText(lblInitializerDetector, constructorInput, grids.get(i));		
				
				//getting old user input if exist//
				Entity entity = ODD.getEntity(simStateAnalizer.getClassName());
				if (entity != null){
					Variable var = entity.getVariable(grids.get(i).getVariableName());
					if (var != null)
						text.setText(var.getUserComment());
				}
				//******************************//
				
				fieldAutomaticDescription.add(lblInitializerDetector);
				fieldUserDescription.add(text);
			}
		}
		else if (grids.size() == 0){	//SimState should not have Grids
			Label lblGridname = new Label(composite, SWT.NONE);
			lblGridname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
			lblGridname.setText(GlobalUtility.getProjectAnalizer().getSimStateCU().getElementName() + " doesn't contains variables" //$NON-NLS-1$
					+ "of type:\n" + simStateAnalizer.getFieldsString() + "."); //$NON-NLS-1$ //$NON-NLS-2$
		}
		setControl(container);
		scrolledComposite.setContent(composite);
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	}
	
	/**
	 * Set the label lblInitializerDetetector with information about field.
	 * Add the relative input variable (height, widht, initialStatus) in SimulationModelGroup.
	 * @param lblInitializerDetector
	 * @param constructorInput
	 * @param field
	 */
	private void setConstructorText(Label lblInitializerDetector, List<Expression> constructorInput, Parameter field) {
		if (constructorInput == null)	return;
		if (constructorInput.size() == 2){
			//2 parameters: width-heigth
			lblInitializerDetector.setText(Messages.GridsCell_WidthValue + field.getVariableName() + Messages.GridsCell_widthIs+ constructorInput.get(0) + ";\n" //$NON-NLS-3$
									+ Messages.GridsCell_HeigthValue + field.getVariableName() + Messages.GridsCell_heigthIs + constructorInput.get(1) + "."); //$NON-NLS-3$
			Parameter p = GlobalUtility.getParameterFromString(constructorInput.get(0).toString(), simStateAnalizer.getCompilationUnit());
			Parameter p1 = GlobalUtility.getParameterFromString(constructorInput.get(1).toString(), simStateAnalizer.getCompilationUnit());
			if (p != null){	
				lblInitializerDetector.setText(lblInitializerDetector.getText() + "\n" //$NON-NLS-1$
					+ Messages.GridsCell_ValueOf + p.getVariableName() +Messages.GridsCell_widthIs  + p.getInitializer() +"."); //$NON-NLS-2$ //$NON-NLS-3$
				//add to group
				addToGroup(p);
			}
			if (p1 != null)	{
				lblInitializerDetector.setText(lblInitializerDetector.getText() + "\n" //$NON-NLS-1$
					+  Messages.GridsCell_ValueOf + p1.getVariableName() + Messages.GridsCell_widthIs + p1.getInitializer() +"."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				//add to group
				addToGroup(p1);
			}
		}
		else if (constructorInput.size() == 3){
			lblInitializerDetector.setText(Messages.GridsCell_WidthValue + field.getVariableName() + " is: "+ constructorInput.get(0) + ";\n" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ Messages.GridsCell_HeigthValue + field.getVariableName() + Messages.GridsCell_widthIs + constructorInput.get(1) + ".\n" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ Messages.GridsCell_ValueOf + field.getVariableName() + Messages.GridsCell_widthIs+ constructorInput.get(2) + "."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			Parameter p = GlobalUtility.getParameterFromString(constructorInput.get(0).toString(), simStateAnalizer.getCompilationUnit());
			Parameter p1 = GlobalUtility.getParameterFromString(constructorInput.get(1).toString(), simStateAnalizer.getCompilationUnit());
			Parameter p2 = GlobalUtility.getParameterFromString(constructorInput.get(2).toString(), simStateAnalizer.getCompilationUnit());
			if (p != null){	
				lblInitializerDetector.setText(lblInitializerDetector.getText() + "\n" //$NON-NLS-1$
					+  Messages.GridsCell_ValueOf + p.getVariableName() +Messages.GridsCell_widthIs + p.getInitializer() +"."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				//add to group
				addToGroup(p);
			}
			if (p1 != null){	
				lblInitializerDetector.setText(lblInitializerDetector.getText() + "\n" //$NON-NLS-1$
					+  Messages.GridsCell_ValueOf + p1.getVariableName() +Messages.GridsCell_widthIs + p1.getInitializer() +"."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				//add to group
				addToGroup(p1);
			}
			if (p2 != null){	
				lblInitializerDetector.setText(lblInitializerDetector.getText() + "\n" //$NON-NLS-1$
					+  Messages.GridsCell_ValueOf + p2.getVariableName() +Messages.GridsCell_widthIs + p2.getInitializer() +"."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				//add to group
				addToGroup(p2);
			}			
		}
	}

	/**
	 * add '@ingroup SimulationModel' to p
	 * @param p
	 */
	private void addToGroup(Parameter p) {
		if (!(p.getJavadoc().contains("@ingroup entities"))) //$NON-NLS-1$
			GlobalUtility.setJavadocToField(simStateAnalizer.getRoot(), p.getField(), p.getJavadoc() + " @ingroup entities"); //$NON-NLS-1$
		if (!simStateAnalizer.alreadyVisited(p))	simStateAnalizer.getVisitedParameters().add(p);		
	}


	@Override
	public boolean canFlipToNextPage() {
		return true;
	}
	
	public boolean isComplete(){
		return true;
	}
	

	public IWizardPage getNextPage(){ 
		ArrayList<Parameter> fields = simStateAnalizer.getFieldsParameters();
		for (int i=0; i<fields.size(); i++){
			String userComment = fieldUserDescription.get(i).getText();
			String autogeneratedComment = fieldAutomaticDescription.get(i).getText();
			String commentValue = "@ingroup entities\n *\n"; //$NON-NLS-1$
			commentValue = commentValue + userComment + "\n"; //$NON-NLS-1$
			commentValue = commentValue + autogeneratedComment;
			ODD.addVariableToEntity(simStateAnalizer.getClassName(), new Variable(fields.get(i).getVariableName(), userComment, autogeneratedComment, fields.get(i).getInitializer()));
			GlobalUtility.setJavadocToField(simStateAnalizer.getRoot(), fields.get(i).getField(), commentValue);
			simStateAnalizer.getVisitedParameters().add(fields.get(i));
		}

		//((MASONDocumentationWizard) super.getWizard()).addPage(new Page4_old());
		log.info("Comment added to grids..."); //$NON-NLS-1$
		H_EnvironmentPage nextPage= new H_EnvironmentPage();
		((MASONDocumentationWizard) super.getWizard()).addPage(nextPage); 
		return nextPage; 
	}
}