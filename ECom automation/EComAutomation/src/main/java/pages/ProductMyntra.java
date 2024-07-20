package pages;

public class ProductMyntra implements Comparable<ProductMyntra>{
	 String name;
     String price;
     int discountPercentage;
     String link;

     public ProductMyntra(String name, String price, int discountPercentage, String link) {
         this.name = name;
         this.price = price;
         this.discountPercentage = discountPercentage;
         this.link = link;
	}

	@Override
	public int compareTo(ProductMyntra o) {
		// TODO Auto-generated method stub
		return o.discountPercentage - this.discountPercentage;
	}
	
	@Override
	public String toString() {
		return "Product Name: " + name + ", Price: Rs. " + price + ", Discount: " + discountPercentage + "%, Link:" + link;
	}

	public int getDiscountPercentage() {
		// TODO Auto-generated method stub
		return discountPercentage;
	}
	

}
