import java.io.FileOutputStream;
import java.util.*;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class InputURL {
	
	public HashMap<Character, ArrayList<String>> Results = new HashMap<Character, ArrayList<String>>();
	
	public static int count;
	
	public static void main(String[] args){
	
		count = 0;
		
		int sure = 0;
		scholarcapture Capture = new scholarcapture();
		
		InputURL Startfunc = new InputURL();
		
		Elements title1 = Capture.Zhuaqu("http://scholar.google.com.cn/citations?hl=zh-CN&user=n1zDCkQAAAAJ&pagesize=100&view_op=list_works", 1).select("tr td a.cit-dark-large-link");
	//	System.out.println("one");
		Elements title2 = Capture.Zhuaqu("http://scholar.google.com.cn/citations?hl=zh-CN&user=n1zDCkQAAAAJ&pagesize=100&view_op=list_works&cstart=100", 1).select("tr td a.cit-dark-large-link");
	//	System.out.println("two");
		Startfunc.Makedata(title1, title2);
    //    System.out.println("three");
    	Document target = Capture.Zhuaqu("http://keg.cs.tsinghua.edu.cn/jietang/publication_list.html", 2);
   // 	System.out.println("four");
    	Elements ele = target.select("body ol li");
    	 
    	
    	try{
    		FileOutputStream file = new FileOutputStream("output.txt");
    		System.out.println(ele.size());
    	//System.out.println(ele.size());
    	for(int i=7; i<ele.size(); i++){
    	//	System.out.println(i + "----" + ele.get(i).ownText());
    		String[] got = ele.get(i).ownText().split("\\. ");
    		String sub = new String(got[1]);
    		
    		if(sub.contains("?")){
    			sub = (sub.split("\\?")[0]+"?").toString();
    		}
    		
    		if(sub.contains("!"))
    			sub = sub.replaceAll("!", "! ");
    			
    		if(sub.contains("+")){
    			System.out.println(sub);
    			sub = sub.replaceAll(" +\\+","+");
    			System.out.println(sub);
    		}
    		if(sub.length()<8){
    		//	System.out.println(sub);
    			sub = got[2];
    		//	System.out.println(sub);
    		}
    	//	sub = sub.replaceAll("&nbsp;", "-");
    		if(!(new Character(sub.charAt(0)).isLetter(sub.charAt(0))))
    			sub = sub.substring(1, sub.length());
    	    if(Startfunc.Matching(sub)==0){
    	    	//file.write(("--" + ele.get(i).ownText()+"\r\n\r\n").getBytes());
    	    	
    	    	for(int h=0; h<got.length; h++)
    	    		if(Startfunc.Matching(got[h])==1){
    	    			sure = 1;
    	    			break;
    	    		}
    	    	if(sure==0){
    	    		System.out.println(count + sub + "  未出现在google主页\n");
    	    		count++;
    	    		file.write((String.valueOf(count) + ": \r\n" + ele.get(i).ownText()+"  未出现在google主页\r\n\r\n").getBytes());
    	    	}
    	    	sure = 0;
    	    }
    	}
    	
    	file.close();
    		
    	}catch(Exception e){
    		e.printStackTrace();	
    	}
    	 
	}


	public void Makedata(Elements title1, Elements title2){
		
		String temp;
        for(int i=0;i<title1.size();i++){
        	temp = title1.get(i).html();
        	Character top = temp.charAt(0);
        	if(Results.containsKey(top)){
        		ArrayList<String> newtemp = Results.get(top);
        		newtemp.add(temp);
        		Results.put(top, newtemp);
        	//	System.out.println(Results.get(top));
        	//	for(int l=0;l<newtemp.size();l++)
        		//	System.out.println(top + "--" + l +" "+newtemp.get(l));
        	}
        	else{
        		ArrayList<String> newtemp = new ArrayList<String>();
        		newtemp.add(temp);
        		Results.put(top , newtemp);
        		//System.out.println(top + "==" + Results.get(top).get(0));
        	}	
        	//System.out.println(top + temp);
        }
        
        for(int i=0;i<title2.size();i++){
        	temp = title2.get(i).html();
        	Character top = temp.charAt(0);
        	if(Results.containsKey(top)){
        		ArrayList<String> newtemp = Results.get(top);
        		newtemp.add(temp);
        		Results.put(top, newtemp);
        	//	System.out.println(Results.get(top));
        	//	for(int l=0;l<newtemp.size();l++)
        		//	System.out.println(top + "--" + l +" "+newtemp.get(l));
        	}
        	else{
        		ArrayList<String> newtemp = new ArrayList<String>();
        		newtemp.add(temp);
        		Results.put(top , newtemp);
        		//System.out.println(top + "==" + Results.get(top).get(0));
        	}
	        	//System.out.println(top + temp);
        }
	    Iterator point = Results.entrySet().iterator();
	    try{
		    FileOutputStream file = new FileOutputStream("pipei.txt");
		    while(point.hasNext()){
		    	Map.Entry one = (Map.Entry)point.next();
		    	System.out.println(one.getKey().toString() + "---" + one.getValue()+"\r\n\r\n");
		    	file.write((one.getKey().toString() + "---" + one.getValue()+"\r\n\r\n").getBytes());
		    }
	    
	    }catch(Exception e){
	    	e.printStackTrace();
	    	
	    }
	    		
	}
	
	
	public int Matching(String source){
		try{
		Character key = new Character(source.charAt(0));
		if(Results.containsKey(key))
			for(int i=0; i<Results.get(key).size(); i++){
				System.out.println("["+source.toLowerCase()+"]");
				String tar = new String(Results.get(key).get(i).toLowerCase());
				if(tar.contains("‐"))
					tar = tar.replaceAll("‐", "-");
				System.out.println("-"+Results.get(key).get(i).toLowerCase()+"-"+"\n");
				if(tar.equals(source.toLowerCase())){
		//			System.out.println(count +"成功\n" + source.toLowerCase() + "\n" + Results.get(key).get(i).toLowerCase() + "匹配");
					return 1;
				}
			}
		else
			return 0;
		
		
		}catch(Exception e){
			e.printStackTrace();	
		}
		
		return 0;
	}
	
}
