/**
 * 
 */
package files;

import java.io.*;

import enigma.Enigma;

/**
 * @author Marcello Fonda
 *
 */
public class Settings extends FileManager {
	
	/**
	 * 
	 * @param parent
	 */
	public Settings(File parent) {
		super(parent, "settings");
	}
	
	/**
	 * 
	 * @param machines the machines from 
	 * @param components
	 * @param setting
	 * @return the Enigma machine specified in the setting file, set as specified in the file
	 * @throws Exception
	 */
	public Enigma loadSetting (Machines machines, Components components, String setting) throws Exception {
		InputStream settings = null;
		try {
			settings = new FileInputStream (new File(dir, setting));
			
			//Get the machine
			Enigma ret = machines.getMachine(readLine(settings), components);
			settings.read();
			
			//The number of used disks of the machine
			int disk_num = ret.getDiskNum();
			
			//Set Walzenlage
			String [] walzenlage = new String[disk_num];
			for (int i = 0; i < disk_num; i++) {
				walzenlage[i] = readWord(settings);
			}
			ret.setWalzenlage(walzenlage);
			
			//Set used UKW
			ret.setUkw(readLine(settings));
			
			// Set Ring settings
			int [] a = new int [disk_num];
			for (int i = 0; i < disk_num; i++)
				a[i] = Integer.parseInt(readWord(settings));
			ret.setRingSetting(a);
			
			// Set Ringstellung
			ret.setRingstellung(readLine(settings));
			settings.read();
			
			//Set Steckerbrett wiring
			String couple = readLine(settings);
			while ( !couple.equals("") ) {
				ret.setSteckerverbindung(couple.charAt(0), couple.charAt(1));
				couple = readLine(settings);
			}
			return ret;
		} catch (FileNotFoundException e) {
			throw new Exception ("Error loading setting " + setting, e);
		} finally {
			if (settings != null)
				settings.close();
		}
		
	}
	
	public void saveCurrentSetting (Enigma enigma, String s) throws Exception {
		OutputStream new_setting = null;
		try {
			//Check if name is available
			if (getFile(s) != null) {
				//TODO delete if necessary
			}
			
			//Define the FileStream where to save the setting
			new_setting = new FileOutputStream(new File(dir, s));
			
			new_setting.write((enigma + "\n\n").getBytes());
			
		} catch (Exception e) {
			throw new Exception ("Error while saving setting, name " + s, e);
		} finally {
			if (new_setting != null)
				new_setting.close();
		}
	}

}
