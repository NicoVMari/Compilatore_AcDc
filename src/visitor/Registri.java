package visitor;

import java.util.ArrayList;
import java.util.Arrays;

public class Registri {
	public static final ArrayList<Character> registri = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'));

	public static char newRegister() {
		char ris = registri.get(0);
		registri.remove(0);
		return ris;
	}
}
