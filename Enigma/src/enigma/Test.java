package enigma;

/**
 * Class with main function and testing purposes
 * @author Marcello
 * @deprecated On release
 */
public class Test {

	public static void main(String[] args) {
		try {
			Component alpha_ekw = new Component ("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			
			Reflector [] one_r = {
					new Reflector("EJMZALYXVBWFCRQUONTSPIKHGD"),
					new Reflector("YRUHQSLDPXNGOKMIEBFZCWVJAT"),
					new Reflector("FVPJIAOYEDRZXWGCTKUQSBNMHL")
			};
			
			Disk [] one_d = {
					new Disk ("EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q"),
					new Disk ("AJDKSIRUXBLHWTMCQGZNPYFVOE", "E"),
					new Disk ("BDFHJLCPRTXVZNYEIWGAKMUSQO", "V"),
					new Disk ("ESOVPZJAYQUIRHXLNFTGKDCMWB", "J"),
					new Disk ("VZBRGITYUPSDNHLXAWMJQOFECK", "Z")
			};
		
			
			Enigma one = new Enigma (26, alpha_ekw, one_r, one_d, 3);
			one.debugMode = false;
			one.setReflector(1);
			one.setWalzenlage(0, 1, 2);
			one.setRingstellung("AAA");
			one.setSteckerverbindung('H', 'B');
			String s = "JRLMREKFECMELMBNYAUCUHTG";
			for (int i = 0; i < s.length(); i++)
				System.out.print(one.chip(s.charAt(i)));
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Ooops!");
			
		}
		
	}

}