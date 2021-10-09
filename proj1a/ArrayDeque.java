public class ArrayDeque<T> {
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 4;
        nextLast = 5;
        size = 0;
    }

    private void resize(int capacity){
        T[] newItems = (T[]) new Object[capacity];
        int first = plusOne(nextFirst);
        for(int i=0; i<size; i++){
            newItems[i] = items[first];
            first = plusOne(first);
        }
        nextFirst = capacity - 1;
        nextLast = size;
        items = newItems;
    }

    private void sizeUp(){
        resize(items.length * 2);
    }

    private void sizeDown(){
        resize(items.length / 2);
    }

    private int minusOne(int index){
        return (index-1 + items.length) % items.length;
    }

    private int plusOne(int index){
        return (index + 1) % items.length;
    }

    public void addFirst(T item){
        if(size == items.length){
            sizeUp();
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public void addLast(T item){
        if(size == items.length){
            sizeUp();
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        if(size < 0){
            return 0;
        }
        return size;
    }

    public void printDeque(){
        int first = plusOne(nextFirst);
        for(int i=0; i < size; i++){
            System.out.print(items[first] + " ");
            first = plusOne(first);
        }
        System.out.println();
    }

    public T removeFirst(){
        int first = plusOne(nextFirst);
        T removedItem = items[first];
        nextFirst = first;
        items[first] = null;
        size -= 1;
        if(items.length >= 16 && ((double)size/(double)items.length) <= 0.25){
            sizeDown();
        }
        return removedItem;
    }

    public T removeLast(){
        int last = minusOne(nextLast);
        T removedItem = items[last];
        nextLast = last;
        items[last] = null;
        size -= 1;
        if(items.length >= 16 && ((double)size/(double)items.length) <= 0.25){
            sizeDown();
        }
        return removedItem;
    }

    public T get(int index){
        return items[(plusOne(nextFirst)+index) % items.length];
    }

//    public static void main(String[] args){
//        ArrayDeque<Integer> t = new ArrayDeque();
//
//        t.addFirst(7);
//        t.addFirst(8);
//        t.addLast(1);
//        t.addLast(2);
//        t.addLast(3);
//        t.addLast(4);
//        t.addLast(5);
//        t.addLast(6);
//        t.addLast(9);
//        t.addLast(10);
//        t.printDeque();
//        System.out.println(t.removeFirst());
//        System.out.println(t.removeLast());
//        System.out.println(t.isEmpty());
//        t.printDeque();
//        System.out.println(t.get(0));
//    }

}
