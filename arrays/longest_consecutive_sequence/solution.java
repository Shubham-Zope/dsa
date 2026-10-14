
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int longestConsecutive(int[] nums) {

        // Base case
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // Find minimum and maximum
        int min = nums[0];
        int max = nums[0];

        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        // Approach 1: Boolean Array for smaller ranges
        if ((long) max - min <= 100000) {

            boolean[] b = new boolean[max - min + 1];

            // Mark numbers that exist
            for (int num : nums) {
                b[num - min] = true;
            }

            int longest = 0;
            int cnt = 0;

            // Find the longest consecutive sequence
            for (boolean val : b) {
                if (val) {
                    cnt++;
                } else {
                    longest = Math.max(longest, cnt);
                    cnt = 0;
                }
            }

            // Handle a sequence ending at the last index
            return Math.max(longest, cnt);
        }

        // Approach 2: HashSet for larger ranges
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;

        for (int num : set) {

            // Start counting only at the beginning
            // of a consecutive sequence
            if (num == Integer.MIN_VALUE || !set.contains(num - 1)) {

                int curr = num;
                int len = 1;

                while (curr != Integer.MAX_VALUE
                        && set.contains(curr + 1)) {
                    curr++;
                    len++;
                }

                longest = Math.max(longest, len);
            }
        }

        return longest;
    }
}
