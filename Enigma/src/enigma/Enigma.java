package enigma;

/**
 * The {@code ENIGMA Machine} is a coding machine invented by Arthur Schrebius
 * @author Marcello Fonda
 *
 */
public class Enigma {
	
	private final int turning_disks;
	private Component steckerbrett;
	private Disk etw;
	private Reflector[] ukws;
	private Reflector used_ukw;
	private Disk[] disks;
	private Disk[] used_disks;
	public static boolean debugMode;
	private String name;

	
	public Enigma ( int letter_num, Component Ekw, Reflector[] Ukws, Disk[] disks, int disk_num,
			int turning_disks, int max_verbindungen, String name) {
		
		this.steckerbrett = new Component(letter_num, max_verbindungen);
		this.etw = new Disk(Ekw.getWiring(), "", Ekw.getName());
		this.ukws = Reflector.copy(Ukws);
		this.disks = Disk.copy(disks);
		used_disks = new Disk[disk_num];
		this.turning_disks = turning_disks;
		this.name = new String (name);
	}
	public Enigma( int letter_num, Component Ekw, Reflector[] Ukws, Disk[] disks, int disk_num, 
			int turning_disks, String name ) {
		
		this.steckerbrett = new Component(letter_num);
		this.etw = new Disk(Ekw.getWiring(), "", Ekw.getName());
		this.ukws = Reflector.copy(Ukws);
		this.disks = Disk.copy(disks);
		used_disks = new Disk[disk_num];
		this.turning_disks = turning_disks;
		this.name = new String (name);
		
	}
	public Enigma( int letter_num, Component Ekw, Reflector[] Ukws, Disk[] disks, int disk_num, 
			int turning_disks, int max_verbindungen ) {
		
		this(letter_num, Ekw, Ukws, disks, disk_num, turning_disks, max_verbindungen, "NO NAME");
		
	}
	public Enigma( int letter_num, Component Ekw, Reflector[] Ukws, Disk[] disks, int disk_num, int turning_disks ) {
		
		this(letter_num, Ekw, Ukws, disks, disk_num, turning_disks, -1, "NO NAME");
		
	}
	//TODO NEW CONSTRUCTOR WITH SPECIAL DISKS
	
	public String [] getDisks () {
		String [] s = new String [disks.length];
		int index = 0;
		for(Disk d: disks)
			s[index++] = d.toString();
		return s;
	}
	
	public int getDiskNum () {
		return used_disks.length;
	}
	public String getUkw () {
		String s = "";
		s += used_ukw;
		return s;
	}
	
	public void setUkw (String s) throws Exception {
		try {
			System.out.println("Enigma " + this + ": Setting UKW: " + s);
			for (int i = 0; i < ukws.length; i++) {
				debug ("Comparing " + s + " with " + ukws[i]);
				if( s.equals(ukws[i].toString()) ) {
					setReflector(i);
					return;
				}
			}
			throw new Exception ("UKW " + s + " not found");
		} catch (Exception e) {
			throw new Exception ("Error setting UKW", e);
		}
	}
	public void setReflector (int i) throws Exception {
		try {
			if (i < 0 || i >= ukws.length)
				throw new IndexOutOfBoundsException ("Specified reflector does not exist: " + i);
			debug ("Setting reflector " + (char)(i + 'A'));
			used_ukw = ukws[i];
		} catch (Exception e) {
			throw new Exception ("Error setting reflector to use", e);
		}
	}
	public String [] getWalzenlage () {
		String [] ret = new String [used_disks.length];
		int i = 0;
		for (Disk d: used_disks) {
			ret[i++] = "" + d;
		}
		return ret;
	}
	
	public void setWalzenlage (String... strings) throws Exception {
		try {
			System.out.print("Enigma " + name + ": Setting Walzenlage: from left to right disks: ");
			for (String s: strings)
				System.out.print(s + " ");
			System.out.println();
			
			if(strings.length != used_disks.length)
				throw new Exception ("Number of disks is incorrect: " + strings.length + "; should be " + used_disks.length);
			
			int [] a = new int [strings.length];
			int index = 0;
			for (String s: strings) {
				int i;
				for (i = 0; i < disks.length; i++) {
					debug ("Comparing " + s + " with " + disks[i]);
					if( s.equals(disks[i].toString()) ) {
						a[index++] = i;
						break;
					}
				}
				if (i == disks.length)
					throw new Exception ("Disk " + s + " not found");
			}
			setUsedDisks(a);
		} catch (Exception e) {
			throw new Exception ("Error setting Walzenlage", e);
		}
	}
	/**
	 * This method sets the <i>Walzenlage</i>, i.e. which disks are used in the chip process.
	 * Will check for the right number of arguments comparing with that indicated in the constructor
	 * @param d the indexes of the {@code disks} to set (choosing from the , as you could see them from the top, from left to right
	 * @throws Exception 
	 */
	//TODO adapt to special disks
	public void setUsedDisks (int... d) throws Exception {
		try {
			
			if (d.length != used_disks.length)
				throw new Exception ("Argument number is incorrect: " + d.length + "; should be " + used_disks.length);
			
			for (int i = 0; i < d.length; i++) {
				if (d[i] < 0 || d[i] >= disks.length)
					throw new IndexOutOfBoundsException ("Specified disk does not exist: at position "
							+ i + ", " + d[i]);
				debug("Setting disk " + (d[i] + 1) + " at position " + (used_disks.length - i) + " from the right");
				
				used_disks[used_disks.length - 1 - i] = new Disk (disks[d[i]]);
			}
			if (debugMode)
				for (int i = 0; i < used_disks.length; i++) {
					used_disks[i].check();	
				}
		} catch (Exception e) {
			throw new Exception ("Error setting used disks", e);
		}
	}
	/**
	 *  Gives the rotation of each disk
	 * @return The string representing the letters visible to the operator, from left to right
	 */
	public String getRingstellung () {
		String s = "";
		for (int i = used_disks.length - 1; i > 0; i--)
			s += used_disks[i].getPosition();
		s += used_disks[0].getPosition();
		return s;
	}
	public void setRingstellung ( String s ) throws Exception {
		try {
			if (s.length() != used_disks.length)
				throw new Exception ( "Input string is not of correct length" );
			
			int i = used_disks.length - 1;
			
			for (Disk current_disk: used_disks) {
				
				char c = Character.toUpperCase(s.charAt(i));
				if ( c < Disk.getStart() || c >= current_disk.getLength() + Disk.getStart() )
					throw new IndexOutOfBoundsException ("Specified character is invalid: " + c);
				current_disk.setPosition(c);
				i--;
			}
		} catch (Exception e) {
			throw new Exception ("Error in Ringstellung setting process", e);
		}
	}
	public int [] getRingSetting () {
		int [] ret = new int [used_disks.length];
		int index = used_disks.length - 1;
		for(Disk d: used_disks) {
			ret[index--] = d.getRingSetting();
		}
		return ret;
	}
	
	public void setRingSetting (int... setting) throws Exception{
		try {
			for (int i = 0; i < used_disks.length; i++) {
				used_disks[used_disks.length - i - 1].setRingSetting(setting[i]);
			}
		} catch (Exception e) {
			throw new Exception ("Error setting rings", e);
		}
	}
	public String [] getSteckerverbindungen () throws Exception {
		try {
			String [] ret = steckerbrett.getConnections();
			return ret;
		} catch (Exception e) {
			throw new Exception("Error getting Steckerverbindungen", e);
		}
	}
	
	public void setSteckerverbindung (char a, char b) throws Exception {
		try {
			System.out.println("Enigma " + name + ": Setting Steckerverbindung (connection) between " + a + " and " + b + "...");
			
			switch (steckerbrett.connect(a, b)) {
			case -1:
				System.out.println ("Unable to set connection: max number of connections reached");
				break;
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
		}
		catch (Exception e) {
			throw new Exception ("Error setting Steckervervindung", e);
		}
	}
	public void operate( int n ) {
		if (used_disks[n].notchReached() && n <= turning_disks) operate(n+1);
		used_disks[n].turn();
	}
	
	public char chip ( char c ) throws Exception {
		String s = "]->";
		try {
			operate(0);
			debug (this + "[" + getRingstellung() + "]");
			s = c + " -STECK-> ";
			c = steckerbrett.chip(c);
			s += c + " -ETW-> ";
			c = etw.chip(c);
			int i = 0;
			for (Disk current_disk: used_disks) {
				s += c + " -ROT" + i + "-> ";
				c = current_disk.chip(c);
				i++;
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
			s += c;

			return c;
		} catch (Exception e) {
			throw new Exception ("Error in chip process", e);
		} finally {
			debug(s);
		}
		
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString () {
		return name;
		
	}
	public static void debug(String s) {
		if (debugMode)
			System.out.println(s);
	}

}
