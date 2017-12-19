package com.acme.cdi.measurement;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class TemperatureConverterWithJUnitRulesTestCase {

    @ClassRule
    public static ArquillianTestClass arquillianTestClass = new ArquillianTestClass();

    @Rule
    public ArquillianTest arquillianTest = new ArquillianTest();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(TemperatureConverter.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    TemperatureConverter converter;

    @Test
    public void testConvertToCelsius() {
        assertThat(converter.convertToCelsius(32d), equalTo(0d));
        assertThat(converter.convertToCelsius(212d), equalTo(100d));
    }

    @Test
    public void testConvertToFarenheit() {
        assertThat(converter.convertToFarenheit(0d), equalTo(32d));
        assertThat(converter.convertToFarenheit(100d), equalTo(212d));
    }
}
