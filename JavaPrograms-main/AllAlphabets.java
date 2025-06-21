//import java.util.*;
public class AllAlphabets
{
	public static void main(String[] args) {
		String str="a quick brown fox jumps over the lazy dog";
		//HashSet<Character> hs=new HashSet<>();
		int []ar=new int[26];
		for(char ch : str.toCharArray()){
		    if(ch>='a' && ch<='z')
		        //hs.add(ch);
		        ar[ch-'a']++;
		}
		int i;
		for(i=0;i<26;i++){
		    if(ar[i]==0){
		        System.out.println("false");
		        break;
		    }
		        
		}
		if(i==26)
		    System.out.println("true");
// 		if(hs.size()==26)
// 		    System.out.println("true");
// 		else
// 		    System.out.println("false");
	}
}
