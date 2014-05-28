package it.isislab.masonhelperdocumentation.mason.control;

import it.isislab.masonhelperdocumentation.ODD.Entitie_s;
import it.isislab.masonhelperdocumentation.ODD.ODD;
import it.isislab.masonhelperdocumentation.ODD.Purpose;
import it.isislab.masonhelperdocumentation.analizer.GlobalUtility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;


/**
 * This class manages config file.
 * 
 * @author Romano Simone 0512101343
 * 
 */
public class ConfigFile {
	private static Logger log = Logger.getLogger("global");
	private static String dirName = "MASONHelperDocumentation_";
	private static String configFileName = "configMASONHelper.dat";

	/**
	 * Return directory path; directory will be create (if does not exist) in
	 * disk root.
	 * 
	 * @return
	 */
	public static String getDir() {
		String dirPath = File.separator + dirName + GlobalUtility.getProjectAnalizer().getProjectName();
		new File(dirPath).mkdir();
		log.info("create/get dir: " + dirPath);
		return dirPath;
	}

	private static File getConfigFile() {
		String dirPath = getDir();
		String configFilePath = dirPath + File.separator + configFileName;
		File configFile = new File(configFilePath);
		try {
			configFile.createNewFile();
		} catch (IOException e) {
			log.severe("Error getting ConfigFile: " + e.getMessage());
			e.printStackTrace();
		}
		log.info("Got config file: " + configFilePath);
		return configFile;
	}

	/**
	 * Set the key to value in ConfigFile pointed by configFilePath
	 * 
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value) {
		FileInputStream fstream = null;
		DataInputStream in = null;
		BufferedWriter out = null;
		boolean propertySet = false;

		try {
			fstream = new FileInputStream(getConfigFile());
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			StringBuilder fileContent = new StringBuilder();

			while ((strLine = br.readLine()) != null) {
				if (strLine.startsWith(key)) {
					fileContent.append(key + "=" + value
							+ System.getProperty("line.separator"));
					propertySet = true;
				} else {
					fileContent.append(strLine);
					fileContent.append(System.getProperty("line.separator"));
				}
			}
			if (!propertySet) {
				fileContent.append(key + "=" + value);
			}

			FileWriter fstreamWrite = new FileWriter(getConfigFile());
			out = new BufferedWriter(fstreamWrite);
			out.write(fileContent.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fstream.close();
				out.flush();
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		log.info("Set key: " + key + " to value: " + value);
	}

	/**
	 * Return the value associated to input key
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		FileInputStream fstream = null;
		DataInputStream in = null;

		try {
			fstream = new FileInputStream(getConfigFile());
			in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while ((strLine = br.readLine()) != null) {
				if (strLine.startsWith(key)) {
					log.info("Get key: " + key);
					return strLine.split("=")[1];
				}
			}
		} catch (FileNotFoundException e) {
			log.severe("File not found: '" + getConfigFile() + "' "
					+ e.getMessage());
		} catch (IOException e) {
			log.severe("Error reading file: '" + getConfigFile() + "' "
					+ e.getMessage());
		}
		return "";
	}

	public static String gettODDPath() {
		return getDir() + File.separator;
	}
}
