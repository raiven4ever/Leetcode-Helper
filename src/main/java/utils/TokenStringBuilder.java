package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TokenStringBuilder {
	

	private List<String> tokens;

	public TokenStringBuilder(Queue<String> queue) {
		tokens = new ArrayList<String>(queue);
	}
	
	/** @param start start inclusive
	 * @param end end exclusive
	 * 
	 */
	public String join (int start, int end) {
		List<String> subList = tokens.subList(start, end);
		StringBuilder builder = new StringBuilder();
		subList.forEach(token -> builder.append(token+" "));
		return builder.toString();
	}
	
	/** @param start start inclusive
	 * 
	 */
	public String joinFromStart (int start) {
		return join(start, tokens.size());
	}
	
	/** @param end end exclusive
	 * 
	 */
	public String joinFromEnd (int end) {
		return join(0, end);
	}
	
	/** no params since this method joins tokens from start to end
	 * 
	 */
	public String joinAll() {
		return join(0, tokens.size());
	}
}
