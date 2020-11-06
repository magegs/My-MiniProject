package Model;

public class Products
{
    private String Product_Descrpition,Category,Product_Specs1,Product_Specs2,Product_Specs3,Product_Specs4,Product_Name,Product_price,date,product_Image,product_id,time;

    public Products()
    {

    }

    public Products(String product_Descrpition, String category, String product_Specs1, String product_Specs2, String product_Specs3, String product_Specs4, String product_Name, String product_price, String date, String product_Image, String product_id, String time) {
        Product_Descrpition = product_Descrpition;
        Category = category;
        Product_Specs1 = product_Specs1;
        Product_Specs2 = product_Specs2;
        Product_Specs3 = product_Specs3;
        Product_Specs4 = product_Specs4;
        Product_Name = product_Name;
        Product_price = product_price;
        this.date = date;
        this.product_Image = product_Image;
        this.product_id = product_id;
        this.time = time;
    }

    public String getProduct_Descrpition() {
        return Product_Descrpition;
    }

    public void setProduct_Descrpition(String product_Descrpition) {
        Product_Descrpition = product_Descrpition;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getProduct_Specs1() {
        return Product_Specs1;
    }

    public void setProduct_Specs1(String product_Specs1) {
        Product_Specs1 = product_Specs1;
    }

    public String getProduct_Specs2() {
        return Product_Specs2;
    }

    public void setProduct_Specs2(String product_Specs2) {
        Product_Specs2 = product_Specs2;
    }

    public String getProduct_Specs3() {
        return Product_Specs3;
    }

    public void setProduct_Specs3(String product_Specs3) {
        Product_Specs3 = product_Specs3;
    }

    public String getProduct_Specs4() {
        return Product_Specs4;
    }

    public void setProduct_Specs4(String product_Specs4) {
        Product_Specs4 = product_Specs4;
    }

    public String getProduct_Name() {
        return Product_Name;
    }

    public void setProduct_Name(String product_Name) {
        Product_Name = product_Name;
    }

    public String getProduct_price() {
        return Product_price;
    }

    public void setProduct_price(String product_price) {
        Product_price = product_price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduct_Image() {
        return product_Image;
    }

    public void setProduct_Image(String product_Image) {
        this.product_Image = product_Image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
