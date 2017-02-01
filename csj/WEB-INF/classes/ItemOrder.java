

/** Associates a catalog Item with a specific order by
 *  keeping track of the number ordered and the total price.
 *  Also provides some convenience methods to get at the
 *  CatalogItem data without extracting the CatalogItem
 *  separately.
 *  <P>
 *  Taken from Core Servlets and JavaServer Pages 2nd Edition
 *  from Prentice Hall and Sun Microsystems Press,
 *  http://www.coreservlets.com/.
 *  &copy; 2003 Marty Hall; may be freely used or adapted.
 */

public class ItemOrder {
  private Product item;
  private int numItems;

  public ItemOrder(Product item) {
    setItem(item);
    setNumItems(1);
  }

  public Product getItem() {
    return(item);
  }

  protected void setItem(Product item) {
    this.item = item;
  }

  public String getItemID() {
    return(getItem().getId());
  }
  
  public String getItemName() {
    return(getItem().getName());
  }

    public int getUnitCost() {
    return(getItem().getPrice());
  }
  
  public int getNumItems() {
    return(numItems);	
  }

  public void setNumItems(int n) {
    this.numItems = n;
  }

  public void incrementNumItems() {
    setNumItems(getNumItems() + 1);
  }

  public void cancelOrder() {
    setNumItems(0);
  }

  public double getTotalCost() {
    return(getNumItems() * getUnitCost());
  }
}
