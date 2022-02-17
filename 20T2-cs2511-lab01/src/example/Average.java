package example;

public class Average {

        /**
         * Returns the average of an array of numbers
         * @param the array of integer numbers
         * @return the average of the numbers
         */
        public float average(int[] nums) {
            float result = 0;
            // Add your code
            for (int num: nums) {
                result += num;
            }
            return result/nums.length;
        }

        public static void main(String[] args) {
            // Add your code
            Average result = new example.Average();
            int[] nums = {2,4,6,7,8};
            float res = result.average(nums);
            System.out.println("average = " + res);
            
        }
}
