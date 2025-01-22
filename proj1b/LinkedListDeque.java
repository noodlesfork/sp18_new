public class LinkedListDeque<T> implements Deque<T>{

    private final IntDeque sentinel;
    private int size;


    public class IntDeque {
        private IntDeque prev;
        private T value;
        private IntDeque next;

        public IntDeque(IntDeque p, T x, IntDeque n) {
            value = x;
            prev = p;
            next = n;
        }

        public T get(int index) {
            if (index == 0) {
                return this.next.value;
            } else {
                return this.next.get(index - 1);
            }

        }



    }


    public LinkedListDeque() {
        sentinel = new IntDeque(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        sentinel.next = new IntDeque(sentinel, x, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;

    }

    @Override
    public void addLast(T x) {
        sentinel.prev = new IntDeque(sentinel.prev, x, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void printDeque() {

        IntDeque temp = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(temp.value + " ");
            temp = temp.next;
        }
    }

    @Override
    public T removeFirst() {

        if (size == 0) {
            return null;
        } else {
            T temp = sentinel.next.value;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return temp;
        }

    }

    @Override
    public T removeLast() {

        if (size == 0) {
            return null;
        } else {
            T temp = sentinel.prev.value;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return temp;
        }
    }

    @Override
    public T get(int index) {
        if (size == 0) {
            return null;
        }
        if (index == 0) {
            return sentinel.next.value;
        } else {
            return sentinel.next.get(index - 1);
        }

    }

    public T getRecursive(int index) {
        if (size == 0) {
            return null;
        } else {
            IntDeque temp = sentinel.next;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            return temp.value;
        }
    }



}
