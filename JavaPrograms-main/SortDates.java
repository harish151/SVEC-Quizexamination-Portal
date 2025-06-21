import java.text.SimpleDateFormat;
import java.util.*;
public class SortDates {
    public static void main(String[] args) {
        ArrayList<Date> dates = new ArrayList<>();
        String s1 = "01-01-2023";
        String s2 = "31-01-2023";
        Date start=null,end=null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try{
            dates.add(sdf.parse("01-01-2023"));
            dates.add(sdf.parse("10-01-2023"));
            dates.add(sdf.parse("05-02-2023"));
            dates.add(sdf.parse("25-12-2022"));
            dates.add(sdf.parse("03-01-2023"));
            dates.add(sdf.parse("01-03-2023"));

            start = sdf.parse(s1);
            end = sdf.parse(s2);
        }
        catch(Exception e){
            System.out.println(e);
        }
        //Collections.sort(dates);
        //String start ="01-01-2023";
        int count=0;
        for(int i=0;i<dates.size();i++){
            if(!dates.get(i).after(end) && !dates.get(i).before(start)){
                //System.out.println(dates.get(i));
                count++;
            }
        }
        System.out.println(count);
    }
}
