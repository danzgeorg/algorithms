/**
 * Class of operations on integer arrays.
 * You MUST NOT change the signatures of the methods supplied. 
 */
 
// IN1002 Introduction to Algorithms
// Coursework 2024/2025
//
// Submission by
// Daniel Georgiev
// dan.georgiev@city.ac.uk

public class iAnalytics {

    
	// Task 1: Count unique elements in an ordered array
    // Time complexity is O(n) where n is the length of the array
    public int countUnique(int[] arr) {

        // boundary case where the array is empty
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int count = 1; // count = 1 because the 1st element is always unique

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[i - 1]) { // if the element is differnet from the previous its unique
                count++;
            }
        }
        return count;
    }


    // Task 2: Find least frequent value in an ordered array
    // Time complexity of O(n) where n is the length of the array
    public int leastFrequent(int[] arr) {
        // boundary cases where the array is empty or has 1 element
        if (arr == null || arr.length == 0) {
            return 0;
        } else if (arr.length == 1) {
            return arr[0]; // single element array means its the least frequent
        }

        int leastFrequentValue = arr[0]; // first element is the smallest at the beginning of test
        int minFrequency = Integer.MAX_VALUE; // maximum possible frequency to start with

        int currValue = arr[0];
        int currFequency = 1;

        for (int i = 1; i <= arr.length; i++) {
            if (i == arr.length || arr[i] != currValue) { // check for end of array or a new value
                if (currFequency < minFrequency) { // check for the current frequency being smaller than minimum frequency
                    minFrequency = currFequency;
                    leastFrequentValue = currValue;
                }

                if (i < arr.length){ // check for not being at the end of the array, update current value and reset frequency if true
                    currValue = arr[i];
                    currFequency = 1;
                }
            } else {
                currFequency++; // same value so increment frequency
            }
        }
        return leastFrequentValue;
    }


    // Task 3: Count elements in an ordered array less than num
    // Time complexity of O(log n) since I am using binary search to find the insertion point
    public int countLess(int[] arr, int num) {
        // boundary cases where the array is null or empty
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // array is already sorted so I can just use binary search to find the position where num would be inserted
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == num) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left; // left being the index where num is inserted which = the count of elements smaller than num
    }
	

    // Task 4: Count elements in an ordered array between low and high
    // Time complexity of O(log n) since I am using binary search twice to find both bounds
    public int countBetween(int[] arr, int low, int high) {
        // boundary cases where the array is null or empty
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // find the position of the smallest element that's bigger than or equal to low
        int left = 0;
        int right = arr.length - 1;
        int lowerBound = arr.length; // default value if no element is >= low

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == low){
                lowerBound = mid; // found a potential lower bound
                right = mid -1; // a potentially earlier occurence
            } else {
                left = mid + 1;
            }
        }
        // if no element is >= low return 0
        if (lowerBound == arr.length) {
            return 0;
        }

        // re-set the values for the 2nd binary search
        left = 0;
        right = arr.length - 1;
        int upperBound = -1; // default if no element is <= high

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] <= high){
                upperBound = mid; // found a potential uppper bound
                left = mid + 1; // a potentially later occurence
            } else {
                right = mid - 1;
            }
        }

        // if no element is <= high return 0
        if (upperBound == -1) {
            return 0;
        }
        // calculate count between the bounds (range + 1 essentially)
        return upperBound - lowerBound + 1;
    }
	

    // Task 5: Find top K most frequent elements in an ordered array
    // Time complexity is O(n + dlog d?) where n is the lenfth of the array and d being the number of different values
    public int[] topKFrequent(int[] arr, int k) {
        // boundary cases where array is null or empty or k is <= 0
        if (arr == null || arr.length == 0 || k <= 0) {
            return new int[0];
        }

        // count frequencies of each value in the array (only one pass needed since array is sorted)
        int differentCount = 0;
        int[] values = new int[arr.length]; // different values array
        int[] frequencies = new int[arr.length]; // stores their frequencues

        int currValue = arr[0];
        int currFrequency = 1;

        for (int i = 1; i <= arr.length; i++) {
            // when end is reached or found a new value
            if (i == arr.length || arr[i] != currValue) {
                values[differentCount] = currValue;
                frequencies[differentCount] = currFrequency;
                differentCount++;

                if (i < arr.length) {
                    currValue = arr[i];
                    currFrequency = 1;
                }
            } else {
                currFrequency++;
            }
        }

        // sort values by frequency (descending) and by value (ascending)
        for (int i = 0; i < differentCount - 1; i++) {
            for (int j = 0; j < differentCount - i - 1; j++) {

                // if j has lower frequency or (same frequency but higher value)
                if (frequencies[j] > frequencies[j + 1] ||
                        frequencies[j] == frequencies[j + 1] && values[j] > values[j + 1]) {

                    // swap frequencues
                    int tempFreq = frequencies[j];
                    frequencies[j] = frequencies[j + 1];
                    frequencies[j + 1] = tempFreq;

                    // swap values
                    int tempVal = values[j];
                    values[j] = values[j + 1];
                    values[j + 1] = tempVal;
                }
            }
        }

        // return the top k most frequent values (or all if < k)
        int resultLength = Math.min(k,differentCount);
        int[] result = new int[resultLength];

        for (int i = 0; i < resultLength; i++) {
            result[i] = values[i];
        }

        return result;
    }

    // Task 6: Longest contiguous subarray in ascending order
    // Time complexity is O(n) where n is the legnth of the array
    public int[] longestAscSubarray(int[] arr) {
        // boundary cases where array is empty, null, or single element
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        if (arr.length == 1) {
            return new int[] { arr[0] };
        }

        int currStart = 0; // index of current ascending sequence (CAS)
        int currLength = 1; // length of CAS

        int maxStart = 0; // index of longest ascending sequence (LAS)
        int maxLength = 1; // length of LAS

        // finding the longest ascending subarray
        for (int i = 1; i < arr.length; i++) {
            // if current element continues the CAS
            if (arr[i] > arr[i - 1]) {
                currLength++;

                // update max if CAS becomes longer
                if (currLength > maxLength) {
                    maxLength = currLength;
                    maxStart = currStart;
                }
            } else {
                // CAS is broken so start a new sequence from current index
                currStart = i;
                currLength = 1;
            }
        }

        // create and return the result subarray
        int[] result = new int[maxLength];
        for (int i = 0; i < maxLength; i++) {
            result[i] = arr[maxStart + i];
        }

        return result;
    }


    // Task 7: Maximum sum of a contiguous subarray with exactly k elements
    //Time complexity is O(n) where n is the length of the array
    public int maxSubarraySum(int[] arr, int k) {
        // boundary cases where empty or null array
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // if array has fewer than k elements, return sum of whole array
        if (arr.length < k) {
            int sum = 0;
            for (int i = 0; i < arr.length; i++) {
                sum += arr[i];
            }
            return sum;
        }

        int currSum = 0;

        // calculate sum of first k-1 elements
        for (int i = 0; i < k-1; i++) {
            currSum += arr[i];
        }
        int maxSum = currSum;

        // slide window and find maximum sum
        for (int i = k-1; i < arr.length; i++) {
            // add current elements to window
            currSum += arr[i];

            // update maxSum if currSum >
            if (currSum > maxSum) {
                maxSum = currSum;
            }

            // remove the leftmost element from the window
            if (i >= k -1){
                currSum -= arr[i] - (k-1);
            }
        }
        return maxSum;
    }
}
