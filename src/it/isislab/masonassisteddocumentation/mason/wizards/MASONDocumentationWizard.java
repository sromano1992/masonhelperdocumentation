package it.isislab.masonassisteddocumentation.mason.wizards;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;

import it.isislab.masonassisteddocumentation.ODD.ODD;
import it.isislab.masonassisteddocumentation.mason.analizer.GlobalUtility;
import it.isislab.masonassisteddocumentation.mason.control.ConfigFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JOptionPane;

import org.eclipse.ui.*;

/**
 * 
 * @author Romano Simone 0512101343
 * 
 */
public class MASONDocumentationWizard extends Wizard implements IExportWizard {
	private static final int _logFileSIZE = 100000000;
	private IJavaProject javaProject;
	private static Logger log = Logger.getLogger("global");
	private static String logFileName = "log.txt";

	/**
	 * Constructor for MASONDocumentationWizard.
	 */
	public MASONDocumentationWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */
	public void addPages() {
		addPage(new A_IntroductionPage());
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard.
	 */
	public boolean performFinish() {
		GlobalUtility.setAllToNull();
		return true;
	}

	public boolean performCancel() {
		GlobalUtility.setAllToNull();
		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		getProject(selection);
		getODDObject(); // get old serialized ODD if it exists
		setLogger();
	}

	private void setLogger() {
		try {
			String logFilePath = ConfigFile.getProjectDir() + File.separator
					+ logFileName;
			Handler handler;
			handler = new FileHandler(logFilePath, _logFileSIZE, 1);
			handler.setFormatter(new SimpleFormatter());
			Logger.getLogger("global").addHandler(handler);
		} catch (SecurityException e) {
			log.severe("SecurityException creating log file: " + e.getMessage());
		} catch (IOException e) {
			log.severe("IOException creating log file: " + e.getMessage());
		}
	}

	private void getODDObject() {
		ODD.deserialize();
	}

	private void getProject(IStructuredSelection selection) {
		try {
			Object o = selection.getFirstElement();
			IProject project = (IProject) o;
			if (project.isNatureEnabled("org.eclipse.jdt.core.javanature")) {
				this.javaProject = JavaCore.create(project);
				new GlobalUtility(this.javaProject);
				log.info("IJavaProject create..."
						+ javaProject.getElementName());
			}
		} catch (CoreException e) {
			log.severe("Error in init method creating javaProject: "
					+ e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			log.severe(e.getMessage());
			JOptionPane
					.showMessageDialog(null, "Open 'Project Explorer' view!");
		}
	}
}