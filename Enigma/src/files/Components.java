/**
 * 
 */
package files;

import java.io.*;

import enigma.Disk;
import files.FileManager;


/**
 * @author Marcello Fonda
 *
 */
public class Components extends FileManager {

	/**
	 * 
	 * @param parent
	 * @param dir
	 */
	public Components(File parent) {
		super(parent, "components");
	}
	
	
	/**
	 * 
	 * @param type
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Disk getComponent (final String type, String name) throws Exception {
		InputStream read = null;
		Disk ret = null;
		try {
			//Check if specified directory exists
			if (dir.list(new FilenameFilter () {

				@Override
				public boolean accept(File dir, String filename) {
					return filename.equals(type);
				}
				
			}) == null) throw new IOException ("Component type " + type + " not found");
			
			read = new FileInputStream(new File(dir, type + "/" + name));
			
			String wiring = readLine (read);
			switch (type) {
			case "etws":
			case "ukws":
				ret = new Disk (wiring, "", name);
				break;
			case "disks":
				String turnovers = readLine (read);
				ret = new Disk (wiring, turnovers, name);
				break;
			default:
				throw new Exception ("Invalid type specified");
			}

			return ret;
		} catch (Exception e) {
			throw new Exception ("Error opening " + type + " " + name, e);
		} finally {
			if (read != null)
				read.close();
		}
	}

}
