package commands.enums;

import java.util.Queue;

import commands.Handleable;

public enum Handleables implements Handleable {
	CONSUME{
		@Override
		public String handle(Queue<String> tokens) {
			// TODO Auto-generated method stub
			return tokens.poll();
		}
	},
	PRESERVE{
		@Override
		public String handle(Queue<String> tokens) {
			// TODO Auto-generated method stub
			return tokens.peek();
		}
	},
	CLEAR{
		@Override
		public String handle(Queue<String> tokens) {
			// TODO Auto-generated method stub
			String poll = tokens.poll();
			tokens.clear();
			return poll;
		}
	}
}
