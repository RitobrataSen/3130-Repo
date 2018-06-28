import java.util.HashMap; 

public class test{
	public static void main(String[] args) {
		HashMap<String, Boolean> h = new HashMap<String, Boolean>();
		h.put("key1", true);
		h.put("key1", false);
		h.put("key3", true);
		System.out.println(h);

	}
}