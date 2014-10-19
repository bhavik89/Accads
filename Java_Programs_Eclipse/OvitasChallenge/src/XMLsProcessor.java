import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XMLsProcessor {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		//Get Directory for XML files
		File dir = new File(
				".\\OvitasInterviewExercisePackage\\shakespeare_plays_xml\\");
		File[] dirFiles = dir.listFiles();
		
		//Check if the result directory exists
		File result_dir = new File(
				".\\OvitasInterviewExercisePackage\\Result\\");
		
		//If Results directory doesn't exist, create it
		if (!result_dir.exists())
			result_dir.mkdir();
		
		//New string builder to store the report data
		StringBuilder report = new StringBuilder();
		report.append("Original Filename" + "\t" + "New Filename" + "\t"
				+ "Replacements\n\n");
		
		//Builder for JDOM object for parsing XML files in our case
		SAXBuilder builder = new SAXBuilder();

		//Iterate through each file in the directory
		try {
			
			System.out.println("Processing XMLs...\n");
			int XMLFileCount = 0;
			for (File f : dirFiles) {
			
			// Counter for word "virtue"			
			int virtueCounter = 0;
			
			//Extract the file extension
			int dotLastIndex = f.getName().lastIndexOf('.');
			String fileExtension = null;
			if (dotLastIndex >= 0) {
				fileExtension = f.getName().substring(dotLastIndex + 1);
			}
			
			// If the file type is XML, parse it, else ignore other file types
			if (fileExtension.equalsIgnoreCase("xml")) {

					XMLFileCount++;

					Document document = (Document) builder.build(f);
					
					//Get the root node of the XML
					Element rootNode = document.getRootElement();
					
					// Get the child with name "ACT" from children of root
					List<Element> actList = rootNode.getChildren("ACT");

					//Iterate through every child in ACT to get "LINE" node
					for (Element act : actList) {
						List<Element> sceneList = act.getChildren("SCENE");
						for (Element scene : sceneList) {
							List<Element> speechList = scene.getChildren("SPEECH");
							for (Element speech : speechList) {
								List<Element> lines = speech.getChildren();
								for (Element line : lines) {
									// Search if "LINE" tag contains word "Virtue"
									// If "virtue" exists, replace it and increment the word counter
									if (line.getName() == "LINE") {
										if (line.getValue().toLowerCase()
												.matches("(?s).*\\bvirtue.*")) {
											virtueCounter++;
											String replace_str = line.getValue();
											// If word "virtue" is found, replace it with "MY_VIRTUE_REPLACEMENT"
											replace_str = replace_str.replaceAll(
													"(?i)virtue?[a-z',;\"\\d]{1,2}",
													"MY_VIRTUE_REPLACEMENT");
											line.setText(replace_str);
											
										}
									}

								}
							}
						}
						

						// Get the children of "ACT"
						List<Element> epilogueList = act.getChildren("EPILOGUE");
						for (Element epilogue : epilogueList) {
							List<Element> speechList = epilogue
									.getChildren("SPEECH");
							for (Element speech : speechList) {
								List<Element> lines = speech.getChildren();
								
								for (Element line : lines) {
									if (line.getName() == "LINE") {
										// Search if "LINE" tag contains word "Virtue"
										// If "virtue" exists, replace it and increment the word counter
										if (line.getValue().toLowerCase()
												.matches("(?s).*\\bvirtue.*")) {
											virtueCounter++;
											String replace_str = line.getValue();
											replace_str = replace_str.replaceAll(
													"\\bvirtue\\b.*",
													"MY_STRING");
											
											line.setText(replace_str);
										}
									}
								}
							}
						}
					}
					
					// New XML outputter to generate new XML file
					XMLOutputter resultXML = new XMLOutputter();
					resultXML.setFormat(Format.getPrettyFormat());
					
					// Create new updated XML file
					resultXML.output(document, new FileWriter(
							"OvitasInterviewExercisePackage\\Result\\result_"
									+ f.getName()));
					
					// Add file name and word counter to the report string
					report.append(f.getName() + "\t" + "result_" + f.getName()
							+ "\t" + virtueCounter + "\n");
				
			}
		  } //End of files loop
		
		//System.out.println(report);
		// Print success message on console	
		System.out.println("Successfully processed " + XMLFileCount + " XML files");
		System.out.println("Created copies in Results directory...\n");	
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
		
		

		try {
			
			// Write report to the report file
			PrintWriter out = new PrintWriter(
					"OvitasInterviewExercisePackage\\Result\\Report.xls");
			out.print(report);
			out.close();
			
			System.out.println("Report generated in OvitasInterviewExercisePackage\\Result\\Report.xls.");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
