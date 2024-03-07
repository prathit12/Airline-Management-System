package Application.DTOs;

public class Customer {
    //-- Create Customer table
    //CREATE TABLE customer (
    //    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    //    customer_name VARCHAR(20),
    //    email VARCHAR(50) NOT NULL UNIQUE,
    //    tel_num VARCHAR(20),
    //    address VARCHAR(255)
    //);
//the customer class is used to store the information of the customer
    //customer_id, customer_name, email, tel_num, address.
    private String customer_number; // primary key

    private int customer_id; // primary key and auto increment

    private static int customer_id_counter = 0;
    private String customer_name;
    private String email;
    private String tel_num;
    private String address;

    //default constructor
    public Customer() {
    }

    //constructor with parameters
    public Customer(String customer_number, String customer_name, String email, String tel_num, String address) {
        this.customer_number = customer_number;
        this.customer_id = ++customer_id_counter;
        this.customer_name = customer_name;
        this.email = email;
        this.tel_num = tel_num;
        this.address = address;
    }

    //paeameter with customer_id
    public Customer(int customer_id,String customer_number, String customer_name, String email, String tel_num, String address) {
        this.customer_number = customer_number;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.email = email;
        this.tel_num = tel_num;
        this.address = address;
    }

    //getters and setters
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