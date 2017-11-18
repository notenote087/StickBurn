package com.example.admin.stickburn;

/**
 * Created by admin on 17/11/2560.
 */

class CallFucntion {



    public  double CalBMI(int h , int w){
            double result = 0.0 ;
            double h_2 = h /100 ;
            result = w / (h_2 * h_2);

        return result;
    }


    public String ResultBMI(double result){
        String resultBMI = "";

        if(result < 17){ resultBMI = "ดัชนีมวลการคือ ผอม" ;}
        else if(result >=17 ||result <=18.4){ resultBMI = "ัดัชนีมวลการคือ สมส่วน";}
        else if(result >= 18.5 || result <= 24.9){resultBMI = "ดัชนีมวลกายคือ เริ่มจะอวบ" ;}
        else if(result >= 25 || result <=29.9){resultBMI = "ดัชนีมวลกายคือ อวบระยะสุดท้าย" ;}
        else if(result > 30){ resultBMI = "ดัชนีมวลกายคือ อ้วนอันตราย" ;}
        return resultBMI;


    }

    public double BMR(double h , double w , String sex , int age){ // Callorru ที่ต้องการแตค่ลพวัน
        double callorry = 0.0;

        // man 10 * w + 6.25 * h  - 5 * age + 5
        // woman 10 * w + 6.25 * h - 5 * age - 161
        switch (sex){

            case "man": callorry =  10 * w + 6.25 * h - 5 * age + 5 ;  break ;
            case "girl" : callorry = 10 * w + 6.25 * h - 5 * age - 161 ;  break ;
        }

        return callorry ;
    }

    public void select_id_food(){



    }

    public void callorryForOneDay(){


    }

    //public





}
