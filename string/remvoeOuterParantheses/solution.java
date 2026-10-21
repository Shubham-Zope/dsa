class Solution {
    public String removeOuterParentheses(String s) {
        StringBuilder result = new StringBuilder();
        int stack = 0;

        for (char ch : s.toCharArray()) {
            if (ch == '(') {
                if (stack > 0) {
                    result.append(ch);
                }
                stack++;
            } else {
                stack--;
                if (stack > 0) {
                    result.append(ch);
                }
            }
        }

        return result.toString();
    }
}
