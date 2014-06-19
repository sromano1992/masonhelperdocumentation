package it.isislab.masonhelperdocumentation.mason.control;

import it.isislab.masonhelperdocumentation.ODD.ODD;
import it.isislab.masonhelperdocumentation.ODD.ODDInformationAsString;
import it.isislab.masonhelperdocumentation.analizer.GlobalUtility;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chapter;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Header;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Section;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * This class create a pdf in output directory if user choices pdf output.
 * 
 * @author Romano Simone 0512101343 *
 */
public class PDFGenerator {
	private Font parTitle = new Font(Font.HELVETICA, 18, Font.BOLD);
	private Font parLine = new Font(Font.NORMAL, 10, Font.NORMAL);
	private static Logger log = Logger.getLogger("global");

	/**
	 * Use itext library to generate pdf.
	 * 
	 * @param filename
	 *            path of pdf to generate.
	 * @return String message
	 */
	public String createPdf(String filename) {
		// setup PdfWriter
		Document document = new Document();
		File pdfFile = null;
		try {
			pdfFile = new File(filename);
			if (!pdfFile.exists())
				pdfFile.createNewFile();
			PdfWriter.getInstance(document,
					new FileOutputStream(pdfFile, false));
		} catch (FileNotFoundException e) {
			log.severe("File " + filename + " not found!");
			JOptionPane.showMessageDialog(null, "File open in other program!");
			e.printStackTrace();
			return e.getMessage();
		} catch (DocumentException e) {
			log.severe("DocumentException: " + e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			log.severe("Error creating new file :" + e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}
		document.setMargins(50, 50, 70, 70);
		document.setMarginMirroring(true);
		
		document.open();
		
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		title.put(GlobalUtility.getProjectAnalizer().getProjectName() + " documentation", "");
		createElement(document, title.entrySet());
		addImage(document);
		try {
			document.newPage();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		createElement(document, ODDInformationAsString.getODDDescritpion().entrySet());
		createElement(document, ODDInformationAsString.getPurpose().entrySet());
		ArrayList<LinkedHashMap<String, String>> entitie_s = ODDInformationAsString.getEntities();
		for(LinkedHashMap<String, String> entity : entitie_s)
			createElement(document, entity.entrySet());
		createElement(document, ODDInformationAsString.getProcessElement().entrySet());
		createElement(document, ODDInformationAsString.getDesignConcpets().entrySet());
		createElement(document, ODDInformationAsString.getInitialization().entrySet());
		createElement(document, ODDInformationAsString.getInputData().entrySet());
		createElement(document, ODDInformationAsString.getSubmodels().entrySet());	
		
		document.close();
		log.info("PDF generated");
		return "done";
	}

	private void addImage(Document document) {
		//imageSelected?
		String imgDirPath = ConfigFile.getValue("imgPath");
		ArrayList<String> fileInDir = imgInDirectory(imgDirPath);
		ArrayList<String> image_s = new ArrayList<String>();
		if (fileInDir != null){
			for (String s : fileInDir){
				if (s.endsWith(".png") || s.endsWith("jpg"))
					image_s.add(s);
			}
		}
		for (String imgPath : image_s){
			Image img;
			try {
				img = Image.getInstance(imgPath);
		        document.add(img);
			} catch (BadElementException | IOException e) {
				log.severe("Error adding image_s: " + e.getMessage());
				e.printStackTrace();
			} catch (DocumentException e) {
				log.severe("Error adding image_s: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	private ArrayList<String> imgInDirectory(String path){
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> toReturn = new ArrayList<String>();
		if (listOfFiles != null){
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        toReturn.add(listOfFiles[i].getAbsolutePath());
		      }
		    }
		    return toReturn;
		}
		return null;
	}

	/**
	 * Generate new paragraph in document with input element.
	 * First element of set is considered paragraph title.
	 * @param document
	 * @param element
	 */
	private void createElement(Document document, Set<Entry<String, String>> element){
		Paragraph newPar = null;
		Chunk title, content = null;
		String firstElementValue = "";
		int cycle = 0;
		for (Entry<String, String> e : element) {
			title = new Chunk(e.getKey(), parTitle);
			content = new Chunk(e.getValue(), parLine);
			if (cycle == 0){	//title!
				newPar = new Paragraph(e.getKey(), parTitle);
				newPar.add(content);
				firstElementValue = e.getValue();
			}
			else{
				newPar.add(title);
				newPar.add(content);
			}
			cycle = 1;
		}

		try {			
			document.add(newPar);
			if (element.size() > 1 || (element.size()==1 && firstElementValue != ""))	//add new page only if page content is not empty
				document.newPage();
			log.info("Add new paragraph");
		} catch (DocumentException e) {
			log.severe("Error adding odd paragraph: " + e.getMessage());
			e.printStackTrace();
		}
	}

	
}
