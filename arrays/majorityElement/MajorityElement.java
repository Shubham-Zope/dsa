public class MajorityElement {
    public int majorityElement(int[] nums) {
        int count = 0;
        Integer candidate = null;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }

    public static void main(String[] args) {
        MajorityElement answer = new MajorityElement();
        int[] nums = {3, 2, 3};
        int result = answer.majorityElement(nums);
        System.out.println(result); // Output: 3
    }
}
