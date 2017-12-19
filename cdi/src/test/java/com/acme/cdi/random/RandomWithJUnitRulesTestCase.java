package com.acme.cdi.random;

import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.ArquillianTest;
import org.jboss.arquillian.junit.ArquillianTestClass;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RandomWithJUnitRulesTestCase {
    @ClassRule
    public static ArquillianTestClass arquillianTestClass = new ArquillianTestClass();

    @Rule
    public ArquillianTest arquillianTest = new ArquillianTest();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addPackage(Random.class.getPackage())
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    @Random
    int randomNumber;

    @Test
    public void shouldGenerateRandomNumber() throws Exception {
        System.out.println("Generated " + randomNumber);
        assertTrue(randomNumber >= 0);
        assertTrue(randomNumber <= 100);
    }
}
