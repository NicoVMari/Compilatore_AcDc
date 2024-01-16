package symbolTable;

import java.util.HashMap;

import ast.LangType;

public class SymbolTable {
	public static class Attributes {
		private LangType tipo;
		private char registro;

		public Attributes(LangType tipo) {
			this.tipo = tipo;
		}

		public LangType getTipo() {
			return tipo;
		}

		@Override
		public String toString() {
			return ""+ tipo + "";
		}
	}
	
	private static HashMap<String, Attributes> table;
	
	public static void init() {
		table = new HashMap<String,Attributes>();
	}
	
	public static void enter(String id, Attributes entry) {
		table.put(id, entry);
	}
	
	public static Attributes lookup(String id) {
		return table.get(id);
	}
	
	public static String toStr() {
		StringBuilder tableString = new StringBuilder();
		
		table.forEach((key, value) -> {
			tableString.append(key + "\t" + value.toString() + "\n");
		});
		
		return tableString.toString();
	}
	
	public static int size() {
		return table.size();
	}

}
