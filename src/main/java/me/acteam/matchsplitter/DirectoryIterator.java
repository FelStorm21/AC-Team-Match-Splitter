package me.acteam.matchsplitter;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DirectoryIterator {
	
	private Path rootPath;
	private List<Path> pathList;
	
	public DirectoryIterator(String rootPath) {
		this.rootPath = Paths.get(rootPath);
		pathList = new ArrayList<Path>();
	}
	
	/** 
	 * Prepares an ArrayList<> of Path objects that represent all the desired files contained in this year-root directory. 
	 * It iterates through all subdirectories as well. Excludes any directories named "temp".
	 * @throws IOException
	 */
	public void prepare() throws IOException {
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(rootPath)) {
			for (Path filepath : stream) {
				if (!filepath.endsWith("temp")) {
					if (filepath.toFile().isDirectory()) {
						File[] subDir = filepath.toFile().listFiles();
						for (File file : subDir) {
							if (!file.toString().contains("ANSWERS") && !file.toString().contains("QUESTIONS")) {
								pathList.add(file.toPath());
							}
						}
					} else {
						pathList.add(filepath);
					}
				}	
			}
		} catch(DirectoryIteratorException e) {
			throw e.getCause();
		}
	}
	
	
	/**
	 * Prints the list of Path objects representing all of the .docx files to be iterated found within the year-root directory.
	 */
	public void printPathList() {
		for (Path path : pathList) {
			System.out.println(path.toString());
		}
	}

	public List<Path> getPathList() {
		return pathList;
	}

	public void setPathList(List<Path> pathList) {
		this.pathList = pathList;
	}

	public Path getRootPath() {
		return rootPath;
	}

	public void setRootPath(Path rootPath) {
		this.rootPath = rootPath;
	}
	
}
