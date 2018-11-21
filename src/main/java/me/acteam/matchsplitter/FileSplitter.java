package me.acteam.matchsplitter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileSplitter {
	private String answersPath;
	private String questionsPath;
	
	private List<String> questions;
	private List<String> answers;
	
	private String year = "";
	private String matchNumber = "";
	private ArrayList<String> skipStrings;
	
	public FileSplitter() {
		answers = new ArrayList<String>(100);
		questions = new ArrayList<String>(100);
		skipStrings = new ArrayList<String>(500);	
	}
	
	public void setUpSkipStrings() {
		skipStrings.clear();
		
		year = Paths.get(answersPath).getParent().getParent().getFileName().toString();
//		skipStrings.add("High School – " + year + " Governor’s Cup Practice Questions");
	//	skipStrings.add("Governor’s Cup Practice Questions");
		skipStrings.add("End of First Half – Begin Three-Minute Inquiry Period");
	//	skipStrings.add("Round " + matchNumber + " - Second Half");
		skipStrings.add("End of Second Half – Begin Three-Minute Inquiry Period");
		skipStrings.add("Extra Questions");
		skipStrings.add("High School – " + year + " Governor’s Cup Practice Questions");
		for (int k = 1; k <= 15; k++) {
			for (int j = 1; j <= 15; j++) {
				skipStrings.add(year + " Governor’s Cup Practice Questions - Round " + j + " - Page " + k);
				skipStrings.add(year + " Governor’s Cup Practice Questions – High School Round " + j + " - Page " + k);
			}
		}
		for (int k = 1; k <= 20; k++) {
			skipStrings.add("Round " + k);
			skipStrings.add("Round " + k + " - Second Half");
			skipStrings.add("Round " + k + " – Use for Varsity");
		}
	}
	
	/**
	 * Splits the match file into questions and answers, each getting their own separate file.
	 * @param isPrinting True - print each file's questions and answers; False - don't print.
	 * @param inputPath - String representation of the Path to the match file that needs to be split into questions and answers.
	 */
	public void split(boolean isPrinting, String inputPath) {
		answers.clear();
		questions.clear();
		
		matchNumber = "";
		for (int i = 0; i < Paths.get(inputPath).getFileName().toString().toCharArray().length; i++) {
			char a = Paths.get(inputPath).getFileName().toString().toCharArray()[i];
			if (Character.isDigit(a)) {
				matchNumber += a;
			}
		}
		System.out.println("year = " + year + " and matchNumber = " + matchNumber);
			
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputPath));
			
			int numLines = 0;
			String currentLine = "";
			String skipped;
			boolean didSkipString;
			boolean didSkipBlank;
			int questionsNum = 0;
			int answersNum = 0;
			
			while ((currentLine = reader.readLine()) != null) {
				numLines++;
				skipped = "";
				didSkipBlank = false;
				didSkipString = false;
				if (currentLine.equals("") || isOnlyWhitespace(currentLine)) {
					skipped = "BLANK ";
					numLines--;
					didSkipBlank = true;
				}
//				if (currentLine.contains(skipStrings[0]) || currentLine.contains(skipStrings[1]) || currentLine.contains(skipStrings[2]) || 
//						currentLine.contains(skipStrings[3]) || currentLine.contains(skipStrings[4]) || currentLine.contains(skipStrings[5])) {
//					skipped = "SKIPPED ";
//					numLines--;
//				}
				for (int j = 0; j < skipStrings.size(); j++) {
					if (j <= 3) {
						if (currentLine.contains(skipStrings.get(j))) {
							skipped = "SKIPPED ";
							numLines--;
							didSkipString = true;
						} 
					} else {
						if (currentLine.equals(skipStrings.get(j))) {
							skipped = "SKIPPED ";
							numLines--;
							didSkipString = true;
						} 
					}
				}
				if (didSkipString && didSkipBlank) {
					numLines++;
				}
				
//				numLines++;
				if (isPrinting) {
					System.out.println(skipped + numLines + ": " + currentLine);
				}
				if (!didSkipBlank && !didSkipString) {
					if (numLines % 2 != 0) {
						questionsNum++;
						questions.add(questionsNum + ". " + currentLine);	
						questions.add("");
					} else {
						answersNum++;
						answers.add(answersNum + ". " + currentLine);
						answers.add("");
					}
				}
				
			}
			reader.close();
			if (isPrinting) {
				answers.forEach(x -> System.out.println(x));
				questions.forEach(x -> System.out.println(x));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Split complete.");
	}
	
//	/**
//	 * Splits the match file into questions and answers, each getting their own separate file.
//	 * @param isPrinting True - print each file's questions and answers; False - don't print.
//	 * @param inputPath - String representation of the Path to the match file that needs to be split into questions and answers.
//	 */
//	public void split(boolean isPrinting, String inputPath) {
//		answers.clear();
//		questions.clear();
//		
//		year = Paths.get(inputPath).getParent().getParent().getFileName().toString();
//		matchNumber = "";
//		for (int i = 0; i < Paths.get(inputPath).getFileName().toString().toCharArray().length; i++) {
//			char a = Paths.get(inputPath).getFileName().toString().toCharArray()[i];
//			if (Character.isDigit(a)) {
//				matchNumber += a;
//			}
//		}
//
////		skipStrings[0] = "Round " + matchNumber;
//////		skipStrings[1] = "High School – " + year + " Governor’s Cup Practice Questions";
////		skipStrings[1] = "Governor’s Cup Practice Questions";
////		skipStrings[2] = "End of First Half – Begin Three-Minute Inquiry Period";
////		skipStrings[3] = "Round " + matchNumber + " - Second Half";
////		skipStrings[4] = "End of Second Half – Begin Three-Minute Inquiry Period";
////		skipStrings[5] = "Extra Questions";
////		skipStrings[6] = "High School – " + year + " Governor’s Cup Practice Questions";
//		
////		skipStrings[0] = "Round " + matchNumber;
////	//	skipStrings[1] = "High School – " + year + " Governor’s Cup Practice Questions";
////	//	skipStrings[1] = "Governor’s Cup Practice Questions";
////		skipStrings[1] = "End of First Half – Begin Three-Minute Inquiry Period";
////	//	skipStrings[2] = "Round " + matchNumber + " - Second Half";
////		skipStrings[2] = "End of Second Half – Begin Three-Minute Inquiry Period";
////		skipStrings[3] = "Extra Questions";
////		skipStrings[4] = "High School – " + year + " Governor’s Cup Practice Questions";
////		for (int k = 1; k <= 12; k++) {
////			skipStrings[4+k] = year + " Governor’s Cup Practice Questions - Round " + matchNumber + " - Page " + k;
////		}
////		skipStrings[6] = "Round " + matchNumber + " - Second Half";
//		System.out.println("year = " + year + "\n" + "matchNumber = " + matchNumber);
//		
//		try {
//			BufferedReader reader = new BufferedReader(new FileReader(inputPath));
//			
//			int questionsNum = 0;
//			int answersNum = 0;
//			int numLines = 0;
//			String currentLine = "";
//			boolean didSkipBlank, didSkipString;
//			
//			while ((currentLine = reader.readLine()) != null) {
//				numLines++;
//				didSkipBlank = false;
//				didSkipString = false;
//				if (currentLine.equals("")) {
//					numLines--;
//					didSkipBlank = true;
//					continue;
//				}
//
//				for (int j = 0; j < skipStrings.length; j++) {
//					if (currentLine.equals(skipStrings[j])) {
//						numLines--;
//						didSkipString = true;
//						continue;
//					}
//				}
//				if (didSkipString && didSkipBlank) {
//					numLines++;
//				}
//				
////				numLines++;
//////				String[] skipStringsNoWhitespace = new String[skipStrings.length];
////				for (int j = 0; j < skipStrings.length; j++) {
////					if (currentLine.equals(skipStrings[j])) {
////						numLines--;
////						continue;
////					}
////				}
////				for (int j = 0; j < skipStrings.length; j++) {
////					skipStringsNoWhitespace[j] = skipStrings[j].replaceAll("\\s+", "");
////					if (currentLine.contains(skipStringsNoWhitespace[j])) {
////						numLines--;
////						continue;
////					}
////				}
////				if (currentLine.equals("") || currentLine.contains(skipStrings[0]) || currentLine.contains(skipStrings[1]) || currentLine.contains(skipStrings[2]) || 
////						currentLine.contains(skipStrings[3]) || currentLine.contains(skipStrings[4]) || currentLine.contains(skipStrings[5])) {
////					numLines--;
////					continue;
////				}
//				if (!didSkipString || !didSkipBlank) {
//					if (numLines % 2 != 0) {
//						questionsNum++;
//						questions.add(questionsNum + ". " + currentLine);
//						questions.add("");					
//					} else {
//						answersNum++;
//						answers.add(answersNum + ". " + currentLine);
//						answers.add("");
//					}
//				}
//			}
//			reader.close();
//			
//			if (isPrinting) {
//				answers.forEach(x -> System.out.println(x));
//				questions.forEach(x -> System.out.println(x));
//			}
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.print("Split complete. ");
//	}
	
	/**
	 * Write the answers obtained through the {@link #split(boolean, String) split} method to their own file. 
	 */
	public void writeAnswers() {
		File answersFile = new File(answersPath);
		try {
			FileUtils.writeLines(answersFile, answers);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("Answers written. ");
	}
	
	/**
	 * Write the questions obtained through the {@link #split(boolean, String) split} method to their own file.
	 */
	public void writeQuestions() {
		File questionsFile = new File(questionsPath);
		try {
			FileUtils.writeLines(questionsFile, questions);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("Questions written. ");
	}
	
	public boolean isOnlyWhitespace(String s) {
		for (char c : s.toCharArray()) {
			if (!Character.isWhitespace(c)) {
				return false;
			}
		}
		return true;
	}
	
	public void printAnswers() {
		answers.forEach(x -> System.out.println(x));
	}
	
	public void printQuestions() {
		questions.forEach(x -> System.out.println(x));
	}
	
	public String getAnswersPath() {
		return answersPath;
	}

	public void setAnswersPath(String answersPath) {
		this.answersPath = answersPath;
	}

	public String getQuestionsPath() {
		return questionsPath;
	}

	public void setQuestionsPath(String questionsPath) {
		this.questionsPath = questionsPath;
	}

	public List<String> getQuestions() {
		return questions;
	}

	public void setQuestions(List<String> questions) {
		this.questions = questions;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}
	
}
