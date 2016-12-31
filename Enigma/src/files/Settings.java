/**
 * 
 */
package files;

import java.io.*;

import enigma.Enigma;
import files.Components;
import files.FileManager;
import files.Machines;

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
	 * @return the Enigma machine specified in the setting files, set as specified in the files
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
	
	public void saveCurrentSetting (Enigma enigma, String name, boolean overwrite) throws Exception {
		OutputStream new_setting = null;
		try {
			//Check if name is available
			if (getFile(name) != null) {
				//TODO delete if necessary
			}
			
			//Define the FileStream where to save the setting
			new_setting = new FileOutputStream(new File(dir, name));
			
			//Write the machine's name
			new_setting.write((enigma + "\n\n").getBytes());
			int n = enigma.getDiskNum();
			
			//Write the Walzenlage
			String [] used_disks = enigma.getWalzenlage();
			for (int i = n - 1; i > 0; i--) {
				new_setting.write((used_disks[i] + " ").getBytes());
			}
			new_setting.write((used_disks[0] + "\n").getBytes());
			
			//Write the reflector
			new_setting.write((enigma.getUkw() + "\n").getBytes());
			
			//Write the ring settings
			int [] ring_settings = enigma.getRingSetting();
			for (int i = 0; i < n - 1; i++) {
				new_setting.write((ring_settings[i] + " ").getBytes());
			}
			new_setting.write((ring_settings[n - 1] + "\n").getBytes());
			
			//Write the Ringstellung
			new_setting.write((enigma.getRingstellung() + "\n\n").getBytes());
			
			//Write the Steckerverbindungen
			for(String s: enigma.getSteckerverbindungen()) {
				new_setting.write((s + "\n").getBytes());
			}
			
		} catch (Exception e) {
			throw new Exception ("Error while saving setting, name " + name, e);
		} finally {
			if (new_setting != null)
				new_setting.close();
		}
	}

}
