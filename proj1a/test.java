public class test {

    public static void main(String[] args){
        int[] a = new int[]{1,2,3,4};
        int b = a[2];
        b = 10086;
        int[] c = new int[3];
        System.arraycopy(a,3, c, 0, 0);
        c[2] = 1;
        System.out.println(c);
        System.out.println(a[2]);

    }
}
