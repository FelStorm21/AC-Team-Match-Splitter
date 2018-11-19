package me.acteam.matchsplitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordDocumentProcessor {	
	
	Path tempPath;
	Path wordDocPath;
	
	/** 
	 * Returns the text of a .docx Word document.
	 * @return String
	 * @throws IOException
	 */
	public String read(String filePath) throws IOException {
		XWPFDocument docx = new XWPFDocument(new FileInputStream(filePath));
		XWPFWordExtractor ex = new XWPFWordExtractor(docx);
		String result = ex.getText();
		ex.close();
		return result;
	}
	
	/** 
	 * Reads .docx text information into a temporary .txt file to be used to extract questions and answers. 
	 * Temporary files are located within the root/temp directory, where root is the directory containing all the subdirectories for each match. 
	 * @return File
	 * @throws IOException
	 */
	public void convert(String filePath) throws IOException {
		wordDocPath = Paths.get(filePath);
//		Path currentPath = Paths.get(filePath);
		String newFileName = wordDocPath.getFileName().toString().replaceAll(".docx", "") + " temp.txt";
		tempPath = wordDocPath.getParent().getParent().resolve("temp").resolve(newFileName);
//		System.out.println(tempPath);
		File tempTextFile = tempPath.toFile();
		FileUtils.writeStringToFile(tempTextFile, read(filePath), StandardCharsets.UTF_8);
		System.out.println("\"" + newFileName + "\" succesfully created.");
	}

	public Path getTempPath() {
		return tempPath;
	}

	public void setTempPath(Path tempPath) {
		this.tempPath = tempPath;
	}

	public Path getWordDocPath() {
		return wordDocPath;
	}

	public void setWordDocPath(Path wordDocPath) {
		this.wordDocPath = wordDocPath;
	}
	
}
