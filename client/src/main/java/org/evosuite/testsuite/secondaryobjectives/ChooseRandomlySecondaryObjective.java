package org.evosuite.testsuite.secondaryobjectives;

import org.evosuite.ga.SecondaryObjective;
import org.evosuite.testsuite.TestSuiteChromosome;

import java.util.Random;

public class ChooseRandomlySecondaryObjective extends SecondaryObjective<TestSuiteChromosome> {

    private static final long serialVersionUID = -3687478373591441255L;

    private final Random random = new Random();

    @Override
    public double compareChromosomes(TestSuiteChromosome chromosome1, TestSuiteChromosome chromosome2) {
        return random.nextBoolean() ? -1 : 1;
    }

    @Override
    public double compareGenerations(TestSuiteChromosome parent1, TestSuiteChromosome parent2, TestSuiteChromosome child1, TestSuiteChromosome child2) {
        return random.nextBoolean() ? -1 : 1;
    }
}
