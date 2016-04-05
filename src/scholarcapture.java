
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.*;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import org.jsoup.nodes.*;
import org.jsoup.*;


public class scholarcapture{
	
	//public List Results = new ArrayList();
	
	public Document fetchPageData(String URL, int type){
		
		int c;
		Document doc = null;
		
		HttpClient httpClient = new HttpClient();
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
		httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
		
		GetMethod getMethod = new GetMethod(URL);
		
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));

		try {
			FileOutputStream file = new FileOutputStream("output2.txt");	
			
			int statusCode = httpClient.executeMethod(getMethod);
	        if (statusCode != HttpStatus.SC_OK){
	            System.err.println("Method failed: " + getMethod.getStatusLine());
	        }
	        InputStream in = getMethod.getResponseBodyAsStream();
	        ByteArrayOutputStream webtext = new ByteArrayOutputStream();
	     
	        while((c = in.read()) != -1)
	        	webtext.write(c);
	        
	        file.write(webtext.toByteArray());
	        File file3 = new File("output2.txt");
	       
	        if(type==1)
	        	doc = Jsoup.parse(file3,"UTF-8");
	        else if(type==2)
	        	doc = Jsoup.parse(file3,"UTF-16");
	        
		}catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		}finally {
			getMethod.releaseConnection();
		}
		return doc;
	}
	
	
	
	
	
	
}