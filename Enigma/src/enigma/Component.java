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

public class Component implements stuff {
	
	protected int [] wiring;
	
	protected static char start = 'A';
	
	public Component () {
		this ( "" );
	}
	
	public Component ( int n ) {
		wiring = new int [n];
		clearWiring();
	}
	
	public Component ( Component c ) {
		this(c.getWiring());
	}
	
	public Component ( String w ) {
		setWiring(w);
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
			//System.out.print("-" + n);
			c = (char) (encode(n) + start);
			//System.out.println(c);
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
	public void connect ( char c1, char c2 ) throws Exception {
		try {
			int n1 = Character.toUpperCase(c1) - start;
			int n2 = Character.toUpperCase(c2) - start;
			
			if (n1 < 0 || n1 >= wiring.length || n2 < 0 || n2 >= wiring.length) 
				throw OutOfRange;
			int n = n1;
			for (int i = 0; i < 2; i++) {
				if (wiring[n] != n)
					wiring[wiring[n]] = wiring[n];
					wiring[n] = n;
				n = n2;
			}
			
			wiring[n1] = n2;
			wiring[n2] = n1;
			
		} catch (Exception e) {
			throw new Exception ( "Error while setting connection", e);
		}
		
		
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
		start = c;
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
	
	@Override
	public String toString () {
		
		String s = "[";
		
		s += getWiring() + "]";
		
		return s;
	}
	
}