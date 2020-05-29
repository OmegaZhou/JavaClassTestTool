import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PerpetualCalendar{
    public static String getNextDay(int y,int m,int d){
        if(y<1900||y>2500||m<1||m>12){
            return "非法输入";
        }
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Calendar date=Calendar.getInstance();
        date.clear();
        date.set(Calendar.YEAR,y);
        date.set(Calendar.MONTH,m-1);
        var i=date.getActualMaximum(Calendar.DATE);
        if(d<1||d>i){
            return "非法输入";
        }
        date.set(Calendar.DATE,d);
        date.add(Calendar.DATE,1);
        return dateFormat.format(date.getTime());
    }
}