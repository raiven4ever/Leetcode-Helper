package commands;

import java.util.Queue;

/**basic interface for all executables
 */
public interface Executable {
	
	public void execute(Queue<String> tokens);

}
