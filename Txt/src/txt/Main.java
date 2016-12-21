/**
 * 
 */
package txt;

import java.io.*;

import enigma.*;

/**
 * @author Marcello
 *
 */
public class Main {
	
	
	public static File path = new File("/Users/Marcello/enigma");

	public static void main(String[] args) {

		try {
			File input = new File("/Users/Marcello/input.txt");
			System.out.println(input.getName());
			Machines machines = new Machines (path);
			
			for(String machine: machines.getAvailable())
				System.out.println(machine);
			machines.getMachine("one");
		} catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		
		

	}

}