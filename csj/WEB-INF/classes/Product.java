
import java.util.ArrayList;
import java.util.List;


public class Product {
    String retailer;
    String name;
    String id;
    String type;
    String image;
    String condition;
    int price;
    List<String> accessories;
    public Product(){
        accessories=new ArrayList<String>();
    }

  /*  public Product(String retailer, String name, String id, String type, String image, String condition, int price){
    	this. retailer = retailer;
    }*/

 public Product(String id,String name){
        this.id = id;
        this.name = name;
    }



void setId(String id) {
	this.id = id;
}
public String getId()
{
	return id;
}

void setRetailer(String retailer) {
	this.retailer = retailer;
}

void setType(String type) {
	this.type = type;
}
public String getType(){
	return type;
}


void setImage(String image) {
	this.image = image;
}

void setCondition(String condition) {
	this.condition = condition;
}

void setPrice(int price) {
	this.price = price;
}
public int getPrice()
{
	return price;
}

/*List setAccessories() {
	 this.accessories=accessories;
}*/
List getAccessories() {
	return accessories;
}


void setName(String name) {
	this.name = name;
}

public String getName(){
	return name;
}



}
