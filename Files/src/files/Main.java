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
public class Main {
	
	
	public static File path = new File("/Users/toader/enigma");
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Enigma.debugMode = false;
		try {
			Machines machines = new Machines (path);
			Settings settings = new Settings (path);
			Components components = new Components (path);
			for(String machine: machines.getAvailable())
				System.out.println(machine);
			
			Enigma I = machines.getMachine("I", components);
			Enigma m3 = machines.getMachine("m3", components);
			Enigma norway = machines.getMachine("norway", components);
			
			
			I = settings.loadSetting(machines, components, "basic");
			
			String s = "VUXMTUNECHLLFGNNEPHMDSCAONYCSEJVGRFSGTRHOVLXIBNT";
			String enchipered = "";
			for (int i = 0; i < s.length(); i++)
				enchipered += I.chip(s.charAt(i));
			
			System.out.println (enchipered);
			//OK!
			
		} catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		
		

	}

}
