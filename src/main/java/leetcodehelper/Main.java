package leetcodehelper;

import java.time.Duration;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import commands.Command;
import commands.HashMapBuilder;
import commands.MainCommand;
import commands.enums.Checkables;
import commands.enums.Handleables;

public class Main {
	public static boolean isRunning = true;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Command main = new Command(new MainCommand(), new HashMapBuilder()
				.add("print", new Command(tokens -> {
					StringBuilder builder = new StringBuilder();
					tokens.forEach(token -> builder.append(token + " "));
					System.out.println(builder.toString());
				}, Checkables.IS_NOT_EMPTY, Handleables.CONSUME))
				.add("exit", new Command(tokens -> isRunning = false, Checkables.IS_EMPTY, Handleables.CONSUME))
				.build());
		Scanner scanner = new Scanner(System.in);
		while (isRunning) {
			System.out.print("leetcode helper: ");
			main.run(new ArrayDeque<String>(List.of(scanner.nextLine().split(" "))));
		}
		scanner.close();
	}

}
