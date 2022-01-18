package org.evosuite.testsmells.smells;

import org.evosuite.Properties;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testsmells.AbstractTestSuiteSmell;
import org.evosuite.testsuite.TestSuiteChromosome;

public class LackOfCohesionOfMethods extends AbstractTestSuiteSmell {

    public LackOfCohesionOfMethods() {
        setSmellName("Lack of Cohesion of Methods");
    }

    @Override
    public int obtainSmellCount(TestSuiteChromosome chromosome) {
        int count = 0;
        Class<?> targetClass = Properties.getTargetClassAndDontInitialise();

        for(TestChromosome testCase : chromosome.getTestChromosomes()){
            if(!testCase.getTestCase().getAccessedClasses().contains(targetClass)){
                count++;
            }
        }

        return count;
    }
}
