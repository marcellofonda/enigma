package enigma;

/**
 * Class {@code Component} gives base features for all {@code Enigma}
 * components and fully represents some of them such as the Steckerbrett.
 * 
 * 
 * @author Marcello Fonda
 *
 */

//TODO Create Javadoc comments for chip, clearWiring, test

public class Component {
	
	protected static char start = 'A';
	
	private final int max_connections;
	private int used_connections;
	
	protected int [] wiring;
	protected String name;
	
	public Component ( String w, int max, String name ) {
		setWiring(w);
		setName(name);
		max_connections = max;
		used_connections = 0;
	}
	
	public Component ( String w, String name ) {
		setWiring(w);
		setName(name);
		max_connections = wiring.length / 2;
		used_connections = 0;
	}
	
	public Component ( String w, int max ) {
		this(w, max, "NO NAME");
	}
	
	public Component ( String w ) {
		this(w,"NO NAME");
	}
	
	public Component ( int n, int max, String name ) {
		setName (name);
		wiring = new int [n];
		clearWiring();
		max_connections = max;
		used_connections = 0;
	}
	
	public Component ( int n, String name ) {
		this(n, n/2, name);
	}
	
	public Component ( int n, int max ) {
		this(n, max, "NO NAME");
	}

	public Component ( int n ) {
		this(n, "NO NAME");
	}
	public Component ( Component c ) {
		this(c.getWiring(), c.max_connections, c.getName());
	}
	
	public Component () {
		this ( "", "NO NAME" );
	}

	/**
	 * Returns the contact at the other side of the disk to find 
	 * @param c the character to pass through the wiring of the machine
	 * @return the character at the other end of the wiring for <b>c</b>
	 * @throws Exception
	 */
	public char chip ( char c ) throws Exception {

		try {
			
			int n = Character.toUpperCase (c) - start;
			Enigma.debug("-" + n);
			c = (char) (encode(n) + start);
			Enigma.debug(c +"");
			return c;
			
		} catch ( Exception e) {
			
			throw new Exception ("Error in component chip process", e);
			
		}
	
	}
	
	protected int encode ( int n ) throws Exception {
		return wiring[n];
		
	}
	
	public void clearWiring () {
		for (int i = 0; i < wiring.length; i++) {
			wiring[i] = i;
		}
	}
	
	/**
	 * Creates a reciprocal connection between two letters in the wiring. If existing connection is found
	 * involving one of the specified letters, 
	 * @param c1
	 * @param c2
	 * @throws Exception "Error while setting connection" with the cause of the exception
	 * 
	 * @see enigma.Component.wiring
	 * @see enigma.Component.
	 */
	public int connect ( char c1, char c2 ) throws Exception {
		try {
			int ret = 0;
			
			int n1 = Character.toUpperCase(c1) - start;
			int n2 = Character.toUpperCase(c2) - start;

			if (n1 < 0 || n1 >= wiring.length) 
				throw new IndexOutOfBoundsException ("Specified character is invalid: " + c1);
			if (n2 < 0 || n2 >= wiring.length)
				throw new IndexOutOfBoundsException ("Specified character is invalid: " + c2);
			
			if (removeConnection(c1)) {
				ret++;
				used_connections--;
			}
			if (removeConnection(c2)) {
				ret += 2;
				used_connections--;
			}
			
			if (ret == 0 && used_connections == max_connections)
				return -1;
			
			wiring[n1] = n2;
			wiring[n2] = n1;
			used_connections++;
			
			return ret;
			
		} catch (Exception e) {
			throw new Exception ( "Error while setting connection", e);
		}
		
		
	}
	
	/**
	 * 
	 * @param c
	 * @return <b>true</b> if a connection was removed, <b>false</b> otherwise
	 */
	public boolean removeConnection (char c) {
		int n = Character.toUpperCase(c) - start;
		if (wiring[n] != n) {
			wiring[wiring[n]] = wiring[n];
			wiring[n] = n;
			return true;
		}
		return false;

	}

	public void test () throws Exception {
		
		System.out.println ( "Testing component " + this + ":");
		for (char c = start; c < (char)(start + wiring.length); c++)
			System.out.println ( c + " encodes to " + chip(c));
		
		System.out.println ( "DONE\n" );
		
	}

	/**
	 * This method gives the value of the starting character for the encoding process 
	 * (the character whose numerical interpretation is 0)
	 * @return the value of enigma.Component.start.
	 */
	public static char getStart () {
		return start;
	}

	
	public void setStart ( char c ) {
		start = Character.toUpperCase(c);
	}
	

	public String getWiring () {
		String s = "";
		
		for (int i = 0; i < wiring.length; i++)
			s += (char)(wiring[i] + start);
		return s;
		
	}

	/**
	 * Setter for {@link enigma.Component#wiring}.
	 * @param wiring the wiring to set.
	 */
	public void setWiring( String wiring ) {
		wiring.toUpperCase();
		this.wiring = new int[wiring.length()];
		for (int i = 0; i < wiring.length(); i++)
			this.wiring[i] = wiring.charAt(i) - start;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getLength() {
		return wiring.length;
	}
	
	/*
	public boolean checkFits( int n ) {
		if (wiring.length == n)
			return true;
		else 
			return false;
	}
	*/
	
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
		this.name = new String(name);
	}

	@Override
	public String toString () {
		String s = "";
		for(int i = name.lastIndexOf('_') + 1; i < name.length(); i++)
			s += name.charAt(i);
		
		return s;
	}
	
}