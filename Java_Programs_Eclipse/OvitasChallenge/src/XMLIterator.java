import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

import org.jdom.*;
import org.jdom.input.*;

import com.sun.org.apache.xpath.internal.operations.Equals;

public class XMLIterator {

	public static void main(String[] args) {
		
		File dir = new File("C:\\OvitasInterviewExercisePackage\\shakespeare_plays_xml\\");
		
		File[] dirFiles = dir.listFiles();

//		for (File file : dirFiles) {
//		    if (file.isFile()) {
//		        System.out.println(file.getName());
//		    }
//		}
		
		SAXBuilder builder = new SAXBuilder();
		//File f = dirFiles[0];
		for(File f: dirFiles){
			
		int virtueCounter = 0;
		int dotLastIndex = f.getName().lastIndexOf('.');
		String fileExtension = null;
		if (dotLastIndex >= 0) {
		    fileExtension = f.getName().substring(dotLastIndex + 1);
		}
		System.out.println(f.getName());
		if(fileExtension.equalsIgnoreCase("xml")){
		
		try {
			 
			Document document = (Document) builder.build(f);
			Element rootNode = document.getRootElement();
			//System.out.println(rootNode.getChildren());
			List<Element> actList = rootNode.getChildren("ACT");
						
			
			//System.out.println(list.size());
			
			for(Element e: actList){
				List<Element> scene =  e.getChildren("SCENE");
				for(Element s: scene){
					List<Element> speech =  s.getChildren("SPEECH");
					for(Element sp:speech){						
							List<Element> tst = sp.getChildren();
							for(Element t:tst){
								if(t.getName() == "LINE"){
									if(t.getValue().toLowerCase().matches("(?s).*\\bvirtue.*")){
										virtueCounter++;
										String sr = t.getValue();
										//System.out.println(sr);
												sr = sr.replaceAll("(?i)virtue?[a-z',;\"\\d]{1,2}", "MY_STRING");
										//System.out.println(sr);
									}
								}
									
							}
					}
				}				
				//System.out.println("Scene:" + scene.size() );
				
				List<Element> epilogueList =  e.getChildren("EPILOGUE");
				 for(Element epilogue: epilogueList){
					 List<Element> speechList = epilogue.getChildren("SPEECH");
					 for(Element sp:speechList){						
							List<Element> tst = sp.getChildren();
							//System.out.println(tst);
							for(Element t:tst){
								if(t.getName() == "LINE"){
									if(t.getValue().toLowerCase().matches("(?s).*\\bvirtue.*")){
										virtueCounter++;
										String sr = t.getValue();
										System.out.println(sr);
												sr = sr.replaceAll("\\bvirtue\\b.*", "MY_STRING");
										//System.out.println(sr);
									}
								}									
							}
					}
				 }
			}
			
			System.out.println(virtueCounter);
	 
			for (int i = 0; i < actList.size(); i++) {
	 
			   Element node = (Element) actList.get(i);
	 
			   //System.out.println("Line: " + node.getChildText("LINE"));			   
	 
			}
 
		  } catch (IOException io) {
			System.out.println(io.getMessage());
		  } catch (JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		  }
		}
		}
	}
}
