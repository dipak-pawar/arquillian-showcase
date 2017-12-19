package com.acme.ejb;

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
import static org.junit.Assert.assertTrue;

public class TemperatureConverterJUnitRulesTestCase {

    @ClassRule
    public static ArquillianTestClass arquillianTestClass = new ArquillianTestClass();

    @Rule
    public ArquillianTest arquillianTest = new ArquillianTest();

    @Deployment
    public static JavaArchive createDeployment() {
        // explicit archive name required until ARQ-77 is resolved
        return ShrinkWrap.create(JavaArchive.class, "test.jar")
            .addClasses(TemperatureConverter.class, TemperatureConverterBean.class);
    }

    @EJB
    TemperatureConverter converter;

    @Test
    public void testConvertToCelsius() {
        assertEquals(converter.convertToCelsius(32d), 0d, 0d);
        assertEquals(converter.convertToCelsius(212d), 100d, 0d);
        converter.isTransactionActive();
    }

    @Test
    public void testConvertToFarenheit() {
        assertEquals(converter.convertToFarenheit(0d), 32d, 0d);
        assertEquals(converter.convertToFarenheit(100d), 212d, 0d);
    }

    @Test
    public void testTransactionActive() {
        assertTrue(converter.isTransactionActive());
    }

}
