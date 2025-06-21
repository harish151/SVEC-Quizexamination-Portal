
import java.util.Scanner;
class Pair{
    int x,y;
    Pair(int x,int y){
        this.x=x;
        this.y=y;
    }
    void swap(){
        x=x^y;
        y=x^y;
        x=x^y;
    }
}
public class Swap {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter Two Numbers");
        int a=sc.nextInt();
        int b=sc.nextInt();
        Pair p=new Pair(a,b);
        System.out.println("Before Swap");
        System.out.println(p.x+" "+p.y);
        p.swap();
        System.out.println("After Swap");
        System.out.println(p.x+" "+p.y);
        sc.close();
    }
}
