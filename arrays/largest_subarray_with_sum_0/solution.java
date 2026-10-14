import java.util.HashMap;

class Solution {
    public int maxLen(int[] arr) {
        HashMap<Integer, Integer> prefix = new HashMap<>();

        int sum = 0;
        int maxLength = 0;

        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];

            if (sum == 0) {
                maxLength = i + 1;
            }

            if (prefix.containsKey(sum)) {
                maxLength = Math.max(maxLength, i - prefix.get(sum));
            } else {
                prefix.put(sum, i);
            }
        }

        return maxLength;
    }
}
