import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


public class CaptureDown{
	
	static byte[] html;
	
	public CaptureDown(){html = null;}
	
	public byte[] fetchData(String URL){
		
		HttpClient httpClient = new HttpClient();
		List<Header> headers = new ArrayList<Header>();
		headers.add(new Header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)"));
		httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
		
		GetMethod getMethod = new GetMethod(URL);
		
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));
		
			try {
	            int statusCode = httpClient.executeMethod(getMethod);
	            if (statusCode != HttpStatus.SC_OK) {
	                System.err.println("Method failed: "
	                        + getMethod.getStatusLine());
	            }
	            InputStream in = getMethod.getResponseBodyAsStream();
				ByteArrayOutputStream bAOut = new ByteArrayOutputStream();
				int c;
				while ((c = in.read()) != -1)
					bAOut.write(c);

				html = bAOut.toByteArray();
				}catch (HttpException e) {
					System.err.println("Fatal protocol violation: " + e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					System.err.println("Fatal transport error: " + e.getMessage());
					e.printStackTrace();
				}finally {
				// Release the connection.
				getMethod.releaseConnection();
				System.out.println("Capture_OK");
			}
			return html;
	}
}