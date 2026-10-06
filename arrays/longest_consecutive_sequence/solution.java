class solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            set.add(num);
        }

        int longest = 0;

        for (int num : set) {
            // Start only if this is the beginning of a sequence
            if (!set.contains(num - 1)) {
                int current = num;
                int count = 0;

                while (set.contains(current)) {
                    current++;
                    count++;
                }

                longest = Math.max(longest, count);
            }
        }

        return longest;
    }
}
