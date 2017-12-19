package com.acme.ejb;

import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.ArquillianTest;
import org.jboss.arquillian.junit.ArquillianTestClass;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

public class GreeterJUnitRulesTestCase {
    @ClassRule
    public static ArquillianTestClass arquillianTestClass = new ArquillianTestClass();

    @Rule
    public ArquillianTest arquillianTest = new ArquillianTest();

    @Deployment
    public static JavaArchive createDeployment() {
        // explicit archive name required until ARQ-77 is resolved
        return ShrinkWrap.create(JavaArchive.class, "test.jar")
            .addClasses(Greeter.class, GreeterBean.class);
    }

    @EJB
    Greeter greeter;

    @Test
    public void shouldBeAbleToInjectEJBAndInvoke() throws Exception {
        String userName = "Earthlings";
        Assert.assertEquals("Hello, " + userName, greeter.greet(userName));
    }
}
