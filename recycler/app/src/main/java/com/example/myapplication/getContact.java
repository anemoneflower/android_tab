/* 이거는 상관없는 파일
package com.example.myapplication;

import java.io.Serializable;

public class getContact implements Serializable {
    public String user_number, user_name;
    public long photo_id = 0, person_id = 0;
    public int id;

    public getContact(){}
    public String get_user_name(){
         return user_name;
    }
    public void set_user_name(String name){
        this.user_name = name;
    }

    public String get_user_number(){
        return user_number;
    }
    public void set_user_number(String number){
        this.user_number = number;
    }

    public long get_photo_id(){
        return photo_id;
    }
    public void set_photo_id(long ph_id){
        this.photo_id = ph_id;
    }

    public long get_person_id(){
        return person_id;
    }
    public void set_person_id(long per_id){
        this.person_id = per_id;
    }

    public long get_id(){
        return id;
    }
    public void set_id(int id){
        this.id = id;
    }

    /*전화번호를 어떻게 하는걸까
    public String get_phone_num_changed(){
        return user_number.replace("-", "");
    }
    @Override
    public void toString(){
        void this.user_number;
    }
    @Override
    public int hashCode(){
        return get_phone_num_changed().hashCode();
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof getContact){
            return get_phone_num_changed().equals(((getContact) o).get_phone_num_changed());
        }
        return false;
    }
}

*/