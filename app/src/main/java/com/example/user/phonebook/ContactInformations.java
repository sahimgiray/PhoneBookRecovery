package com.example.user.phonebook;

/**
 * Created by User on 20.3.2016.
 */
public class ContactInformations {
    private String phone_number;
    private String contact_name;

    public ContactInformations(String phone_number, String contact_name) {
        this.phone_number = phone_number;
        this.contact_name = contact_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }
}
