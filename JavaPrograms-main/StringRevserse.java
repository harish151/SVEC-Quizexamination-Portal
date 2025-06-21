
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StringRevserse {
    public static void main(String[] args) throws Exception{
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter a string : ");
        String str=br.readLine();
        System.out.println("Reversed String : "+reverse(str));
        br.close();
    }
    static String reverse(String str){
        String ans="";
        int i=str.length();
        while(i-->0){
            ans+=str.charAt(i);
        }
        return ans;
    }
}
