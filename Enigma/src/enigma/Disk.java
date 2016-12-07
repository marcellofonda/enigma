package enigma;

/**
 * 
 * @author Marcello
 *
 */
public class Disk extends Component {

	private int[] notches;
	private int offset;
	
	public Disk (String wiring, String turnovers) {
		super(wiring);
		setNotches(turnovers);
		offset = 0;
	}
	public Disk (Disk d) {
		this(d.getWiring(), d.getNotches());
	}
	
	//Inherited from enigma.Component
	@Override
	public char chip (char c) throws Exception {
		try {
			int n = (Character.toUpperCase (c) - start);
			//System.out.print("disk.chip:" + n);
			char d = (char) (encode((n + offset) % wiring.length) + start);
			//System.out.print(d);
			d = (char) (d - offset);
			if (d < start)
				d += wiring.length;
			//System.out.println(d);
			return d;
		} catch (Exception e) {
			throw new Exception ("Error in disk chip process", e);
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
				//System.out.print("disk.rechip(" + c + "): " + i + "\n\t");
				if (chip(i) == c) {
					//System.out.println("Found " + i);
					return i;
				}
			}
			
			throw new Exception ("Not Found.");
		} catch (Exception e) {
			throw new Exception ("Error in rechip process", e);
		}
		
	}
	
	public void setPosition ( char c ) throws Exception {
		
		try {
			int i = Character.toUpperCase (c) - start;
			if ( i < 0 || i >= wiring.length) 
				throw OutOfRange;
			else offset = i;
		} catch (Exception e) {
			throw new Exception ("Error setting disk position", e);
		}
	}
	
	public char getPosition () {
		return (char)(offset + start);
	}
	
	public void turn () {
		offset = ++offset % wiring.length;
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
	
	//Inherited from enigma.Component
	@Override
	public String toString () {
		String s = super.toString();
		s += "[" + getNotches() + "]";
		return s;
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
			System.out.println("Test started");
			for (char i = start; i < wiring.length + start; i++) {
				char c = chip(i);
				System.out.print("\t" + i + "->" + c);
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
