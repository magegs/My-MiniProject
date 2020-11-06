package Model;

public class Bonus
{
    private  String cust_id,ref_id;
    private  Long Total_ref,points;

    public Bonus()
    {

    }

    public Bonus(String cust_id, String ref_id, Long total_ref, Long points)
    {
        this.cust_id = cust_id;
        this.ref_id = ref_id;
        Total_ref = total_ref;
        this.points = points;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public String getRef_id() {
        return ref_id;
    }

    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }

    public Long getTotal_ref() {
        return Total_ref;
    }

    public void setTotal_ref(Long total_ref) {
        Total_ref = total_ref;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
