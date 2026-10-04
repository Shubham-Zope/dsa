import java.util.Arrays;
import java.util.HashMap;

public class solution {

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> set = new HashMap<>();
        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int complement = target - num;
            if (set.containsKey(complement)) {
                result[0] = set.get(complement);
                result[1] = i;
                return result; // Found a pair that sums to the target
            }
            set.put(num, i);
        }
        return result; // No pair found
    }


    public static void main(String[] args) {
        solution answer = new solution();
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result = answer.twoSum(nums, target);
        System.out.println(Arrays.toString(result)); // Output: [2, 7]
    }
}