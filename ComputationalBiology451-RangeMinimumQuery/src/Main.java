public class Main {
    static int[][] sparseTable;

    public static void main(String[] args) {
        int[] arr = {9,8,7,6,5,4,3,2,1};
        int[] milli = new int[1000000];
        int left = 0;
        int right = 2;
        System.out.println("Query 1: Sorted Array");
        System.out.print("Array: ");
        printArray(arr);
        System.out.println();
        populateSparseTable(arr);
        System.out.println("Sparse Table: ");
        printSparseTable(arr);
        System.out.println("Query Range: ("+left+","+right+")");
        System.out.println("Minimum: " +rangeQuery(arr, left, right));

        int[] arr2 = {5,9,21,4,7,6,0,10,8,99,103,2,3};
        left = 1;
        right = 6;
        System.out.println();
        System.out.println("Query 2: Random Array");
        System.out.print("Array: ");
        printArray(arr2);
        System.out.println();
        populateSparseTable(arr2);
        System.out.println("Sparse Table: ");
        printSparseTable(arr2);
        System.out.println("Query Range: ("+left+","+right+")");
        System.out.println("Minimum: " +rangeQuery(arr2, left, right));


        for(int i = 0; i < 1000000; i++) {
            milli[i] = (int)(Math.random()*100+5);
        }
        left = 0;
        right = 70000;
        System.out.println();
        System.out.println("Query 3: Array with a million values");
        System.out.println("Did not print sparse table because of size");
        populateSparseTable(milli);
        System.out.println("Query Range: ("+left+","+right+")");
        System.out.println("Minimum: " +rangeQuery(milli, left, right));

    }

    private static void populateSparseTable(int[] array) {
        int length = array.length;
        int floor = logBaseTwo(length)+1;
        sparseTable = new int[length][floor];

//        Populate first row of the sparse table with input values
        for(int i = 0; i <length; i++) {
            sparseTable[i][0] = i;
        }


//                    Calculate the minimum value in the range from i to i+2^j
//                    Dynamically split range in half
//                    Then, calculate the minimum value of each half
//                    Then, compare the minimum values of each half
//                    Whichever one is smaller, we store the index of that value
//                    in our sparseTable at (i,j)
        for(int j = 1; Math.pow(2, j) <= length; j++) {
            for(int i = 0; (i + Math.pow(2, j))  <= length; i++) {
                if (array[sparseTable[i][j - 1]] < array[sparseTable[i + (int)(Math.pow(2, j-1))][j - 1]]) {
                    sparseTable[i][j] = sparseTable[i][j - 1];
                }
                else {
                    sparseTable[i][j] = sparseTable[i + (int) (Math.pow(2, j - 1))][j - 1];
                }
            }
        }


    }
//    Function that returns the minimum value for a given range of indexes
    private static int rangeQuery(int[] array, int l, int r) {
        int floor = logBaseTwo(r-l+1);
//    Split query into two ranges and checks which is smaller
        if (array[sparseTable[l][floor]] <= array[sparseTable[r - (int) (Math.pow(2, floor)) + 1][floor]]) {
            return array[sparseTable[l][floor]];
        } else {
            return array[sparseTable[r - (int) (Math.pow(2, floor)) + 1][floor]];
        }


    }
// We had to implement this function as Java does not have a built in Log base 2
    private static int logBaseTwo(int x)
    {
        return (int)(Math.log(x)/Math.log(2));
    }

//    Easliy print the sparse table for debugging
    private static void printSparseTable(int[] array) {
        int length = array.length;
        int floor = logBaseTwo(length)+1;
        for(int i = 0; i <length; i++) {
            for(int j = 0; j < floor; j++) {
                System.out.print(sparseTable[i][j]+ " ");
            }
            System.out.println();
        }
    }

    private static void printArray(int[] array) {
        int length = array.length;
        for(int i = 0; i <length; i++) {
            System.out.print(array[i] + " ");
        }
    }
}
