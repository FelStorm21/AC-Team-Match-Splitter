package me.acteam.matchsplitter;

import java.util.List;

public class OutputTester {
	
	private int upperCount = 0;
	private int lowerCount = 0;
	private int failCount = 0;
	private int testCount = 0;
	
	public void testAnswers(List<String> answers) {
		upperCount = 0;
		lowerCount = 0;
		
		answers.forEach(x -> {
			for (char i : x.toCharArray()) {
				if (Character.isUpperCase(i)) {
					upperCount++;
				} else if (Character.isLowerCase(i)) {
					lowerCount++;
				}
			}
		});
		
		if (upperCount > lowerCount) {
			System.out.println("Preliminary test passed.");
		} else {
			System.out.println("Preliminary test failed.");
			failCount++;
		}
		testCount++;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public int getTestCount() {
		return testCount;
	}

	public void setTestCount(int testCount) {
		this.testCount = testCount;
	}
}
