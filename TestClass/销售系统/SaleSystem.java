
public class SaleSystem{
    static public Double getBrokerage(Integer sales_value, Double arrival_rate, Integer leave_days){
        if(sales_value<0||arrival_rate<0||arrival_rate>1||leave_days<0||leave_days>365){
            return -1.0;
        }
        if(sales_value>200&&leave_days<=10){
            if(arrival_rate>0.6){
                return sales_value/7.0;
            }else{
                return 0.0;
            }
        }else{
            if(arrival_rate<0.85){
                return sales_value/6.0;
            }else{
                return sales_value/5.0;
            }
        }
    } 
}