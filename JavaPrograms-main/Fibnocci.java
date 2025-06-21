
import java.util.Scanner;

class Fibnocci
{
    public static void main(String args[]){
        int a=0,b=1;
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter N : ");
            int n=sc.nextInt();
            System.out.print(0);
            while(n-->1){
                int c=a+b;
                System.out.print(" "+c);
                a=b;
                b=c;
            }
        }
    }
}