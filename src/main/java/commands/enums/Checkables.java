package commands.enums;

import java.util.List;
import java.util.Queue;

import commands.Checkable;

public enum Checkables implements Checkable {
	IS_EMPTY{
		@Override
		public boolean check(Queue<String> tokens) {
			// TODO Auto-generated method stub
			boolean empty = tokens.isEmpty();
			if (!empty)
				System.out.println("argument must be empty");
			return empty;
		}
	},
	IS_NOT_EMPTY{
		@Override
		public boolean check(Queue<String> tokens) {
			// TODO Auto-generated method stub
			boolean b = tokens != null && !tokens.isEmpty();
			if (!b)
				System.out.println("argument must not be empty");
			return b;
		}
	},
	IS_INTEGER{
		@Override
		public boolean check(Queue<String> tokens) {
			// TODO Auto-generated method stub
			try {
				Integer valueOf = Integer.valueOf(tokens.peek());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				System.out.println("argument must be a number");
				return false;
			}
			return true;
		}
		
	};
	
	/*	#|#########################################################################################|#
	 *  #|######################################## METHODS ########################################|#
	 *  #|#########################################################################################|#
	 * */
	
	public static Checkable LESS_THAN(int i) {
		return tokens -> tokens.size() < i;
	}
	
	public static Checkable EQUAL(int i) {
		// TODO Auto-generated method stub
		return tokens -> {
			int size = tokens.size();
			boolean b = size == i;
			if (!b)
				System.out.println("argument count must be equal of: " + i);
			return b;
		};
	}
	
	/*	#|#########################################################################################|#
	 *  #|###################################### LOGIC GATES ######################################|#
	 *  #|#########################################################################################|#
	 * */
	public static Checkable NOT(Checkable checkable) {
		return tokens -> {
			boolean b = !checkable.check(tokens);
			if (!b)
				System.out.println("argument must NOT satisfy a criteria");
			return b;
		};
	}
	
	public static Checkable AND(Checkable... checkables) {
		return tokens -> {
			boolean allMatch = List.of(checkables).stream().allMatch(checkable -> checkable.check(tokens));
			if (!allMatch)
				System.out.println("argument(s) must satisfy all criteria");
			return allMatch;
		};
	}
	
	public static Checkable OR(Checkable... checkables) {
		return tokens -> {
			boolean anyMatch = List.of(checkables).stream().anyMatch(checkable -> checkable.check(tokens));
			if (!anyMatch)
				System.out.println("argument(s) must satisfy any of the criteria");
			return anyMatch;
		};
	}
	
	public static Checkable NAND(Checkable... checkables) {
		return tokens -> {
			boolean b = !AND(checkables).check(tokens);
			if (!b)
				System.out.println("argument(s) must NOT satisfy all of the criteria");
			return b;
		};
	}
	
	public static Checkable NOR(Checkable... checkables) {
		return tokens -> {
			boolean b = !OR(checkables).check(tokens);
			if (!b)
				System.out.println("argument(s) must NOT satisfy any of the criteria");
			return b;
		};
	}
	
	public static Checkable XOR(Checkable... checkables) {
		return tokens -> {
			boolean b = List.of(checkables).stream().filter(checkable -> checkable.check(tokens)).count() % 2 == 1;
			if (!b)
				System.out.println("number of criteria returning true for the given argument(s) must be odd");
			return b;
		};
	}
	
	public static Checkable XNOR(Checkable... checkables) {
		return tokens -> {
			boolean b = !XOR(checkables).check(tokens);
			if (!b)
				System.out.println("number of criteria returning true for the given argument(s) must be even");
			return b;
		};
	}

	@Override
	public boolean check(Queue<String> tokens) {
		// TODO Auto-generated method stub
		return false;
	}
}
