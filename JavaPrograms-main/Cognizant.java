class Cognizant{
    public static void main(String[] args) {
        int []A = {2,2,3,3,3,6,4,4,4};
        int size = A.length;
        int i=0,j=i+1;
        int ans=0;
        while(j < size){
            //System.out.println("1");
            if(A[i] != A[j]){
                if((j-i) == A[i])
                    ans++;
                i=j;
                j++;
            }
            else {
                j++;
            }
        }
        if((j-i) == A[i])
            ans++;
        System.out.println(ans);
    }
}