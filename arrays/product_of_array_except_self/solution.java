class solution {
    public int[] productExceptSelf(int[] nums) {
        int prefix=1;
        int postfix=1;

        int [] answer=new int[nums.length];
        for(int i=0;i<nums.length;i++){
            answer[i]=prefix;
            prefix=prefix*nums[i];
        }
        for(int i=nums.length-1;i>=0;i--){
            answer[i]=answer[i]*postfix;
            postfix=postfix*nums[i];
        }
        return answer;
    }

    public static void main(String[] args) {
        solution sol = new solution();
        int[] nums = {1, 2, 3, 4};
        int[] result = sol.productExceptSelf(nums);
        for (int i : result) {
            System.out.print(i + " ");
        }
    }
}
