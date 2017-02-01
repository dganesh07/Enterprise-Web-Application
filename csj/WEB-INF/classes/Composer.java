import java.util.ArrayList;
import java.util.List;


public class Composer {
	String productName;
	String id;

	public Composer(String id, String productName) {
		this.id = id;
		this.productName = productName;
	}


	public String getId() {
		return id;
	}

	public String getproductName() {
		return productName;
	}
}