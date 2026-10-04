public class solution {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false; // If lengths are different, they cannot be anagrams
        }

        int[] count = new int[26]; // Assuming only lowercase letters

        for (char c : s.toCharArray()) {
            count[c - 'a']++; // Increment count for each character in s
        }

        for (char c : t.toCharArray()) {
            count[c - 'a']--; // Decrement count for each character in t
            if (count[c - 'a'] < 0) {
                return false; // If any count goes negative, they are not anagrams
            }
        }

        return true; // All counts are zero, they are anagrams
    }

    public static void main(String[] args) {
        solution answer = new solution();
        String s = "rat";
        String t = "car";
        boolean result = answer.isAnagram(s, t);
        System.out.println(result);
    }
}
