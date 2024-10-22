package commands;

import java.util.Map;

import commands.enums.Checkables;
import commands.enums.Handleables;

public class MainCommand extends Command {

	public MainCommand() {
		super(buildExecutable(), buildCheckable(), Handleables.CONSUME);
	}

	public static Executable buildExecutable() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Checkable buildCheckable() {
		// TODO Auto-generated method stub
		return Checkables.IS_NOT_EMPTY;
	}

}
