package in.regres.listUsers;

import java.util.List;

public class RegresUsers {
    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<Data> data;
    public RegresUsers(){

    }

    public int getPage() {
        return page;
    }

    public int getPer_page() {
        return per_page;
    }

    public int getTotal() {
        return total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public List<Data> getData() {
        return data;
    }
}
