import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void TestAddLastRemoveLast(){

        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();

        int n = 5;
        String output = "";

        for (int i = 0; i < 15; i ++) {
            int A = StdRandom.uniform(n);
            sad1.addLast(A);
            ads1.addLast(A);
            output += "addFirst(" + A + ")\n";
        }

        for (int i = 0; i < 15; i ++) {
            output += "removeLast\n";
            assertEquals(output, sad1.removeLast(), ads1.removeLast());

        }
    }

//    @Test
//    public void TestAddFirstRemoveLast(){
//
//        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
//        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();
//
//        int n = 5;
//        String output = "";
//
//        for (int i = 0; i < 15; i ++) {
//            int A = StdRandom.uniform(n);
//            sad1.addFirst(A);
//            ads1.addFirst(A);
//            output += String.format("addFirst(%d)", A);
//            output += "\n";
//        }
//
//        for (int i = 0; i < 15; i ++) {
//            output += "removeLast";
//            assertEquals(output, sad1.removeLast(), ads1.removeLast());
//            output += "\n";
//        }
//    }

//    @Test
//    public void TestAddFirstRemoveFirst(){
//
//        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
//        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();
//
//        int n = 5;
//        String output = "";
//
//        for (int i = 0; i < 15; i ++) {
//            int A = StdRandom.uniform(n);
//            sad1.addFirst(A);
//            ads1.addFirst(A);
//            output += String.format("addFirst(%d)", A);
//            output += "\n";
//        }
//
//        for (int i = 0; i < 15; i ++) {
//            output += "removeFirst\n";
//            assertEquals(output, sad1.removeFirst(), ads1.removeFirst());
//        }
//    }
//
//    @Test
//    public void TestAddLastRemoveFirst(){
//
//        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
//        ArrayDequeSolution<Integer> ads1 = new ArrayDequeSolution<>();
//
//        int n = 5;
//        String output = "";
//
//        for (int i = 0; i < 15; i ++) {
//            int A = StdRandom.uniform(n);
//            sad1.addLast(A);
//            ads1.addLast(A);
//            output += String.format("addLast(%d)", A);
//            output += "\n";
//        }
//
//        for (int i = 0; i < 15; i ++) {
//            output += "removeFirst\n";
//            assertEquals(output, sad1.removeFirst(), ads1.removeFirst());
//        }
//    }

}
