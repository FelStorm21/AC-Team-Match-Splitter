package me.acteam.matchsplitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
//	private static boolean isPrinting;
	
	public static void main(String[] args) {
//		FileSplitter fileSplitter = new FileSplitter("C:\\Users\\bzall\\OneDrive\\Documents\\AC Team Matches\\2017-2018\\Match 2\\match 2.txt", "C:\\Users\\bzall\\OneDrive\\Documents\\AC Team Matches\\2017-2018\\Match 2\\Match 2 ANSWERS.txt", "C:\\Users\\bzall\\OneDrive\\Documents\\AC Team Matches\\2017-2018\\Match 2\\Match 2 QUESTIONS.txt");
//		fileSplitter.split(false);
//		fileSplitter.writeAnswers();
//		fileSplitter.writeQuestions();
		
		boolean isTesting = false;
		
		FileSplitter fs = new FileSplitter();
		DirectoryIterator dir = new DirectoryIterator("C:\\Users\\bzall\\OneDrive\\Documents\\AC Team Matches\\2014-15");
		if (args.length != 0) {
			dir.setRootPath(Paths.get(args[0]));
		}
		WordDocumentProcessor wdp = new WordDocumentProcessor();
		OutputTester tester = new OutputTester();
//		isPrinting = true;
		
//		fs.split(true, "C:\\Users\\bzall\\OneDrive\\Documents\\AC Team Matches\\2017-18\\temp\\Varsity - Match 1 temp.txt");
		if (isTesting) {
			try {
				wdp.convert("C:\\Users\\bzall\\OneDrive\\Documents\\AC Team Matches\\2014-15\\Match 1\\Varsity Week 1.docx");
				fs.setAnswersPath(wdp.getWordDocPath().toString().replaceAll(".docx", "") + " ANSWERS.txt");
				fs.setQuestionsPath(wdp.getWordDocPath().toString().replaceAll(".docx", "") + " QUESTIONS.txt");
				
				fs.setUpSkipStrings();
				fs.split(true, wdp.getTempPath().toString());
				tester.testAnswers(fs.getAnswers());
				
				fs.writeAnswers();
				fs.writeQuestions();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				System.out.println("----------------------OPERATION BEGUN----------------------");
				dir.prepare();
				dir.getPathList().stream().forEach(x -> {
					try {
						wdp.convert(x.toString());
						fs.setAnswersPath(x.toString().replaceAll(".docx", "") + " ANSWERS.txt");
						fs.setQuestionsPath(x.toString().replaceAll(".docx", "") + " QUESTIONS.txt");
						
						fs.setUpSkipStrings();
						fs.split(false, wdp.getTempPath().toString());
						tester.testAnswers(fs.getAnswers());

						fs.writeAnswers();
						fs.writeQuestions();
						System.out.println("\nDeleting temporary file: \"" + wdp.getTempPath().getFileName().toString() + "\"");
						String deleted = wdp.getTempPath().toFile().delete() ? "Deletion successful." : "Deletion unsuccessful.";
						System.out.println(deleted);
						System.out.println("\n");
	//					isPrinting = false;
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				System.out.println("Deleting temporary directory \""+ dir.getRootPath().resolve("temp").toString() + "\"");
				String deleteMessage = dir.getRootPath().resolve("temp").toFile().delete() ? "Deletion successful." : "Deletion unsuccessful.";
				System.out.println(deleteMessage);
				System.out.println(tester.getFailCount() + "/" + tester.getTestCount() + " preliminary tests failed.");
				System.out.println(tester.getTestCount() - tester.getFailCount() + "/" + tester.getTestCount() + " preliminary tests passed.");
				System.out.println("----------------------OPERATION COMPLETE----------------------");
				
	//			File test = new File("C:\\Users\\bzall\\OneDrive\\Documents\\AC Team Matches\\2017-18\\temp\\Varsity - Match 1 temp.txt");
	//			List<String> lines = FileUtils.readLines(test, StandardCharsets.UTF_8);
	//			lines.stream().forEach(x->System.out.println(x));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	//	wdr.read();
	}
}
