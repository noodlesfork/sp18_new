public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> A = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i++) {
            Character C = word.charAt(i);
            A.addLast(C);
        }
        return A;
    }

    public boolean isPalindrome(String word) {
        if (word == null || word.isEmpty()) {
            return true;
        }
        Deque<Character> A = wordToDeque(word);
        while(A.size() > 1) {
            if (A.removeFirst() != A.removeLast()) {
                return false;
            }
        }
        return true;

    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        int len = word.length();
        if (len <= 1) {
            return true;
        }
        Deque<Character> A = wordToDeque(word);
        Deque<Character> B = wordToDeque(word);
        for (int i = 0; i < Math.floorDiv(len, 2); i++) {
            if (!cc.equalChars(A.removeFirst(), B.removeLast())) {
                return false;
            }
        }
        return true;
    }
}
