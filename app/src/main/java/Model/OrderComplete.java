package Model;

public class OrderComplete {
    private String AddressLine1,AddressLine2,AddressLine3,Contact,Customer_Name,PaymentMode,State,Status,Total_Amount,date,pincode,time,Cart_id,Total_Items;

    public OrderComplete()
    {

    }

    public OrderComplete(String addressLine1, String addressLine2, String addressLine3, String contact, String customer_Name, String paymentMode, String state, String status, String total_Amount, String date, String pincode, String time, String cart_id, String total_Items) {
        AddressLine1 = addressLine1;
        AddressLine2 = addressLine2;
        AddressLine3 = addressLine3;
        Contact = contact;
        Customer_Name = customer_Name;
        PaymentMode = paymentMode;
        State = state;
        Status = status;
        Total_Amount = total_Amount;
        this.date = date;
        this.pincode = pincode;
        this.time = time;
        Cart_id = cart_id;
        Total_Items = total_Items;
    }

    public String getAddressLine1() {
        return AddressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        AddressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return AddressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        AddressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return AddressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        AddressLine3 = addressLine3;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public String getPaymentMode() {
        return PaymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        PaymentMode = paymentMode;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(String total_Amount) {
        Total_Amount = total_Amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCart_id() {
        return Cart_id;
    }

    public void setCart_id(String cart_id) {
        Cart_id = cart_id;
    }

    public String getTotal_Items() {
        return Total_Items;
    }

    public void setTotal_Items(String total_Items) {
        Total_Items = total_Items;
    }
}
