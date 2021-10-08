public class LinkedListDeque<T> {

    public class Node {
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

    public LinkedListDeque(LinkedListDeque<T> other){
        setinel = new Node(null, null, null);
        setinel.prev = setinel;
        setinel.next = setinel;
        size = 0;
        for(int i=0; i < other.size(); i++){
            addLast(other.get(i));
        }
    }

    public void addFirst(T item){
        setinel.next.prev = new Node(item, setinel, setinel.next);
        setinel.next = setinel.next.prev;
        size += 1;
    }

    public void addLast(T item){
        setinel.prev.next = new Node(item, setinel.prev, setinel);
        setinel.prev = setinel.prev.next;
        size += 1;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void printDeque(){
        Node p = setinel.next;
        while(p != setinel){
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    public T removeFirst(){
        T removedNode = setinel.next.item;
        setinel.next.next.prev = setinel;
        setinel.next = setinel.next.next;
        size -= 1;
        return removedNode;
    }

    public T removeLast(){
        T removedNode = setinel.prev.item;
        setinel.prev.prev.next = setinel;
        setinel.prev = setinel.prev.prev;
        size -= 1;
        return removedNode;
    }

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

    public T getRecursiveHelper(Node p, int i){
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

//    public static void main(String[] args){
//        LinkedListDeque<Integer> t = new LinkedListDeque<>();
//        t.addFirst(1);
//        t.addFirst(2);
//        t.addFirst(3);
//        t.addFirst(4);
//        t.addLast(6);
//        t.addLast(7);
//        t.addLast(8);
//        System.out.println(t.isEmpty());
//        System.out.println(t.size());
//        t.printDeque();
//        System.out.println(t.removeFirst());
//        System.out.println(t.removeLast());
//        t.printDeque();
//        System.out.println(t.size());
//        System.out.println(t.get(4));
//        System.out.println(t.getRecursive(2));
//    }
}
