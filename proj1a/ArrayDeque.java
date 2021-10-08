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

    public ArrayDeque(ArrayDeque other){
        items = (T[]) new Object[other.items.length];
        System.arraycopy(other.items,0,items,0,items.length);
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        size = other.size;
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
        return size;
    }

    public void printDeque(){
        int first = plusOne(nextFirst);
        for(int i=0; i < size; i++){
            System.out.print(items[first] + " ");
            first = plusOne(first);
        }
    }

    public T removeFirst(){
        int first = plusOne(nextFirst);
        T removedItem = items[first];
        nextFirst = first;
        items[first] = null;
        size -= 1;
        if(items.length >= 16 && (size/items.length) <= 0.25){
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
        if(items.length >= 16 && (size/items.length) <= 0.25){
            sizeDown();
        }
        return removedItem;
    }

    public T get(int index){
        return items[(plusOne(nextFirst)+index) % items.length];
    }

}
