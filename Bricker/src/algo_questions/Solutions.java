package algo_questions;

import java.util.Arrays;

import static java.lang.Math.min;


public class Solutions {
    public Solutions() {
    }


    /**
     * runtime nlog(n)+mlog(m)+n*m
     * Method computing the maximal amount of tasks out of n tasks that
     * can be completed with m time slots.
     * A task can only be completed in a time slot if the length of the time
     * slot is grater than the no. of hours needed to complete the task.
     *
     * @param tasks     - array of integers of length n.
     *                  tasks[i] is the time in hours required to complete task i.
     * @param timeSlots - array of integers of length m. timeSlots[i] is the length in hours of the slot i.
     * @return - array of integersof length m. timeSlots[i] is the length in hours of the slot i.
     */
    public static int alotStudyTime(int[] tasks, int[] timeSlots) {
        int numTasksCompleted = 0;
        Arrays.sort(tasks);
        Arrays.sort(timeSlots);
        for (int m = 0; m < timeSlots.length; m++) {
            for (int n = 0; n < tasks.length; n++) {
                if (tasks[n] <= timeSlots[m] && tasks[n] != -1) {
                    numTasksCompleted++;
                    tasks[n] = -1;
                    break;
                }
            }
        }
        return numTasksCompleted;
    }

    /**
     * Method computing the nim amount of leaps a frog needs to jump across n waterlily leaves,
     * from leaf 1 to leaf n. The leaves vary in size and how stable they are, so some leaves allow larger
     * leaps than others. leapNum[i] is an integer telling you how many leaves ahead you can jump from leaf i.
     * If leapNum[3]=4, the frog can jump from leaf 3, and land on any of the leaves 4, 5, 6 or 7.
     * @param leapNum - array with num jumps for leap
     * @return - minimal number of leaps to last leaf.
     */
    public static int minLeap(int[] leapNum) {
        int currentLeap = 0;
        int maxStep = 0,currentStep,bestStep=0,numSteps=0;
        while(currentLeap < leapNum.length -1){
            for (int step = 1; step <= leapNum[currentLeap]; step++) {
                currentStep = currentLeap + step;
                if(leapNum.length - 1 <= currentStep){
                    bestStep= step;
                    break;
                }
                if(currentStep < leapNum.length-1){
                    currentStep += leapNum[currentStep];
                }
                if(maxStep < currentStep){
                    maxStep = currentStep;
                    bestStep = step;
                }
            }
            currentLeap += bestStep;
            numSteps+=1;
            maxStep = 0;
        }
        return numSteps;
    }

    /**
     * A boy is filling the water trough for his father's cows in their village.
     * The trough holds n liters of water. With every trip to the village well,
     * he can return using either the 2 bucket yoke, or simply with a single bucket. A bucket holds 1 liter.
     * In how many different ways can he fill the water trough? n can be assumed
     * to be greater or equal to 0, less than or equal to 48.
     * @param n - num of litters
     * @return valid output of algorithm.
     */
    public static int bucketWalk(int n) {

        int[] numWaysForLitters = new int[n+1];
        if (n == 0){ return 1; }
        numWaysForLitters[0] = 1;
        numWaysForLitters[1] = 1;
        for (int i = 2; i <= n; i++) {
            numWaysForLitters[i] = numWaysForLitters[i-1] + numWaysForLitters[i-2];
        }
        return numWaysForLitters[n];


    }

    /**
     *  Given an integer n, return the number of structurally unique
     *  BST's (binary search trees) which has exactly n nodes of unique values from 1 to n.
     *  You can assume n is at least 1 and at most 19.
     * @param n - num trees
     * @return valid output of algorithm.
     */
    public static int numTrees(int n) {
        int[] helperArray = new int[n+1];
        helperArray[0]=1;
        helperArray[1]=1;
        for (int i = 2; i < n+1; i++) {
            for (int j = 1; j < i+1; j++) {
                helperArray[i] += helperArray[j-1]*helperArray[i-j];
            }

        }
        return helperArray[n];
    }


}