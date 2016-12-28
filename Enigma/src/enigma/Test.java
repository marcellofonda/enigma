package enigma;

/**
 * For DIYS fans
 * @author Marcello Fonda
 */
public class Test {

	public static void main(String[] args) {
		try {
			Component alpha_ekw = new Component ("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			
			Reflector [] one_r = {
					new Reflector("EJMZALYXVBWFCRQUONTSPIKHGD", "A"),
					new Reflector("YRUHQSLDPXNGOKMIEBFZCWVJAT", "B"),
					new Reflector("FVPJIAOYEDRZXWGCTKUQSBNMHL", "C")
			};
			
			Disk [] one_d = {
					new Disk ("EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q", "I"),
					new Disk ("AJDKSIRUXBLHWTMCQGZNPYFVOE", "E", "II"),
					new Disk ("BDFHJLCPRTXVZNYEIWGAKMUSQO", "V", "III"),
					new Disk ("ESOVPZJAYQUIRHXLNFTGKDCMWB", "J", "IV"),
					new Disk ("VZBRGITYUPSDNHLXAWMJQOFECK", "Z", "V")
			};
			for(Disk d: one_d)
				System.out.println(d);
			Enigma one = new Enigma (26, alpha_ekw, one_r, one_d, 3, 3, "one");
			for(String s: one.getDisks())
				System.out.println(s);
			Enigma.debugMode = false;
			
			one.setReflector(0);
			one.setWalzenlage("I", "II", "III");
			one.setRingstellung("AAA");
			one.setRingSetting(0,0,0);
			one.setSteckerverbindung('H', 'B');
			one.setSteckerverbindung('A', 'E');
			one.setSteckerverbindung('M', 'O');
			one.setSteckerverbindung('F', 'G');
			one.setSteckerverbindung('N', 'L');
			one.setSteckerverbindung('P', 'Q');
			one.setSteckerverbindung('D', 'R');
			one.setSteckerverbindung('K', 'I');
			one.setSteckerverbindung('S', 'W');
			one.setSteckerverbindung('Z', 'C');
			
			
			
			String s = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";//"VUXMTUNECHLLFGNNEPHMDSCAONYCSEJVGRFSGTRHOVLXIBNT";ZGGFNCYYXHQXTIIEXXZMQJWUWOBQCPKJJOIRDHI
			for (int i = 0; i < s.length(); i++)
				System.out.print(one.chip(s.charAt(i)));
			
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Ooops!");
			
		}
		
	}

}