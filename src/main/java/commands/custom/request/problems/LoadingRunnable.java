package commands.custom.request.problems;

import java.util.Arrays;

public class LoadingRunnable implements Runnable {
	
	int i = 0;
	public void run() {
		// TODO Auto-generated method stub
		char[] chars = {'—', '\\', '|', '/'};
		System.out.println(String.format("[%c] loading...", chars[i++]));
		if (i == chars.length)
			i = 0;
	}

}
