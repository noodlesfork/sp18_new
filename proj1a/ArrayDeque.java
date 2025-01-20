public class ArrayDeque<T> {

    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
    }

    public void resize(int new_size) {

        T[] items_resized = (T[]) new Object[new_size];
        /*
        有没有可能搞一个getnowFirst的函数
         */
        int nowFirst;
        int nowLast;

        if (nextFirst == items.length - 1) {
            nowFirst = 0;
        } else {
            nowFirst = nextFirst + 1;
        }

        if (nextLast == 0) {
            nowLast = items.length - 1;
        } else {
            nowLast = nextLast - 1;
        }

        if (nowFirst < nowLast) {
            System.arraycopy(items, nowFirst, items_resized, 0, nowLast - nowFirst + 1);
        } else if (nowFirst > nowLast) {
            System.arraycopy(items, nowFirst, items_resized, 0, items.length - nowFirst);
            System.arraycopy(items, 0, items_resized, items.length - nowFirst, nowLast + 1);
        } else {
            System.out.println("nowFirst and nowLast should not be equal!");
        }

        items = items_resized;
        nextFirst = items.length - 1;
        nextLast = size;
        this.printDeque();
        System.out.println();

    }

    public void addFirst(T item) {
        /*
        现在想的做法是addFirst先把items该有的变化都实现，然后再判读要不要resize
         */

        items[nextFirst] = item;
        size += 1;

        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }

        if (size == items.length) {
            resize(size * 2);
        }
    }

    public void addLast(T item) {

        items[nextLast] = item;
        size += 1;

        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }

        if (size == items.length) {
            resize(size * 2);
        }

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int i = 0;
        while (i < size) {
            int index_to_print = nextFirst + i + 1;
            if (index_to_print <= items.length - 1) {
                System.out.print(items[index_to_print] + " ");
            } else {
                System.out.print(items[index_to_print - items.length] + " ");
            }

            i++;
        }
    }

    public T removeFirst() {

        if (nextFirst == items.length - 1) {
            nextFirst = 0;
        } else {
            nextFirst += 1;
        }

        T temp = items[nextFirst];
        size -= 1;
        items[nextFirst] = null;

        if (items.length >= 16 && size <= 0.25 * items.length) {
            resize(Math.floorDiv(items.length, 2));
        }

        return temp;

    }

    public T removeLast() {
        if (nextLast == 0) {
            nextLast = items.length - 1;
        } else {
            nextLast -= 1;
        }

        T temp = items[nextLast];
        size -= 1;
        items[nextLast] = null;

        if (items.length >= 16 && size <= 0.25 * items.length) {
            resize(Math.floorDiv(items.length, 2));
        }

        return items[nextLast];

    }

    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            int index_should_be = nextFirst + index + 1;
            if (index_should_be >= items.length) {
                return items[index_should_be - items.length];
            } else {
                return items[index_should_be];
            }
        }


    }


}
