package org.evosuite.testsmells;

import com.examples.with.different.packagename.testsmells.TestSmellsSimpleUser;
import com.examples.with.different.packagename.testsmells.TestSmellsTestingClass1;
import org.evosuite.Properties;
import org.evosuite.assertion.PrimitiveAssertion;
import org.evosuite.symbolic.TestCaseBuilder;
import org.evosuite.testcase.DefaultTestCase;
import org.evosuite.testcase.statements.Statement;
import org.evosuite.testcase.variable.VariableReference;
import org.evosuite.testsmells.smells.LackOfCohesionOfMethods;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public class LackOfCohesionOfMethodsSmellTest {

    AbstractTestSmell lackOfCohesionOfMethods;

    @Before
    public void setUp() {
        Properties.TARGET_CLASS = TestSmellsTestingClass1.class.getCanonicalName();
        this.lackOfCohesionOfMethods = new LackOfCohesionOfMethods();
    }

    @Test
    public void testAllTestCasesPerformTestsOnTheClassUnderTest() throws NoSuchMethodException {
        TestSuiteChromosome suite = new TestSuiteChromosome();
        DefaultTestCase test0 = createTestCase0();
        DefaultTestCase test1 = createTestCase0();
        suite.addTest(test0);
        suite.addTest(test1);

        double computedMetric = this.lackOfCohesionOfMethods.computeTestSmellMetric(suite);
        double expectedComputedMetric = 0;
        assertEquals(expectedComputedMetric, computedMetric, 0.01);
    }

    @Test
    public void testOneTestCaseDoesNotPerformTestsOnTheClassUnderTest() throws NoSuchMethodException {
        TestSuiteChromosome suite = new TestSuiteChromosome();
        DefaultTestCase test0 = createTestCase0();
        DefaultTestCase test1 = createTestCase1();
        suite.addTest(test0);
        suite.addTest(test1);

        double computedMetric = this.lackOfCohesionOfMethods.computeTestSmellMetric(suite);
        double expectedComputedMetric = 0.5;
        assertEquals(expectedComputedMetric, computedMetric, 0.01);
    }

    @Test
    public void testMultipleTestCasesDoNotPerformTestsOnTheClassUnderTest() throws NoSuchMethodException {
        TestSuiteChromosome suite = new TestSuiteChromosome();
        DefaultTestCase test0 = createTestCase0();
        DefaultTestCase test1 = createTestCase1();
        DefaultTestCase test2 = createTestCase1();
        DefaultTestCase test3 = createTestCase1();
        suite.addTest(test0);
        suite.addTest(test1);
        suite.addTest(test2);
        suite.addTest(test3);

        double computedMetric = this.lackOfCohesionOfMethods.computeTestSmellMetric(suite);
        double expectedComputedMetric = 0.75;
        assertEquals(expectedComputedMetric, computedMetric, 0.01);
    }

    @Test
    public void testNoTestCasePerformsTestsOnTheClassUnderTest() throws NoSuchMethodException {
        TestSuiteChromosome suite = new TestSuiteChromosome();
        DefaultTestCase test0 = createTestCase1();
        DefaultTestCase test1 = createTestCase1();
        DefaultTestCase test2 = createTestCase1();
        DefaultTestCase test3 = createTestCase1();
        suite.addTest(test0);
        suite.addTest(test1);
        suite.addTest(test2);
        suite.addTest(test3);

        double computedMetric = this.lackOfCohesionOfMethods.computeTestSmellMetric(suite);
        double expectedComputedMetric = 0.8;
        assertEquals(expectedComputedMetric, computedMetric, 0.01);
    }

    @Test
    public void testOneTestCasePerformsTestsOnTwoClasses() throws NoSuchMethodException {
        TestSuiteChromosome suite = new TestSuiteChromosome();
        DefaultTestCase test0 = createTestCase0();
        DefaultTestCase test1 = createTestCase2();
        suite.addTest(test0);
        suite.addTest(test1);

        double computedMetric = this.lackOfCohesionOfMethods.computeTestSmellMetric(suite);
        double expectedComputedMetric = 0;
        assertEquals(expectedComputedMetric, computedMetric, 0.01);
    }

    @Test
    public void testOneTestCaseCreatesInstanceOfTheClassUnderTestButDoesNotPerformTests() throws NoSuchMethodException {
        TestSuiteChromosome suite = new TestSuiteChromosome();
        DefaultTestCase test0 = createTestCase0();
        DefaultTestCase test1 = createTestCase3();
        suite.addTest(test0);
        suite.addTest(test1);

        double computedMetric = this.lackOfCohesionOfMethods.computeTestSmellMetric(suite);
        double expectedComputedMetric = 0;
        assertEquals(expectedComputedMetric, computedMetric, 0.01);
    }

    @Test
    public void testOneTestCaseIsEmpty() throws NoSuchMethodException {
        TestSuiteChromosome suite = new TestSuiteChromosome();
        DefaultTestCase test0 = createTestCase0();
        DefaultTestCase test1 = createEmptyTestCase();
        suite.addTest(test0);
        suite.addTest(test1);

        double computedMetric = this.lackOfCohesionOfMethods.computeTestSmellMetric(suite);
        double expectedComputedMetric = 0.5;
        assertEquals(expectedComputedMetric, computedMetric, 0.01);
    }

    private DefaultTestCase createTestCase0() throws NoSuchMethodException {

        // Create test case

        TestCaseBuilder builder = new TestCaseBuilder();

        VariableReference stringStatement0 = builder.appendStringPrimitive("Bob");

        Constructor<TestSmellsTestingClass1> const0 = TestSmellsTestingClass1.class.getConstructor(String.class);
        VariableReference constructorStatement0 = builder.appendConstructor(const0, stringStatement0);

        VariableReference intStatement0 = builder.appendIntPrimitive(5);

        Method setNumberMethod0 = TestSmellsTestingClass1.class.getMethod("setNumber", int.class);
        builder.appendMethod(constructorStatement0, setNumberMethod0, intStatement0);

        Method getNumberMethod0 = TestSmellsTestingClass1.class.getMethod("getNumber");
        VariableReference methodStatement0 = builder.appendMethod(constructorStatement0, getNumberMethod0);

        DefaultTestCase testCase = builder.getDefaultTestCase();

        // Add assertions

        PrimitiveAssertion primitiveAssertion0 = new PrimitiveAssertion();
        primitiveAssertion0.setSource(methodStatement0);
        primitiveAssertion0.setValue(5);
        Statement currentStatement = testCase.getStatement(4);
        currentStatement.addAssertion(primitiveAssertion0);

        return testCase;
    }

    private DefaultTestCase createTestCase1() throws NoSuchMethodException {

        // Create test case

        TestCaseBuilder builder = new TestCaseBuilder();

        VariableReference stringStatement0 = builder.appendStringPrimitive("Bob");

        Constructor<TestSmellsSimpleUser> const0 = TestSmellsSimpleUser.class.getConstructor(String.class);
        VariableReference constructorStatement0 = builder.appendConstructor(const0, stringStatement0);

        Method getNameMethod0 = TestSmellsSimpleUser.class.getMethod("getName");
        VariableReference methodStatement0 = builder.appendMethod(constructorStatement0, getNameMethod0);

        DefaultTestCase testCase = builder.getDefaultTestCase();

        // Add assertions

        PrimitiveAssertion primitiveAssertion0 = new PrimitiveAssertion();
        primitiveAssertion0.setSource(methodStatement0);
        primitiveAssertion0.setValue("Bob");
        Statement currentStatement = testCase.getStatement(2);
        currentStatement.addAssertion(primitiveAssertion0);

        return testCase;
    }

    private DefaultTestCase createTestCase2() throws NoSuchMethodException {

        // Create test case

        TestCaseBuilder builder = new TestCaseBuilder();

        VariableReference stringStatement0 = builder.appendStringPrimitive("Bob");

        Constructor<TestSmellsTestingClass1> const0 = TestSmellsTestingClass1.class.getConstructor(String.class);
        VariableReference constructorStatement0 = builder.appendConstructor(const0, stringStatement0);

        VariableReference intStatement0 = builder.appendIntPrimitive(5);

        Method setNumberMethod0 = TestSmellsTestingClass1.class.getMethod("setNumber", int.class);
        builder.appendMethod(constructorStatement0, setNumberMethod0, intStatement0);

        Method getNumberMethod0 = TestSmellsTestingClass1.class.getMethod("getNumber");
        VariableReference methodStatement0 = builder.appendMethod(constructorStatement0, getNumberMethod0);

        Constructor<TestSmellsSimpleUser> const1 = TestSmellsSimpleUser.class.getConstructor(String.class);
        VariableReference constructorStatement1 = builder.appendConstructor(const1, stringStatement0);

        Method getNameMethod0 = TestSmellsSimpleUser.class.getMethod("getName");
        VariableReference methodStatement1 = builder.appendMethod(constructorStatement1, getNameMethod0);

        DefaultTestCase testCase = builder.getDefaultTestCase();

        // Add assertions

        Statement currentStatement;

        PrimitiveAssertion primitiveAssertion0 = new PrimitiveAssertion();
        primitiveAssertion0.setSource(methodStatement0);
        primitiveAssertion0.setValue(5);
        currentStatement = testCase.getStatement(4);
        currentStatement.addAssertion(primitiveAssertion0);

        PrimitiveAssertion primitiveAssertion1 = new PrimitiveAssertion();
        primitiveAssertion1.setSource(methodStatement1);
        primitiveAssertion1.setValue("Bob");
        currentStatement = testCase.getStatement(6);
        currentStatement.addAssertion(primitiveAssertion1);

        return testCase;
    }

    private DefaultTestCase createTestCase3() throws NoSuchMethodException {

        // Create test case

        TestCaseBuilder builder = new TestCaseBuilder();

        VariableReference stringStatement0 = builder.appendStringPrimitive("Bob");

        Constructor<TestSmellsTestingClass1> const0 = TestSmellsTestingClass1.class.getConstructor(String.class);
        builder.appendConstructor(const0, stringStatement0);

        return builder.getDefaultTestCase();
    }

    private DefaultTestCase createEmptyTestCase() {

        // Create test case

        TestCaseBuilder builder = new TestCaseBuilder();
        return builder.getDefaultTestCase();
    }
}
