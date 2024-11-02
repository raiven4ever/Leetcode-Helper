package commands.custom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import commands.Command;
import commands.custom.print.util.ProblemsTable;
import commands.enums.Checkables;
import commands.enums.Handleables;
import leetcodehelper.Main;
import utils.objects.Problem;
import utils.objects.Problem.Tag;

public class FIlterCommand extends Command {
	
	private HashMap<String, Command> pseudosubordinates;
	private Set<Tag> excludedSet;
	private Set<Tag> includedSet;
	
	public FIlterCommand() {
		// TODO Auto-generated constructor stub
		super(null, Checkables.IS_NOT_EMPTY, Handleables.CONSUME);
	}

	public FIlterCommand setFilter() {
		// TODO Auto-generated method stub
		setExecutable(tokens -> {
			//setup;
			pseudosubordinates = new HashMap<String, Command>();
			excludedSet = new HashSet<Tag>();
			includedSet = new HashSet<Tag>();
			buildSubordinates();
			Set<Tag> tags = Main.getTags();
			tags.stream().forEach(tag -> excludedSet.add(tag));
			
			//return conditions
			if (tags.isEmpty())
				return;
			if (getSubordinates().containsKey(tokens.peek()))//if direct descendant is called
				return;
			
			//handle the unofficial subordinates
			while (true) {
				String slug = tokens.poll();
				if (slug == null)
					break;
				Command command = pseudosubordinates.get(slug);
				if (command == null) {
					System.out.println("no such tag " + slug + "exists");
				}
				command.run(tokens);
			}
			
		});
		return this;
	}

	private void buildSubordinates() {
		Main.getTags().stream().forEach(tag -> {
			String slug = tag.getSlug();
			Command excludeCommand = new Command(tokens -> {
				excludedSet.add(tag);
				action(tokens);
			}, null, Handleables.PRESERVE);
			Command includeCommand = new Command(tokens -> {
				if (excludedSet.contains(tag))
					excludedSet.remove(tag);
				includedSet.add(tag);
				action(tokens);
			}, null, Handleables.PRESERVE);
			Command ambiguousCommand = new Command(tokens -> {
				excludedSet.remove(tag);
				action(tokens);
			}, null, Handleables.PRESERVE);
			
			pseudosubordinates.put("-"+slug, excludeCommand);
			pseudosubordinates.put("+"+slug, includeCommand);
			pseudosubordinates.put("?"+slug, ambiguousCommand);
		});;
		//the only direct subordinate of this command
		addSubordinate("help", new Command(tokens -> {
			Main.getTags().stream().sorted().forEach(tag -> System.out.println(tag.getSlug()));
		}, Checkables.IS_EMPTY, Handleables.CONSUME));
	}

	private void action(Queue<String> tokens) {
		// TODO Auto-generated method stub
		if (!tokens.isEmpty())
			return;
		this.checkSubordinates = false;
		List<Problem> list = Main.getProblemsList()
		.stream()
		.filter(problem -> !problem.getTopicTags()
				.stream()
				.anyMatch(tag -> excludedSet.contains(tag)))
		.filter(problem -> problem.getTopicTags().containsAll(includedSet))
		.toList();
		ProblemsTable table = new ProblemsTable(list);
		System.out.println(table.buildTable());
	}
}
