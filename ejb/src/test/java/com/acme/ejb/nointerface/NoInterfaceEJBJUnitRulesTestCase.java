package com.acme.ejb.nointerface;

import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.ArquillianTest;
import org.jboss.arquillian.junit.ArquillianTestClass;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NoInterfaceEJBJUnitRulesTestCase {
    @ClassRule
    public static ArquillianTestClass arquillianTestClass = new ArquillianTestClass();

    @Rule
    public ArquillianTest arquillianTest = new ArquillianTest();

    @Deployment
    public static JavaArchive createDeployment() {
        // explicit archive name required until ARQ-77 is resolved
        return ShrinkWrap.create(JavaArchive.class, "test.jar").addClass(NoInterfaceEJB.class);
    }

    @EJB
    NoInterfaceEJB ejb;

    // NOTE fails on OpenEJB
    @Test
    public void shouldBeAbleToResolveAndInvokeNoInterfaceEJB() throws Exception {
        assertNotNull("Verify that the ejb was injected", ejb);

        assertEquals("Verify that the ejb returns correct value", "pong", ejb.ping());
    }
}
