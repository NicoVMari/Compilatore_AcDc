package symbolTable;

import java.util.HashMap;

import ast.LangType;

public class SymbolTable {
	public static class Attributes {
		private LangType tipo;

		public Attributes(LangType tipo) {
			this.tipo = tipo;
		}

		public LangType getTipo() {
			return tipo;
		}
	}
	
	private static HashMap<String, Attributes> table;
	
	public static void init() {
		table = new HashMap<String,Attributes>();
	}
	
	public static boolean enter(String id, Attributes entry) {
		if(table.get(id) != null) return false;
		else {
			table.put(id, entry);
			return true;
		}
	}
	
	public static Attributes lookup(String id) {
		return table.get(id);
	}
	
	public static String toStr() {
		StringBuilder tableString = new StringBuilder();
		
		table.forEach((key, value) -> {
			tableString.append(key + "\t" + value + "\n");
		});
		
		return tableString.toString();
	}
	
	public static int size() {
		return table.size();
	}

}
