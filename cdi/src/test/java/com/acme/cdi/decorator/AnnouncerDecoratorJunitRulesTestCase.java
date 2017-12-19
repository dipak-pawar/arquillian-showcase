package com.acme.cdi.decorator;

import com.acme.cdi.decorate.Announcer;
import com.acme.cdi.decorate.AnnouncerBean;
import com.acme.cdi.decorate.AnnouncerDecorator;
import javax.inject.Inject;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.ArquillianTest;
import org.jboss.arquillian.junit.ArquillianTestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;
import org.jboss.shrinkwrap.descriptor.api.beans10.BeansDescriptor;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

public class AnnouncerDecoratorJunitRulesTestCase {

    @ClassRule
    public static ArquillianTestClass arquillianTestClass = new ArquillianTestClass();

    @Rule
    public ArquillianTest arquillianTest = new ArquillianTest();

    @Deployment
    public static Archive<?> createArchive() {
        BeansDescriptor beansXml = Descriptors.create(BeansDescriptor.class);
        return ShrinkWrap.create(JavaArchive.class)
            .addPackage(Announcer.class.getPackage())
            .addAsManifestResource(
                new StringAsset(beansXml.getOrCreateDecorators().clazz(AnnouncerDecorator.class.getName()).up().exportAsString()),
                beansXml.getDescriptorName());
    }

    @Inject
    AnnouncerBean bean;

    @Test
    public void shouldDecorateAnnouncement() {
        Assert.assertEquals("May I have your attention! School is out!", bean.makeAnnouncement("School is out!"));
    }
}
