class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        Set<String> seen = new HashSet<>();
        Set<String> result = new HashSet<>();
        //TC : O(n-10)*10 and SC : O(n-10)*10
        for(int i = 0; i<=s.length()-10; i++){
            String checked = s.substring(i,i+10);

            if(seen.contains(checked)){
                result.add(checked);
            }
            seen.add(checked);
        }

         List<String> ans = new ArrayList<>(result);

         return ans;
    }
}