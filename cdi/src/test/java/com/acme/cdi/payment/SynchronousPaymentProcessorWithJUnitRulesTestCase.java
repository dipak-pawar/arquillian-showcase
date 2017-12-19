package com.acme.cdi.payment;

import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.ArquillianTest;
import org.jboss.arquillian.junit.ArquillianTestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.beans10.BeansDescriptor;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SynchronousPaymentProcessorWithJUnitRulesTestCase {

    @ClassRule
    public static ArquillianTestClass arquillianTestClass = new ArquillianTestClass();

    @Rule
    public ArquillianTest arquillianTest = new ArquillianTest();

    @Deployment
    public static Archive<?> createDeployment() {
        BeansDescriptor beansXml = Descriptors.create(BeansDescriptor.class);
        // enable the alternative in beans.xml
        beansXml.getOrCreateAlternatives().clazz(MockPaymentProcessor.class.getName());

        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addAsManifestResource(new StringAsset(beansXml.exportAsString()), beansXml.getDescriptorName())
            .addPackage(Synchronous.class.getPackage());

        WebArchive war = ShrinkWrap.create(WebArchive.class)
            .addAsWebInfResource(new StringAsset(beansXml.exportAsString()), beansXml.getDescriptorName())
            .addPackage(Synchronous.class.getPackage());

        // return jar => will only work with JBoss AS 7 adapters when using the default JMX protocol and embedded CDI adapters
        // return war => will work in all cases
        // NOTE: Arquillian does not move the custom beans.xml to WEB-INF when
        //       it bundles the jar in a war in order to deploy to a Java EE
        //       container and execute tests over the Servlet protocol.
        return war;
    }

    @Inject
    @Synchronous
    PaymentProcessor syncProcessor;

    @Test
    public void shouldBeReplacedByAMock() throws Exception {
        Double firstPayment = new Double(25);
        Double secondPayment = new Double(50);

        MockPaymentProcessor.PAYMENTS.clear();

        syncProcessor.process(firstPayment);

        assertEquals(1, MockPaymentProcessor.PAYMENTS.size());
        assertEquals(firstPayment, MockPaymentProcessor.PAYMENTS.get(0));

        syncProcessor.process(secondPayment);

        assertEquals(2, MockPaymentProcessor.PAYMENTS.size());
        assertEquals(secondPayment, MockPaymentProcessor.PAYMENTS.get(1));
    }
}
