public class test_ad {

    public static void test_isEmpty(){
        ArrayDeque<Integer> ad = new ArrayDeque<> ();
        System.out.println(ad.isEmpty());
    }

    public static void test_size(){
        ArrayDeque<Integer> ad = new ArrayDeque<> ();
        System.out.println(ad.size());
    }

    public static void test_addFirstLast(){
        ArrayDeque<Integer> ad = new ArrayDeque<> ();
        ad.addFirst(123);
        ad.addLast(4);
        ad.addFirst(1);
        ad.addLast(5);
        ad.addFirst(2);
        ad.addLast(5);
        ad.addFirst(3);
        ad.addFirst(6);
        ad.addLast(9);
        ad.printDeque();
    }

    public static void test_removeFirstLast(){
        ArrayDeque<Integer> ad = new ArrayDeque<> ();
        for (int i = 0; i < 24; i++ ) {
            ad.addFirst(i);
        }
        for (int i = 0; i < 24; i++ ) {
            if (i % 2 == 0) {
                ad.removeFirst();
            } else {
                ad.removeLast();
            }

        }

    }

    public static void test_get(){

        ArrayDeque<Integer> ad = new ArrayDeque<> ();
        for (int i = 0; i < 24; i++ ) {
            ad.addFirst(i);
        }

        for (int i = 0; i < 24; i++ ) {
            System.out.println(ad.get(i));
        }


    }


    public static void main(String[] args){
//        test_isEmpty();
//        test_size();
//        test_addFirstLast();
//        test_removeFirstLast();
        test_get();

    }
}
