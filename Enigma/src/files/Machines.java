package files;

import java.io.*;

import enigma.*;
import files.Components;
import files.FileManager;

/**
 * Handles the various Enigma machines available
 * @author Marcello Fonda
 *
 */
public class Machines extends FileManager {
	
	public Machines(File parent) {
		super(parent, "machines");
	}

	/**
	 * Builds a new Enigma machine by reading the files specified by {@code machine_name} in the 
	 * directory {@code machines} to retrieve informations on how to do it.
	 * The files contains the names of the various parts of the machine, which will be used by 
	 * {@code components} to build them.
	 * @param machine_name the name of the machine you want to build. Corresponds
	 * @param components
	 * @return the built machine
	 * @throws Exception
	 * 
	 * @see Components
	 * @see enigma.Enigma
	 */
	public Enigma getMachine (String machine_name, Components components) throws Exception{
		
		FileInputStream blueprints = null;
		
		try {
			
			blueprints = new FileInputStream(getFile(machine_name));
			
			// The parameters to initialize the Enigma Machine
			int letter_num = 0;
			Component etw = null;
			Reflector [] ukws = null;
			Disk [] disks = null;
			int disk_num = 0;
			int turning_disks = 0;
			int max_steckerverbindungen = 0;
			
			// Read first line to find an int (letter_num)
			letter_num = Integer.parseInt(readLine(blueprints));
			Enigma.debug(""+ letter_num);
			blueprints.read();
			
			// Read second line to find the name of the etw
			etw = components.getComponent("etws", readLine(blueprints));
			Enigma.debug(""+ etw);
			blueprints.read();
			
			// Read third line to find an int (number of available disks)
			int available_num = Integer.parseInt(readLine(blueprints));
			
			// Read names of the disks
			disks = new Disk [available_num];
			for (int i = 0; i < available_num; i++) {
				disks[i] = components.getComponent("disks", readLine(blueprints));
				Enigma.debug(disks[i] +"");
			}
			available_num = 0;
			blueprints.read();
			
			// Read an int (number of available ukws)
			available_num = Integer.parseInt(readLine(blueprints));
			
			// Read names of the ukws
			String name = "";
			ukws = new Reflector [available_num];
			for (int i = 0; i < available_num; i++) {
				name = readLine(blueprints);
				ukws[i] = new Reflector(components.getComponent("ukws", name).getWiring());
				ukws[i].setName(name);
				Enigma.debug(ukws[i] +"");
			}
			blueprints.read();
			
			// Read the number of used disks and the number of turning disks
			disk_num = Integer.parseInt(readLine(blueprints));
			blueprints.read();
			turning_disks = Integer.parseInt(readLine(blueprints));
			blueprints.read();
			max_steckerverbindungen = Integer.parseInt(readLine(blueprints));
			
			// Build the machine
			return new Enigma(letter_num, etw, ukws, disks, disk_num, turning_disks, max_steckerverbindungen,
					machine_name);
			
		} catch (Exception e) {
			throw new Exception ("Error opening Enigma " + machine_name, e);
		} finally {
			// Close the stream
			if (blueprints != null)
				blueprints.close();
		}
		
	}
	
}
