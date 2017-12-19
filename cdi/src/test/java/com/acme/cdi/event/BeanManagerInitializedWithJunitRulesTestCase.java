package com.acme.cdi.event;

import javax.enterprise.inject.spi.Extension;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.ArquillianTest;
import org.jboss.arquillian.junit.ArquillianTestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class BeanManagerInitializedWithJunitRulesTestCase {

    @ClassRule
    public static ArquillianTestClass arquillianTestClass = new ArquillianTestClass();

    @Rule
    public ArquillianTest arquillianTest = new ArquillianTest();

    @Deployment
    public static Archive<?> createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClasses(ApplicationInitializer.class, BeanManagerInitializedExtension.class, Initialized.class)
            .addAsServiceProvider(Extension.class, BeanManagerInitializedExtension.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void should_observe_startup_event() {
        assertTrue(ApplicationInitializer.RECEIVED_STARTUP_EVENT);
    }

}
