package Model;

public class CartData {
    private  String  pid,pimagr,pname,price,quantity;

    public CartData()
    {

    }

    public CartData(String pid, String pimagr, String pname, String price, String quantity)
    {
        this.pid = pid;
        this.pimagr = pimagr;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPimagr() {
        return pimagr;
    }

    public void setPimagr(String pimagr) {
        this.pimagr = pimagr;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
