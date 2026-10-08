class solution {
    public int[] rearrangeArray(int[] nums) {
        int pos = 0;
        int neg = 1;

        while (pos < nums.length && neg < nums.length) {

            while (pos < nums.length && nums[pos] > 0) {
                pos += 2;
            }

            while (neg < nums.length && nums[neg] < 0) {
                neg += 2;
            }

            if (pos < nums.length && neg < nums.length) {
                swap(nums, pos, neg);
                pos += 2;
                neg += 2;
            }
        }

        return nums;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
