public class Palindrome {

    public Deque<Character> wordToDeque(String word){
        if(word == null){
            return null;
        }
        LinkedListDeque<Character> t = new LinkedListDeque();
        for(int i=0; i<word.length(); i++){
            t.addLast(word.charAt(i));
        }
        return t;
    }

    public boolean isPalindrome(String word){
        Deque t = wordToDeque(word);
        if(t == null){
            return true;
        }
        while(t.size() > 1){
            if(t.removeFirst() != t.removeLast()){
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque t = wordToDeque(word);
        if(t == null){
            return true;
        }
        while(t.size() > 1){
            if( !cc.equalChars((char)t.removeFirst(),(char)t.removeLast()) ){
                return false;
            }
        }
        return true;
    }

}
