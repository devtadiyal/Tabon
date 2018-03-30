package com.saffron.tabon.contacts;


import java.util.ArrayList;


public class ContactsList{

    public ArrayList<com.saffron.tabon.contacts.Contact> contactArrayList;

    ContactsList(){

        contactArrayList = new ArrayList<com.saffron.tabon.contacts.Contact>();
    }



    public int getCount(){

        return contactArrayList.size();
    }

    public void addContact(com.saffron.tabon.contacts.Contact contact){
        contactArrayList.add(contact);
    }

    public  void removeContact(com.saffron.tabon.contacts.Contact contact){
        contactArrayList.remove(contact);
    }

    public com.saffron.tabon.contacts.Contact getContact(int id){

        for(int i=0;i<this.getCount();i++){
            if(Integer.parseInt(contactArrayList.get(i).id)==id)
                return contactArrayList.get(i);
        }

        return null;
    }




}
