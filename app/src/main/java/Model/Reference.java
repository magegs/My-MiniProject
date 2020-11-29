package Model;

public class Reference {
    private  String jp_id,ref_id,ref_num;

    public Reference()
    {

    }

    public Reference(String jp_id, String ref_id, String ref_num) {
        this.jp_id = jp_id;
        this.ref_id = ref_id;
        this.ref_num = ref_num;
    }

    public String getJp_id() {
        return jp_id;
    }

    public void setJp_id(String jp_id) {
        this.jp_id = jp_id;
    }

    public String getRef_id() {
        return ref_id;
    }

    public void setRef_id(String ref_id) {
        this.ref_id = ref_id;
    }

    public String getRef_num() {
        return ref_num;
    }

    public void setRef_num(String ref_num) {
        this.ref_num = ref_num;
    }
}
