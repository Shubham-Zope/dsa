class solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int prefixSum = 0;

        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(0, 1);

        for (int num : nums) {
            prefixSum += num;

            int required = prefixSum - k;

            if (map.containsKey(required)) {
                count += map.get(required);
            }

            map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
        }

        return count;
    }
}
