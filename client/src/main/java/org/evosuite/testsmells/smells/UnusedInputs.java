package org.evosuite.testsmells.smells;

import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.statements.MethodStatement;
import org.evosuite.testsmells.AbstractTestSmell;
import org.evosuite.testcase.statements.Statement;

public class UnusedInputs extends AbstractTestSmell {

    public UnusedInputs(String smellName) {
        super(smellName);
    }

    @Override
    public int obtainSmellCount(TestChromosome chromosome) {
        int size = chromosome.size();
        int count = 0;

        Statement currentStatement;

        for (int i = 0; i < size; i++){
            currentStatement = chromosome.getTestCase().getStatement(i);

            if(currentStatement instanceof MethodStatement){
                count += currentStatement.hasAssertions() ? 0 : 1;
            }
        }
        return count;
    }
}