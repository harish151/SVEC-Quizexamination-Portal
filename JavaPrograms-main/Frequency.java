//given a string find frequency of each character
import java.util.*;
class Frequency{
    public static void main(String args[]){
        String str = "Shankar";
        HashMap<Character,Integer> hm = new LinkedHashMap<>();
        for(char ch : str.toCharArray()){
            hm.put(ch, hm.getOrDefault(ch,0)+1);
        }
        System.out.println("Frequency of all characters ");
        for(char ch : hm.keySet()){
            System.out.println(ch +" : "+hm.get(ch));
        }
    }
}