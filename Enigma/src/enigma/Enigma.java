package enigma;

import java.util.Arrays;

/**
 * The {@code ENIGMA Machine} is a 
 * @author Marcello Fonda
 *
 */
public class Enigma implements stuff {
	
	private Component steckerbrett;
	private Disk etw;
	private Reflector[] ukws;
	private Reflector used_ukw;
	private Disk[] disks;
	private Disk[] used_disks;
	public boolean debugMode;

	public Enigma( int n, Component Ekw, Reflector[] Ukws, Disk[] disks, int diskn ) {
		
		this.steckerbrett = new Component(n);
		this.etw = new Disk(Ekw.getWiring(), "");
		this.ukws = (Reflector[]) Reflector.copy(Ukws);
		this.disks = Disk.copy(disks);
		used_disks = new Disk[diskn];
		
	}
	
	public void setReflector (int i) throws Exception {
		try {
			if (i < 0 || i >= ukws.length)
				throw OutOfRange;
			debug ("Setting reflector " + (char)(i + 'A'));
			used_ukw = ukws[i];
		} catch (Exception e) {
			throw new Exception ("Error setting reflector to use", e);
		}
	}
	
	public String getReflector () {
		String s = "";
		s += (char) (Arrays.asList(ukws).indexOf(used_ukw) + Disk.getStart());
		return s;
	}
	
	/**
	 * This method sets the <i>Walzenlage</i>, i.e. which disks are used in the chip process.
	 * Will check for the right number of arguments comparing with that indicated in the constructor
	 * @param d the indexes of the {@code disks} to set (choosing from the , as you could see them from the top, from left to right
	 * @throws Exception 
	 */
	public void setWalzenlage (int... d) throws Exception {
		try {
			if (d.length != used_disks.length)
				throw new Exception ("Argument number is incorrect");
			for (int i = 0; i < d.length; i++) {
				if (d[i] < 0 || d[i] >= disks.length)
					throw OutOfRange;
				debug("Setting disk " + (d[i] + 1) + " at position " + (used_disks.length - i) + " from the right");
				// E.g. i = 0 -> used_disks[2] == disks[d[0]]
				used_disks[used_disks.length - 1 - i] = new Disk (disks[d[i]]);
			}
			if (debugMode)
				for (int i = 0; i < used_disks.length; i++) {
					used_disks[i].check();	
				}
		} catch (Exception e) {
			throw new Exception ("Error in Walzenlage setting process", e);
		}
	}
	
	/**
	 *  Gives the rotation of each disk
	 * @return The string representing the letters visible to the operator, from left to right
	 */
	public String getRingstellung () {
		String s = "";
		for (int i = used_disks.length - 1; i > 0; i--)
			s += used_disks[i].getPosition() + "-";
		s += used_disks[0].getPosition();
		return s;
	}
	
	public void setRingstellung ( String s ) throws Exception {
		try {
			if (s.length() != used_disks.length)
				throw new Exception ( "Input string is not of correct length" );
			for (int i = 1; i <= used_disks.length; i++) {
				char c = Character.toUpperCase(s.charAt(i - 1));
				if ( c < Disk.getStart() || c >= used_disks[0].getLength() + Disk.getStart() )
					throw OutOfRange;
				used_disks[used_disks.length - i].setPosition(c);
			}
		} catch (Exception e) {
			throw new Exception ("Error in Ringstellung setting process", e);
		}
	}
	
	public void setSteckerverbindung (char a, char b) throws Exception {
		try {
			System.out.println("Setting Steckerverbindung (connection) between " + a + " and " + b + "...");
			
			switch (steckerbrett.connect(a, b)) {
			case 1:
				System.out.println ("Removed existing connection for " + a);
				break;
			case 2:
				System.out.println ("Removed existing connection for " + b);
				break;
			case 3:
				System.out.println ("Removed existing connection for " + a + " and " + b);
				break;
			default:
				break;	
			}
			System.out.println("Connection set.");
		}
		catch (Exception e) {
			throw new Exception ("Error setting Steckervervindung", e);
		}
	}
	
	public void operate( int n ) {
		if (used_disks[n].notchReached() && n <= used_disks.length) operate(n+1);
		used_disks[n].turn();
	}
	
	public char chip ( char c ) throws Exception {
		String s = "]->";
		try {
			operate(0);
			debug ("[" + getRingstellung() + "]");
			s = c + " -STECK-> ";
			c = steckerbrett.chip(c);
			s += c + " -ETW-> ";
			c = etw.chip(c);
			int i;
			for (i = 0; i < used_disks.length; i++) {
				s += c + " -ROT" + i + "-> ";
				c = used_disks[i].chip(c);
			}
			s += c + " -UKW-> ";
			c = used_ukw.chip(c);
			for (i = used_disks.length - 1; i >= 0; i--) {
				s += c + " -ROT" + i + "-> ";
				c = used_disks[i].rechip(c);
			}
			s += c + " -ETW-> ";
			c = etw.rechip(c);
			s += c + " -STECK-> ";
			c = steckerbrett.chip(c);
			
			debug(s);
			return c;
		} catch (Exception e) {
			debug(s);
			throw new Exception ("Error in chip process", e);
		}
		
	}
	
	//TODO Complete
	@Override
	public String toString () {
		String s = "[";
		
		return s;
		
	}
	private void debug(String s) {
		if (debugMode)
			System.out.println(s);
	}

}
