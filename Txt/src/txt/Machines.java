package txt;

import java.io.*;

import enigma.*;

public class Machines {

	private File dir;
	
	public Machines(File parent) {
		dir = new File (parent, "machines");
	}
	public String [] getAvailable () {
		return dir.list();

	}
	
	public File getMachineSettings (final String s) {
		return new File (dir, dir.list(new FilenameFilter () {

			@Override
			public boolean accept(File file, String name) {
				
				return name.equals(s);
			}
			
		})[0]);
	}
	
	public Enigma getMachine (String s) throws Exception{
		FileInputStream stream = null;
		
		try {
			File settings = getMachineSettings(s);
			
			stream = new FileInputStream(settings);
			
			int letter_num = 0;
			Component ekw = null;
			Reflector [] ukws = null;
			Disk [] disks = null;
			int disk_num = 0;
			int turning_disks = 0;
			
			
			
			return new Enigma(letter_num, ekw, ukws, disks, disk_num, turning_disks);
		} catch (Exception e) {
			throw new Exception ("Error opening Enigma " + s + ".", e);
		} finally {
			if (stream != null)
				stream.close();
		}
		
	}

}
