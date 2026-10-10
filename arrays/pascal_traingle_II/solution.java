class solution {
    public List<Integer> getRow(int rowIndex) {

        ArrayList<Integer> ans = new ArrayList<>();

        int n = rowIndex;
        long value = 1;
        ans.add(1);
        
        for(int i = 1; i <= n; i++) {

            value = value * (n - i + 1);
            value = value / i;

            ans.add((int)value);
        }
        
        return ans;
    }
}
