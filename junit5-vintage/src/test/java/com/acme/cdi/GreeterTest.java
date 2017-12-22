package com.acme.cdi;

import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.ArquillianTest;
import org.jboss.arquillian.junit.ArquillianTestClass;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.ComparisonFailure;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class GreeterTest {

    @ClassRule
    public static ArquillianTestClass arquillianTestClass = new ArquillianTestClass();

    @Rule
    public ArquillianTest arquillianTest = new ArquillianTest();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Inject
    Greeter greeter;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(Greeter.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void should_greet_earthlings() throws Exception {
        String name = "Earthlings";
        Assert.assertEquals("Hello, " + name, greeter.greet(name));
    }

    @Test
    public void should_fail() throws Exception {
        String name = "Earthlings";
        thrown.expect(ComparisonFailure.class);
        Assert.assertEquals(" " + name, greeter.greet(name));
    }


    @Test@Ignore
    public void should_skip() throws Exception {
        String name = "Earthlings";
        Assert.assertEquals("" + name, greeter.greet(name));
    }

}
