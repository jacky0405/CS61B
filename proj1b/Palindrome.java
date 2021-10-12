public class Palindrome {

    public Deque<Character> wordToDeque(String word){
        LinkedListDeque<Character> t = new LinkedListDeque();
        for(int i=0; i<word.length(); i++){
            t.addLast(word.charAt(i));
        }
        return t;
    }

    public boolean isPalindrome(String word){
        Deque<Character> t = wordToDeque(word);

        while(t.size() > 1){
            if(t.removeFirst() != t.removeLast()){
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> t = wordToDeque(word);

        while(t.size() > 1){
            if( !cc.equalChars(t.removeFirst(),t.removeLast()) ){
                return false;
            }
        }
        return true;
    }

}
