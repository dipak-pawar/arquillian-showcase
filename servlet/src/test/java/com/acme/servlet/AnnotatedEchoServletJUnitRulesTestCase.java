package com.acme.servlet;

import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.ArquillianTest;
import org.jboss.arquillian.junit.ArquillianTestClass;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

@RunAsClient
public class AnnotatedEchoServletJUnitRulesTestCase {

    @ClassRule
    public static ArquillianTestClass arquillianTestClass = new ArquillianTestClass();

    @Rule
    public ArquillianTest arquillianTest = new ArquillianTest();

    @Deployment(testable = false)
    public static WebArchive getTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "servlet-test.war").addClass(EchoServlet.class);
    }

    @ArquillianResource
    URL deploymentUrl;

    @Test
    public void shouldBeAbleToInvokeServletInDeployedWebApp() throws Exception {
        String requestUrl = deploymentUrl + EchoServlet.URL_PATTERN.substring(1) + "?" + EchoServlet.MESSAGE_PARAM + "=hello";
        String body = StreamReaderUtil.readAllAndClose(new URL(requestUrl).openStream());

        Assert.assertEquals("Verify that the servlet was deployed and returns expected result", "hello", body);
    }
}
