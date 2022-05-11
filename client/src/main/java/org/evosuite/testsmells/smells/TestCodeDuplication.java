package org.evosuite.testsmells.smells;

import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testsmells.AbstractNormalizedTestCaseSmell;

/**
 * Definition:
 * Unwanted duplication in the test code.
 *
 * Adaptation:
 * This metric uses the Levenshtein Distance to check whether there are repeated groups of similar statements
 * in a test case.
 *
 * Metric:
 * Verify whether a test case contains repeated groups of similar statements.
 *
 * Computation:
 * 1 - Iterate over the statements of a test case
 * [1: Start loop]
 * 2 - Get the String that represents the current statement
 * 3 - Iterate over the statements of the same test case, but initialize the loop with the value that follows the
 * current position of the outer loop.
 * [3: Start loop]
 * 4 - Get the String that represents the current statement of the inner loop
 * 5 - Use the Levenshtein Distance to calculate the similarities between the two statements
 * 6 - If the two statements are at least 80% similar: increment the smell counter
 * [3: End loop]
 * [1: End loop]
 * 7 - Return the smell counter
 */
public class TestCodeDuplication extends AbstractNormalizedTestCaseSmell {

    private static final long serialVersionUID = -4589622102567812843L;

    public TestCodeDuplication() {
        super("TestSmellTestCodeDuplication");
    }

    @Override
    public long computeNumberOfTestSmells(TestChromosome chromosome) {
        int size = chromosome.size();
        long count = 0;

        int dist;
        double similar;

        String currentStatementString;
        String compareString;

        TestCase testCase = chromosome.getTestCase();

        for(int i = 0; i < size; i++){
            currentStatementString =  testCase.getStatement(i).toString();
            for(int j = i + 1; j < size; j++){
                compareString = testCase.getStatement(j).toString();
                dist = getLevenshteinDistance(currentStatementString, compareString);
                similar = 1 - (dist / (double) Math.max(currentStatementString.length(), compareString.length()));
                if(similar > 0.8){
                    count ++;
                }
            }
        }

        return count;
    }

    private int getLevenshteinDistance(String originalString, String newString){
        //Levenshtein distance- simple (no weights, no recorded edit transcript)

        int[][] d= new int[originalString.length()+1][newString.length()+1];

        for(int i=1; i<originalString.length()+1; i++)
            d[i][0]=i;

        for(int j=1; j<newString.length()+1; j++)
            d[0][j]=j;

        for(int j=1; j<newString.length()+1; j++){
            for(int i=1; i<originalString.length()+1; i++){

                if(originalString.charAt(i-1)==newString.charAt(j-1)){
                    d[i][j]= d[i-1][j-1];			//if match, cost=0
                }else{
                    d[i][j]= Math.min(d[i][j-1]+1, 	//insertion,  cost=1
                            Math.min(d[i-1][j-1]+1, 	//substitution
                                    d[i-1][j]+1));    //deletion
                }
            }

        }//for
        return d[originalString.length()][newString.length()];

    }//getDistance
}
