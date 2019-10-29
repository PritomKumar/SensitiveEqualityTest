import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FileOpener2 extends JFrame {
	

	private JPanel contentPane;
	
	private final JFileChooser openFileChooser;
	private final JFileChooser openFolderChooser;
	private final JFileChooser chooseFolder;
	private String javaFileName ;
	private String errorLog ;
	private JTextArea txtErrorLog;
	private JScrollPane scroll;
	private int returnValue = -1;;
	private boolean isProject = true;
	private boolean fileClickTest = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileOpener2 frame = new FileOpener2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FileOpener2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 880, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		openFileChooser = new JFileChooser();
		openFileChooser.setCurrentDirectory(new File("G:\\pritom\\SpamBase\\src\\testing"));
		openFileChooser.setFileFilter(new FileNameExtensionFilter("JAVA Class", "java"));
		
		openFolderChooser = new JFileChooser();
		openFolderChooser.setCurrentDirectory(new File("G:\\pritom\\SpamBase"));
		openFolderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		chooseFolder = new JFileChooser();
		chooseFolder.setCurrentDirectory(new File("G:\\TestProjectError"));
		chooseFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		
		JLabel messageBox = new JLabel("");
		messageBox.setForeground(Color.BLACK);
		messageBox.setFont(new Font("Imprint MT Shadow", Font.ITALIC, 18));
		messageBox.setBounds(301, 13, 511, 41);
		contentPane.add(messageBox);
		
		JButton btnSelectProject = new JButton("Select Project....");
		btnSelectProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtErrorLog.setText("");
				messageBox.setForeground(Color.MAGENTA);
				messageBox.setText("Choose a Project... ");
				returnValue = openFolderChooser.showOpenDialog(FileOpener2.this);
				
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					javaFileName = openFolderChooser.getSelectedFile().toString();
					System.out.println("Folder Name = " + javaFileName);
					isProject = true;
					fileClickTest = true;
					
					messageBox.setForeground(Color.GREEN);
					messageBox.setText("JAVA folder Successfully Loaded !!! ");
				}
				else {
					messageBox.setForeground(Color.RED);
					messageBox.setText("No folder Chosen!!! ");
				}
			}
		});
		btnSelectProject.setBounds(12, 13, 130, 41);
		contentPane.add(btnSelectProject);
		
		
		JButton SelectFileButton = new JButton("Select File.....");
		SelectFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtErrorLog.setText("");
				messageBox.setForeground(Color.MAGENTA);
				messageBox.setText("Choose a File... ");
				returnValue = openFileChooser.showOpenDialog(FileOpener2.this);
				
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					javaFileName = openFileChooser.getSelectedFile().toString();
					System.out.println("File Name = " + javaFileName);
					isProject = false;
					fileClickTest = true;
					messageBox.setForeground(Color.GREEN);
					messageBox.setText("JAVA file Successfully Loaded !!! ");
				}
				else {
					messageBox.setForeground(Color.RED);
					messageBox.setText("No File Chosen!!! ");
				}
				
			}
		});
		SelectFileButton.setBounds(154, 13, 130, 41);
		contentPane.add(SelectFileButton);
		
		
		
		txtErrorLog = new JTextArea();
		
		//contentPane.add(txtErrorLog);
		//txtErrorLog.add(scroll);
		scroll = new JScrollPane(txtErrorLog , JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED ,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		txtErrorLog.setFont(new Font("Lucida Console", Font.BOLD, 16));
		scroll.setBounds(12, 69, 752, 406);
		scroll.setVisible(true);
		contentPane.add(scroll);
		//scroll.setViewportView(txtErrorLog);
		
		JButton btnShowResult = new JButton("Show Result");
		btnShowResult.setBounds(230, 522, 149, 37);
		contentPane.add(btnShowResult);
		
		JButton btnCheckForErrors = new JButton("Check For Errors");
		btnCheckForErrors.setBounds(38, 522, 165, 37);
		contentPane.add(btnCheckForErrors);
		
		JButton btnSaveResultAs = new JButton("Save Result As Text File");
		btnSaveResultAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(fileClickTest == true) {
					messageBox.setForeground(Color.MAGENTA);
					messageBox.setText("Select / Create  a Folder ... ");
					returnValue = chooseFolder.showOpenDialog(FileOpener2.this);
					
					if(returnValue == JFileChooser.APPROVE_OPTION) {
						javaFileName = chooseFolder.getSelectedFile().toString();
						System.out.println("File Name = " + javaFileName);
						messageBox.setForeground(Color.GREEN);
						messageBox.setText("Error Log successfully saved in " + javaFileName + " .");
						FileWriter fw;
						try {
							String fileName = javaFileName + "\\ErrorLog.txt";
							fw = new FileWriter(fileName);
							fw.write(errorLog);    
							fw.close();  
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}    
					}
					else {
						messageBox.setForeground(Color.RED);
						messageBox.setText("No Folder Chosen!!! ");
					}
				
				}
				
				
				else {
					messageBox.setForeground(Color.MAGENTA);
					messageBox.setText("Choose a file First !!! ");			
				}
			}
				
		});
		btnSaveResultAs.setBounds(410, 522, 189, 37);
		contentPane.add(btnSaveResultAs);
		
		btnCheckForErrors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(returnValue != -1) {
					
					SensititiveEqualitySmell se = new SensititiveEqualitySmell();
					try {
						if(isProject == true) {
							
							File folder = new File(javaFileName);
							se.chooseFolder(folder);
							errorLog = se.getErrorLog();
							se.setErrorLog("");
							int errorCount = se.getErrorCount();
							se.setErrorCount(0);
							txtErrorLog.setText("");
							messageBox.setForeground(Color.RED);
							messageBox.setText("There are " + errorCount + " errors in project");
						}
						else {
							se.chooseFile(javaFileName);
							errorLog = se.getErrorLog();
							se.setErrorLog("");
							int errorCount = se.getErrorCount();
							messageBox.setForeground(Color.RED);
							messageBox.setText("There are " + errorCount + " errors in file");
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					messageBox.setForeground(Color.MAGENTA);
					messageBox.setText("Choose a file First !!! ");			
				}
				
			}
		});
		
		btnShowResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(returnValue != -1) {
					System.out.println(errorLog);
					txtErrorLog.setText(errorLog);
				}
				else {
					messageBox.setForeground(Color.MAGENTA);
					messageBox.setText("Choose a file First !!! ");			
				}
			}
		});
		
	}
}
