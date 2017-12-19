package com.acme.cdi;

import javax.enterprise.inject.spi.BeanManager;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class BeanManagerWithJUnitRulesTestCase {
    @ClassRule
    public static ArquillianTestClass arquillianTestClass = new ArquillianTestClass();

    @Rule
    public ArquillianTest arquillianTest = new ArquillianTest();

    @Deployment
    public static JavaArchive createTestArchive() {
        return ShrinkWrap.create(JavaArchive.class, "test.jar") // archive name optional
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    BeanManager beanManager;

    @Test
    public void testCdiBootstrap() {
        assertNotNull(beanManager);
        assertFalse(beanManager.getBeans(BeanManager.class).isEmpty());
        printCdiImplementationInfo(beanManager);
    }

    protected void printCdiImplementationInfo(BeanManager beanManager) {
        String impl = beanManager.getClass().getPackage().getImplementationTitle();
        if (impl == null) {
            impl = beanManager.getClass().getPackage().getImplementationVendor();
        }
        if (impl != null) {
            System.out.println("CDI implementation: " + impl.replaceFirst("^([^ ]+)( .*)?$", "$1"));
        }
        else {
            System.out.println("Could not determine CDI implementation");
        }
        System.out.println("BeanManager implementation class: " + beanManager.getClass().getName());
    }
}
