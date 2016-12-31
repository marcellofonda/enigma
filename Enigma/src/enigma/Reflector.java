package enigma;
//TODO Finish description
/**
 * Class {@code Reflector} is meant to represent the reflector disks (<i>UKWs</i>) of {@code Enigma}.
 * It has the same methods of {@link enigma.Component} plus
 * @author Marcello
 *
 */
public class Reflector extends Component {
	/**
	 * Checks if current reflector is valid, i.e. connections are bijective
	 * @return true if reflector is valid, false otherwise
	 */
	
	public Reflector (String s, String name) {
		super(s, name);
	}
	public Reflector (String s) {
		super(s, "NO NAME");
	}
	
	public Reflector (Component c) {
		super(c);
	}
	public boolean isValid () {
		for (int i = 0; i < wiring.length; i++) 
			if(wiring[wiring[i]] != i || wiring[i] == i) return false;
		return true;
		
	}
	
	@Override
	public char chip (char c) throws Exception {
		try {
			if(!isValid()) 
				throw new Exception ("Reflector in use is not valid");
			else 
				return super.chip(c);
		} catch (Exception e) {
			throw new Exception ("Error in reflector chip process", e);
		}
	}

	public static Reflector[] copy ( Reflector[] c ) {

		Reflector[] ret = new Reflector[c.length];
		for (int i = 0; i < c.length; i++) {
			ret[i] = new Reflector (c[i]);
		}
		return ret;
	}
}