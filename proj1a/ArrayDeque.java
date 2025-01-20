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

    private void resize(int newSize) {

        T[] itemsResized = (T[]) new Object[newSize];
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
            System.arraycopy(items, nowFirst, itemsResized, 0, nowLast - nowFirst + 1);
        } else if (nowFirst > nowLast) {
            System.arraycopy(items, nowFirst, itemsResized, 0, items.length - nowFirst);
            System.arraycopy(items, 0, itemsResized, items.length - nowFirst, nowLast + 1);
        } else {
            System.out.println("nowFirst and nowLast should not be equal!");
        }

        items = itemsResized;
        nextFirst = newSize - 1;
        nextLast = size;

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
            int indexToPrint = nextFirst + i + 1;
            if (indexToPrint <= items.length - 1) {
                System.out.print(items[indexToPrint] + " ");
            } else {
                System.out.print(items[indexToPrint - items.length] + " ");
            }

            i++;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        if (nextFirst == items.length - 1) {
            nextFirst = 0;
        } else {
            nextFirst += 1;
        }

        T temp = items[nextFirst];
        size -= 1;

        if (items.length >= 16 && size <= 0.25 * items.length) {
            resize(Math.floorDiv(items.length, 2));
        }

        return temp;

    }

    public T removeLast() {

        if (size == 0) {
            return null;
        }
        if (nextLast == 0) {
            nextLast = items.length - 1;
        } else {
            nextLast -= 1;
        }

        T temp = items[nextLast];
        size -= 1;

        if (items.length >= 16 && size <= 0.25 * items.length) {
            resize(Math.floorDiv(items.length, 2));
        }

        return temp;

    }

    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            int indexShouldBe = nextFirst + index + 1;
            if (indexShouldBe >= items.length) {
                return items[indexShouldBe - items.length];
            } else {
                return items[indexShouldBe];
            }
        }


    }


}
