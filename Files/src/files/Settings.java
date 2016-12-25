/**
 * 
 */
package files;

import java.io.*;

import enigma.Enigma;

/**
 * @author toader
 *
 */
public class Settings extends FileManager {

	public Settings(File parent) {
		super(parent, "settings");
	}
	
	public Enigma loadSettings (Machines machines, String s) throws Exception {
		InputStream setting = null;
		try {
			setting = new FileInputStream(getFile(s));
			
			
			return null;
		} catch (Exception e) {
			throw new Exception ("Error loading setting " + s, e);
		} finally {
			if(setting != null)
				setting.close();
		}
		
	}
	
	public Enigma loadSetting (String setting) {
		return null;
	}
	
	public void saveCurrentSetting (Enigma enigma, String s) throws Exception {
		OutputStream new_setting = null;
		try {
			//Check if name is available
			if (getFile(s) != null)
				throw new Exception ("Name was already taken");
			
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
