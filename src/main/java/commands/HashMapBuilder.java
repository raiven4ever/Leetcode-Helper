package commands;

import java.util.HashMap;

public class HashMapBuilder {
	
	private HashMap<String, Command> hashMap;

	public HashMapBuilder() {
		// TODO Auto-generated constructor stub
		hashMap = new HashMap<String, Command>();
	}
	
	public HashMapBuilder add(String name, Command command) {
		hashMap.put(name, command);
		return this;
	}
	
	public HashMap<String, Command> build(){
		return hashMap;
	}
}
