public class LinkedListDeque<T> implements Deque<T>{

    private class Node {
        public Node prev;
        public T item;
        public Node next;
        public Node(T i, Node p, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }

    private Node setinel;
    private int size;

    public LinkedListDeque() {
        setinel = new Node(null, null, null);
        setinel.prev = setinel;
        setinel.next = setinel;
        size = 0;
    }

    @Override
    public void addFirst(T item){
        setinel.next.prev = new Node(item, setinel, setinel.next);
        setinel.next = setinel.next.prev;
        size += 1;
    }

    @Override
    public void addLast(T item){
        setinel.prev.next = new Node(item, setinel.prev, setinel);
        setinel.prev = setinel.prev.next;
        size += 1;
    }

    @Override
    public int size(){
        if(size < 0){
            return 0;
        }
        return size;
    }

    @Override
    public void printDeque(){
        Node p = setinel.next;
        while(p != setinel){
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst(){
        T removedNode = setinel.next.item;
        setinel.next.next.prev = setinel;
        setinel.next = setinel.next.next;
        size -= 1;
        return removedNode;
    }

    @Override
    public T removeLast(){
        T removedNode = setinel.prev.item;
        setinel.prev.prev.next = setinel;
        setinel.prev = setinel.prev.prev;
        size -= 1;
        return removedNode;
    }

    @Override
    public T get(int index){
        Node p = setinel;
        if(index > (size-1)){
            return null;
        }
        while(index >= 0){
            p = p.next;
            index -= 1;
        }
        return p.item;
    }

    private T getRecursiveHelper(Node p, int i){
        if(i == 0){
            return p.item;
        }
        p = p.next;
        i -= 1;
        return getRecursiveHelper(p, i);
    }

    public T getRecursive(int index){
        if(index > (size-1)){
            return null;
        }
        return getRecursiveHelper(setinel.next, index);
    }

}
