import java.util.*;
public class AreaPerimeter {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter base and height of triangle  ");
        double b=sc.nextInt();
        double h=sc.nextInt();
        System.out.println("Area of triangle : "+area(b,h));
        sc.close();
    }
    static double area(double  b,double  h){
        return (b*h)/2;
    }
}
