import java.util.HashMap;

public class Goodpersons {
    public static void main(String[] args) {
        String s1 = "aabbjkl";
        String s2 = "aaabbbjk";
        HashMap<Character,Integer> hm1 = new HashMap<>();
        HashMap<Character,Integer> hm2 = new HashMap<>();
        for(char ch : s1.toCharArray()){
            hm1.put(ch,hm1.getOrDefault(ch,0)+1);        
        }
        for(char ch : s2.toCharArray()){
            hm2.put(ch,hm2.getOrDefault(ch,0)+1);        
        }
        int ans=0;
        for(char ch : hm1.keySet()){
            if(hm1.get(ch) == 2){
                if(hm2.containsKey(ch)  && hm2.get(ch) == 3)
                ans++;
            }
        }
        System.out.println(ans);
    }
}
