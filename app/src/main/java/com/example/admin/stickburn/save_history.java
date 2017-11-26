package com.example.admin.stickburn;

/**
 * Created by admin on 26/11/2560.
 */

public class save_history {

    public String date_;
    public String food ;
    public String cal ;

    public save_history(){

    }

    public save_history(String food , String cal,String date){

        this.date_ = date;
        this.food = food;
        this.cal=cal;
    }
}
