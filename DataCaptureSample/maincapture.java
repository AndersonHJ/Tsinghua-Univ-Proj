

public class maincapture {

	public static void main(String[] args){
			CaptureDown capture = new CaptureDown();
			
			System.out.println(new String(capture.fetchData(
				"http://scholar.google.com.cn/citations?hl=zh-CN&user=n1zDCkQAAAAJ&view_op=list_works&pagesize=100")));
	}
}
