class Solution {
 public:
  vector<int> twoSum(vector<int>& nums, int target) {
    vector<int> ans;
    for (int i = 0; i < nums.size(); i++) {
      for (int j = i + 1; j < nums.size(); j++) {
        if (nums[i] + nums[j] == target) {
          ans.emplace_back(i);
          ans.emplace_back(j);
          break;
        }
      }
    }
    return ans;
  }
};

class Solution {
 public:
  vector<int> twoSum(vector<int>& nums, int target) {
    int i, j, n = nums.size(), sum = 0, l = 0, r = 0;
    for (i = 0; i < n - 1; i++) {
      for (j = 1; j < n; j++) {
        if (nums[i] + nums[j] == target) {
          l = l + i;
          r = r + j;
          vector<int> res = {l, r};
          return res;
        }
      }
    }
    vector<int> res = {l, r};
    return res;
  }
};