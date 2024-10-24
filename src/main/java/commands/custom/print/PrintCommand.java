package commands.custom.print;

import java.util.Map;

import commands.Command;
import commands.enums.Checkables;
import commands.enums.Handleables;
import utils.TokenStringBuilder;

public class PrintCommand extends Command {
	
	public PrintCommand() {
		// TODO Auto-generated constructor stub
		super(null, Checkables.IS_NOT_EMPTY, Handleables.CLEAR);
	}
	
	public Command setPrint() {
		setExecutable(tokens -> {
			Map<String, Command> subordinates = getSubordinates();
			if (subordinates.containsKey(tokens.peek()))
				return;
			System.out.println(new TokenStringBuilder(tokens).joinAll());
			checkSubordinates = false;
		});
		return this;
	}
}
