package commands.custom;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

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
					continue;
				}
				command.run(tokens);
			}
			
			List<Problem> list = Main.getProblemsList()
			.stream()
			.filter(problem -> !problem.getTopicTags()
					.stream()
					.anyMatch(tag -> excludedSet.contains(tag)))
			.filter(problem -> problem.getTopicTags().containsAll(includedSet))
			.toList();
			ProblemsTable table = new ProblemsTable(list);
			System.out.println(table.buildTable());
		});
		return this;
	}

	private void buildSubordinates() {
		//add unofficial subordinates
		Set<Tag> tags = Main.getTags();
		tags.stream().forEach(tag -> {
			String slug = tag.getSlug();
			
			pseudosubordinates.put("-"+slug, new Command(tokens -> {
				excludedSet.add(tag);
			}, null, Handleables.PRESERVE));
			
			pseudosubordinates.put("+"+slug, new Command(tokens -> {
				if (excludedSet.contains(tag))
					excludedSet.remove(tag);
				includedSet.add(tag);
			}, null, Handleables.PRESERVE));
			
			pseudosubordinates.put("?"+slug, new Command(tokens -> {
				excludedSet.remove(tag);
			}, null, Handleables.PRESERVE));
		});;
		
		pseudosubordinates.put("-all", new Command(tokens -> {
			excludedSet.addAll(tags);
		}, null, Handleables.PRESERVE));
		
		pseudosubordinates.put("+all", new Command(tokens -> {
			excludedSet.removeAll(tags);
			includedSet.addAll(tags);
		}, null, Handleables.PRESERVE));
		
		pseudosubordinates.put("?all", new Command(tokens -> {
			excludedSet.removeAll(tags);
		}, null, Handleables.PRESERVE));
		
		//the only direct subordinate of this command
		addSubordinate("help", new Command(tokens -> {
			Main.getTags().stream().sorted().forEach(tag -> System.out.println(tag.getSlug()));
		}, Checkables.IS_EMPTY, Handleables.CONSUME));
	}

}
