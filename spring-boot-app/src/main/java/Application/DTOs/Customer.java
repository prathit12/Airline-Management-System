package Application.DTOs;

public class Customer {
    private String customer_number;
    private int customer_id;
    private static int customer_id_counter = 0;
    private String customer_name;
    private String email;
    private String tel_num;
    private String address;

    public Customer() {
    }

    public Customer(String customer_number, String customer_name, String email, String tel_num, String address) {
        this.customer_number = customer_number;
        this.customer_id = ++customer_id_counter;
        this.customer_name = customer_name;
        this.email = email;
        this.tel_num = tel_num;
        this.address = address;
    }

    public Customer(int customer_id, String customer_number, String customer_name, String email, String tel_num, String address) {
        this.customer_number = customer_number;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.email = email;
        this.tel_num = tel_num;
        this.address = address;
    }

    public String getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel_num() {
        return tel_num;
    }

    public void setTel_num(String tel_num) {
        this.tel_num = tel_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customer_number='" + customer_number + '\'' +
                ", customer_id=" + customer_id +
                ", customer_name='" + customer_name + '\'' +
                ", email='" + email + '\'' +
                ", tel_num='" + tel_num + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}