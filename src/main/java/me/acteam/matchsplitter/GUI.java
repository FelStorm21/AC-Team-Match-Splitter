package me.acteam.matchsplitter;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI {
	
	private JFrame frame;
	private JFileChooser fileChooser;
	private FileNameExtensionFilter filter;
	private File selectedFile;
	private boolean didSelect;
	
	public GUI() {
		try {
			SwingUtilities.invokeAndWait(this::initGui);
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initGui() {
		System.out.println("Initializing GUI...");
		
//		frame = new JFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			selectedFile = fileChooser.getSelectedFile();
			System.out.println("You chose to this folder: " + selectedFile.getPath());
			didSelect = true;
		} else {
			System.out.println("You cancelled the operation.");
			didSelect = false;
		}
		
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	public boolean didSelect() {
		return didSelect;
	}

	public void setDidSelect(boolean didSelect) {
		this.didSelect = didSelect;
	}
	
}
