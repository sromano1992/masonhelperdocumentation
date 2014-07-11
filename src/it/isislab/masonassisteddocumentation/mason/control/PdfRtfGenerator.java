package it.isislab.masonassisteddocumentation.mason.control;

import it.isislab.masonassisteddocumentation.ODD.ODDInformationAsString;
import it.isislab.masonassisteddocumentation.mason.analizer.GlobalUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.rtf.RtfWriter2;

/**
 * This class create a pdf in output directory if user choices pdf output.
 * 
 * @author Romano Simone 0512101343 *
 */
public class PdfRtfGenerator {
	private static final int _IMG_HEIGTH = 400;
	private static final int _IMG_WIDTH = 400;
	private Font parTitle;
	private Font parSubTitle;
	private Font elementTitle;
	private Font parLine;
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
		createFont();
		Document document = new Document();
		File pdfFile = null;
		try {
			pdfFile = new File(filename);
			if (!pdfFile.exists())
				pdfFile.createNewFile();
			PdfWriter.getInstance(document,	new FileOutputStream(pdfFile, false));
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
		createDocument(document);
		log.info("pdf generated");
		return "done";
	}
	
	/**
	 * Use itext library to generate rtf.
	 * 
	 * @param filename
	 *            path of rtf to generate.
	 * @return String message
	 */
	public String createRtf(String filename) {
		createFont();
		Document document = new Document();
		File rtfFile = null;
		try {
			rtfFile = new File(filename);
			if (!rtfFile.exists())
				rtfFile.createNewFile();
			RtfWriter2.getInstance(document, new FileOutputStream(rtfFile));
		} catch (FileNotFoundException e) {
			log.severe("File " + filename + " not found!");
			JOptionPane.showMessageDialog(null, "File open in other program!");
			e.printStackTrace();
			return e.getMessage();
		} catch (IOException e) {
			log.severe("Error creating new file :" + e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}
		createDocument(document);
		log.info("rtf generated");
		return "done";
	}

	private void createDocument(Document document) {
		document.setMargins(50, 50, 70, 70);
		document.setMarginMirroring(true);
		
		setHeaderAndFooter(document);		
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
	}

	private void setHeaderAndFooter(Document document) {
		// headers and footers must be added before the document is opened
        HeaderFooter footer;
		try {
			footer = new HeaderFooter(
			            new Phrase("", new Font(getFont(), 7, Font.NORMAL)), true);
			footer.setBorder(Rectangle.NO_BORDER);
			            footer.setAlignment(Element.ALIGN_CENTER);
			            document.setFooter(footer);

			            HeaderFooter header = new HeaderFooter(
			                        new Phrase("Document has been generated by MAD(Mason assisted documentation), built by Simone Romano in ISISLab.", new Font(getFont(), 7, Font.NORMAL)), false);
			            header.setAlignment(Element.ALIGN_CENTER);
			            document.setHeader(header);
		} catch (DocumentException | IOException e) {
			log.severe("Exception adding header/footer: " + e.getMessage());
			e.printStackTrace();
		}
        
	}

	/**
	 * This method gets font for pdf.
	 */
	private void createFont() {		
		try {
			BaseFont bf = getFont();
			parLine = new Font(bf, 10, Font.NORMAL);
			parTitle = new Font(bf, 14, Font.BOLD);
			parSubTitle = new Font(bf, 13, Font.BOLD);
			elementTitle = new Font(bf, 12, Font.ITALIC);
		} catch (DocumentException e) {
			log.severe("Error getting font: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.severe("IOExcpetion getting font: " + e.getMessage());
			e.printStackTrace();
		}			
	}

	private BaseFont getFont() throws DocumentException, IOException {
		String fontPath = this.getClass().getResource("/font/Concrete/CALIBRI.TTF").getPath();	
		BaseFont bf = BaseFont.createFont(fontPath, BaseFont.WINANSI, false);
		return bf;
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
				img.scaleAbsolute(_IMG_WIDTH, _IMG_HEIGTH);
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
			title = new Chunk(e.getKey(), elementTitle);
			content = new Chunk(e.getValue(), parLine);
			if (cycle == 0){	//title!
				if (e.getKey().contains("_subTitle")){
					newPar = new Paragraph(e.getKey().replace("_subTitle", ""), parSubTitle);
					newPar.add(content);
					firstElementValue = e.getValue();
				}
				else{
					newPar = new Paragraph(e.getKey(), parTitle);
					newPar.add(content);
					firstElementValue = e.getValue();
				}
				cycle = 1;
			}
			else{
				List list = new List(false, 20);
				list.add(new ListItem(title));
				newPar.add(list);
				newPar.add(content);
			}
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
