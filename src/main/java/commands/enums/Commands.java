package commands.enums;

import commands.Command;

public enum Commands{
	PARENT(new Command(null, Checkables.IS_NOT_EMPTY, Handleables.CONSUME));

	private Command command;

	Commands(Command command) {
		this.command = command;
	}

	public Command get() {
		return command;
	}
	
}
