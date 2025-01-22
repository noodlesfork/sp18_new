public class ArrayDeque<T> implements Deque<T>{

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
        /*
        啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊原来可以直接这样的到nowFirst，我疯了
         */
        int nowFirst = (nextFirst + 1) % items.length;  // 下一个 first 的位置
        int nowLast = (nextLast - 1 + items.length) % items.length;  // 上一个 last 的位置

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

    @Override
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

    @Override
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

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
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
