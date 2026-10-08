# Algorithms: iAnalytics

Coursework for Introduction to Algorithms module. A Java class of operations on integer arrays, tested on datasets from tiny to large.

## Methods

| Method | What it returns |
|---|---|
| `countUnique` | Number of distinct values |
| `leastFrequent` | The value that appears least often |
| `countLess` | How many values are below a number |
| `countBetween` | How many values fall in a range |
| `topKFrequent` | The k most frequent values |
| `longestAscSubarray` | The longest run of increasing values |
| `maxSubarraySum` | The largest sum of any k consecutive values |

## Running it

```bash
cd iAnalytics
javac *.java
java iTest
```

`iTest` runs every method on the four datasets in `data/` (tiny, small, medium and large).
