public class tryIn {


    public static void main(String[] args){

        In file = new In("data/planets.txt");
        int i = file.readInt();
        double j = file.readDouble();
        double a = file.readDouble();

        System.out.println(i);
        System.out.println(j);
        System.out.println(a);

    }

}

