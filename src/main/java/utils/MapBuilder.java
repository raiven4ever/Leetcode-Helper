package utils;

import java.util.Map;

import commands.Command;

@Deprecated
/** this was just a bad idea from the start
 * 
 */
public class MapBuilder {
	
	private Map<String, Command> map;

	public MapBuilder(Map<String, Command> map) {
		this.map = map;
	}
	
	public MapBuilder add(String name, Command command) {
		map.put(name, command);
		return this;
	}
	
	public MapBuilder addAll(Map<String, Command> map) {
		this.map.putAll(map);
		return this;
	}

	public Map<String, Command> build() {
		return map;
	}
	
}
