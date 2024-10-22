package commands;

import java.util.Queue;

public interface Handleable {

	String handle(Queue<String> tokens);

}
