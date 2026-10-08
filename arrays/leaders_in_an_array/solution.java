class slution {
    public List<Integer> leaders(int[] nums) {
        List<Integer> result =  new ArrayList<Integer>();

        int n = nums.length;
        int max = nums[n-1];
        result.add(max);
        for (int i=n-2;i>=0;i--) {
            if (nums[i] > max) {
                result.add(nums[i]);
                max = nums[i];
            }
        }
        
        Collections.reverse(result);

        return result;
    }
}
