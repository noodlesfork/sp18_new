public class test_lld {
    public static void test_printDeque(){
        LinkedListDeque<Integer> lld =  new LinkedListDeque<>();
        lld.addFirst(1);
        lld.addLast(4);
        lld.addFirst(2);
        lld.addFirst(3);
        lld.addLast(5);
        int a = lld.get(3);
        int b = lld.get(4);
        System.out.println(a);
        System.out.println(b);
        lld.printDeque();
        System.out.println();
        lld.removeFirst();
        lld.printDeque();
        System.out.println();
        lld.removeLast();
        lld.printDeque();
        System.out.println();


    }

    public static void main(String[] args) {


        test_printDeque();
    }
}
