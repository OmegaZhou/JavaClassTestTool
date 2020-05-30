public class PhoneBillCalculator{

    private static double getDiscount(int phone_time,int uncost_times){
        if(phone_time>0&&phone_time<=60&&uncost_times<=1){
            return 0.01;
        }
        if(phone_time>60&&phone_time<=120&&uncost_times<=2){
            return 0.015;
        }
        if(phone_time>120&&phone_time<=180&&uncost_times<=3){
            return 0.02;
        }
        if(phone_time>180&&phone_time<=300&&uncost_times<=3){
            return 0.025;
        }
        if(phone_time>300&&uncost_times<=6){
            return 0.03;
        }
        return 0.0;
    }
    private static int MaxPhoneTime=44640;
    private static double MaxLastYearCost=6721.0;
    private static int MaxUnCostTimes=11;
    public static Double calPhoneBill(int phone_time,int uncost_times,double last_year_cost){
        if(phone_time<0||phone_time>MaxPhoneTime||uncost_times<0||uncost_times>MaxUnCostTimes||last_year_cost<0||last_year_cost>MaxLastYearCost){
            return -1.0;
        }
        return 25.0+phone_time*0.15*(1-getDiscount(phone_time, uncost_times))+last_year_cost*0.05;
    } 
}