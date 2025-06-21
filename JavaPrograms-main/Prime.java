
import java.util.Scanner;

public class Prime {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter N : ");
        int n=sc.nextInt();
        if(isPrime(n))
            System.out.println("Prime Number");
        else
            System.out.println("Not a Prime Number");
        sc.close();
    }
    static boolean isPrime(int n){
        for(int i=2;i*i<=n;i++){
            if(n%i==0)
                return false;
        }
        return true;
    }
}
