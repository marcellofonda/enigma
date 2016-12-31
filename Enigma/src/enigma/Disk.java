package enigma;


/**
 * 
 * @author Marcello
 *
 */
public class Disk extends Component {

	private int[] notches;
	private int offset;
	private int setting;
	
	public Disk (String wiring, String turnovers, String name) {
		super(wiring);
		setNotches(turnovers);
		offset = 0;
		setting = 0;
		setName(name);
	}
	public Disk (String wiring, String turnovers) {
		this(wiring, turnovers, "NO NAME");
	}
	public Disk (Disk d) {
		this(d.getWiring(), d.getNotches(), d.getName());
	}
	
	//Inherited from enigma.Component
	@Override
	public char chip (char c) throws Exception {
		String debug = "";
		try {
			int n = (Character.toUpperCase (c) - start);
			debug += "disk.chip:" + n + '\n';
			n = n + offset - setting;
			if (n < 0)
				n += wiring.length;
			n %= wiring.length;
			char d = (char) (encode(n) + start);
			debug += d +" ";
			
			d = (char) (d - offset + setting);
			if (d < start)
				d += wiring.length;
			else if (d >= start + wiring.length)
				d -= wiring.length;
			debug += d;
			
			return d;
		} catch (Exception e) {
			throw new Exception ("Error in disk chip process", e);
		} finally {
			Enigma.debug(debug);
		}
	}
	
	/**
	 * Scans through all contacts to find the one which connects to <i>c</i>
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public char rechip (char c) throws Exception {
		try {
			
			for (char i = start; i < wiring.length + start; i++) {
				Enigma.debug ("disk.rechip(" + c + "): " + i + "\n\t");
				if (chip(i) == c) {
					Enigma.debug ("Found " + i);
					return i;
				}
			}
			
			throw new Exception ("Not Found.");
		} catch (Exception e) {
			throw new Exception ("Error in rechip process", e);
		}
		
	}
	
	public char getPosition () {
		return (char)(offset + start);
	}
	public void setPosition ( char c ) throws Exception {
		
		try {
			int i = Character.toUpperCase (c) - start;
			if ( i < 0 || i >= wiring.length) 
				throw new IndexOutOfBoundsException ("Specified character is invalid: " + c);
			else offset = i;
		} catch (Exception e) {
			throw new Exception ("Error setting disk position", e);
		}
	}
	
	public void turn () {
		offset = ++offset % wiring.length;
	}

	/**
	 * @return the setting
	 */
	public int getRingSetting() {
		return setting;
	}
	/**
	 * @param setting the setting to set
	 */
	public void setRingSetting(int setting) throws Exception {
		try {
			if (setting < 0 || setting >= wiring.length)
				throw new IndexOutOfBoundsException ("Specified value doesn't fit the disk wiring: " + setting);
			this.setting = setting;
		} catch (Exception e) {
			throw new Exception ("Error setting ring for disk " + this, e);
		}
	}
	public String getNotches() {
		String s = "";
		for (int i = 0; i < notches.length; i++) {
			s += (char)(notches[i] + start);
		}
		return s;
	}

	public void setNotches(String s) {
		notches = new int[s.length()];
		for (int i = 0; i < s.length(); i++)
			notches[i] = s.charAt(i) - start;
	}
	
	public boolean notchReached () {
		for (int i = 0; i < notches.length; i++)
			if (offset == notches[i]) return true;
		return false;
	}
	
	public static Disk[] copy ( Disk[] c ) {

		Disk[] ret = new Disk[c.length];
		for (int i = 0; i < c.length; i++) {
			ret[i] = new Disk (c[i]);
		}
		return ret;
	}
	
	public void check() {
		try {
			System.out.println("Test started, disk " + this);
			for (char i = start; i < wiring.length + start; i++) {
				char c = chip(i);
				System.out.println("\t" + i + "->" + c);
				if(rechip(c) != i)
					throw new Exception ("Invalid disk");
				System.out.println(" OK");
			}
			System.out.println("Test successful");
		} catch (Exception e) {
			System.out.println("Test failed");
			e.printStackTrace();
		}
	}
}
