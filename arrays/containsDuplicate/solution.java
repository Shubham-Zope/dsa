import java.util.HashSet;

public class solution {
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                return true; // Duplicate found
            }
            set.add(num);
        }
        return false; // No duplicates found
    }

    public static void main(String[] args) {
        solution answer = new solution();
        int[] nums = {1, 2, 3, 4, 1};
        boolean result = answer.containsDuplicate(nums);
        System.out.println(result);
    }
}