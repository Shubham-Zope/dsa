public class solution {
    int[] prefix;
    int n;

    public void NumArray(int[] nums) {
        n = nums.length;
        prefix = new int[n];
        prefix[0]=nums[0];

        for(int i=1;i<n;i++){
            prefix[i] = prefix[i-1]+nums[i];
        }
    }
    
    public int sumRange(int left, int right) {
       if(left>0) return prefix[right]-prefix[left-1];
       return prefix[right];
    }

    public static void main(String[] args) {
        solution answer = new solution();
        int[] nums = {-2, 0, 3, -5, 2, -1};
        answer.NumArray(nums);
        int left = 0;
        int right = 2;
        int result = answer.sumRange(left, right);
        System.out.println(result); // Output: 1
    }
}
