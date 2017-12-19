package com.acme.cdi.translate;

import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.ArquillianTest;
import org.jboss.arquillian.junit.ArquillianTestClass;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

public class TextTranslatorServiceWithJUnitRuleTestCase {
    @ClassRule
    public static ArquillianTestClass arquillianTestClass = new ArquillianTestClass();

    @Rule
    public ArquillianTest arquillianTest = new ArquillianTest();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addPackage(TextTranslatorService.class.getPackage())
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    TextTranslatorService service;

    @Test
    public void should_spin_text() throws Exception {
        Assert.assertEquals("There's been a proposed surge of troops.",
            service.translate("There's been a proposed escalation of troops."));
    }
}
