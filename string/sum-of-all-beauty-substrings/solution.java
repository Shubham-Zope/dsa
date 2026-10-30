class Solution {
    public int beautySum(String s) {
        int beautySum = 0;

        for (int i = 0; i < s.length(); i++) {
            HashMap<Character, Integer> map = new HashMap<>();

            for (int j = i; j < s.length(); j++) {

                char ch = s.charAt(j);

                map.put(ch, map.getOrDefault(ch, 0) + 1);

                int maxCount = 0;
                int minCount = Integer.MAX_VALUE;

                for (int count : map.values()) {
                    maxCount = Math.max(maxCount, count);
                    minCount = Math.min(minCount, count);
                }

                beautySum += (maxCount - minCount);
            }
        }

        return beautySum;
    }
}
