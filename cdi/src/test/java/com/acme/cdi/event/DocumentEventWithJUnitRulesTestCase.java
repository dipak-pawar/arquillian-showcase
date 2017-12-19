package com.acme.cdi.event;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DocumentEventWithJUnitRulesTestCase {

    @ClassRule
    public static ArquillianTestClass arquillianTestClass = new ArquillianTestClass();

    @Rule
    public ArquillianTest arquillianTest = new ArquillianTest();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClasses(Document.class, WordProcessor.class, PrintSpool.class, PrintJobLiteral.class, PrintJob.class, JobSize.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    WordProcessor processor;

    @Inject
    PrintSpool spool;

    @Test
    public void print_spool_should_observe_print_event() {
        processor.create(5);
        processor.print();
        assertEquals(1, spool.getNumDocumentsSent());
        assertNotNull(spool.getDocumentsProcessed());
        assertEquals(1, spool.getDocumentsProcessed().size());
        assertEquals(1, spool.getDocumentsProcessed(JobSize.MEDIUM).size());
        processor.close();

        processor.create(100);
        processor.print();
        assertEquals(2, spool.getNumDocumentsSent());
        assertNotNull(spool.getDocumentsProcessed());
        assertEquals(2, spool.getDocumentsProcessed().size());
        assertEquals(1, spool.getDocumentsProcessed(JobSize.LARGE).size());
        processor.close();

        processor.printUnknownSize(new Document(25));
        assertEquals(3, spool.getNumDocumentsSent());
        assertEquals(1, spool.getNumFailedDocuments());
    }
}
