/*
 * Copyright (c) 2009, 2020 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * $Id$
 */
package ee.jakarta.tck.faces.test.faces20.webapp.factoryfinder;

import java.io.IOException;
import java.io.PrintWriter;

import ee.jakarta.tck.faces.test.util.common.servlets.HttpTCKServlet;
import ee.jakarta.tck.faces.test.util.common.util.JSFTestUtil;

import jakarta.faces.FactoryFinder;
import jakarta.faces.application.ApplicationFactory;
import jakarta.faces.context.FacesContextFactory;
import jakarta.faces.lifecycle.LifecycleFactory;
import jakarta.faces.render.RenderKitFactory;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public final class TestServlet extends HttpTCKServlet {

  /**
   * <p>
   * Initialize this <code>Servlet</code> instance.
   * </p>
   * 
   * @param config
   *          the configuration for this <code>Servlet</code>
   * 
   * @throws jakarta.servlet.ServletException
   *           indicates initialization failure
   */
  public void init(ServletConfig config) throws ServletException {

    super.init(config);

  } // init

  // ------------------------------------------------------------ Test Methods

  public void factoryFinderConfig1Test(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    // validate FactoryFinder can be configured via faces-config in
    // /WEB-INF
    PrintWriter out = response.getWriter();
    ApplicationFactory factory = (ApplicationFactory) FactoryFinder
        .getFactory(FactoryFinder.APPLICATION_FACTORY);

    if (factory == null) {
      out.println(
          "Test FAILED.  Unable to obtain ApplicationFactory" + " instance.");
      return;
    }

    if (!(factory.getWrapped() instanceof TCKApplicationFactory)) {
      out.println("Test FAILED.  Unexpected ApplicationFactory returned"
          + " by the FactoryFinder.");
      out.println("Expected: " + TCKApplicationFactory.class);
      out.println("Received: " + factory.getWrapped().getClass());
      return;
    }

    out.println(JSFTestUtil.PASS);

  } // END factorFinderConfig1Test

  public void factoryFinderConfig2Test(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    // validate FactoryFinder can be configured via jakarta.faces.CONFIG_FILES
    // context parameter
    PrintWriter out = response.getWriter();
    FacesContextFactory factory = (FacesContextFactory) FactoryFinder
        .getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);

    if (factory == null) {
      out.println(
          "Test FAILED.  Unable to obtain FacesContextFactory" + " instance.");
      return;
    }

    if (!(factory.getWrapped() instanceof TCKFacesContextFactory)) {
      out.println("Test FAILED.  Unexpected FacesContextFactory returned"
          + " by the FactoryFinder.");
      out.println("Expected: " + TCKFacesContextFactory.class);
      out.println("Received: " + factory.getWrapped().getClass());
      return;
    }

    out.println(JSFTestUtil.PASS);

  } // END factorFinderConfig2Test

  public void factoryFinderConfig3Test(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    // validate FactoryFinder can be configured via META-INF/faces-config.xml
    PrintWriter out = response.getWriter();
    LifecycleFactory factory = (LifecycleFactory) FactoryFinder
        .getFactory(FactoryFinder.LIFECYCLE_FACTORY);

    if (factory == null) {
      out.println(
          "Test FAILED.  Unable to obtain LifecycleFactory" + " instance.");
      return;
    }

    if (!(factory instanceof TCKLifecycleFactory)) {
      out.println("Test FAILED.  Unexpected LifecycleFactory returned"
          + " by the FactoryFinder.");
      out.println("Expected: " + TCKLifecycleFactory.class);
      out.println("Received: " + factory.getClass());
      return;
    }

    out.println(JSFTestUtil.PASS);

  } // END factorFinderConfig3Test

  public void factoryFinderConfig4Test(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {

    // validate FactoryFinder can be configured via META-INF/faces-config.xml
    PrintWriter out = response.getWriter();
    RenderKitFactory factory = (RenderKitFactory) FactoryFinder
        .getFactory(FactoryFinder.RENDER_KIT_FACTORY);

    if (factory == null) {
      out.println(
          "Test FAILED.  Unable to obtain RenderKitFactory" + " instance.");
      return;
    }

    if (!(factory instanceof TCKRenderKitFactory)) {
      out.println("Test FAILED.  Unexpected RenderKitFactory returned"
          + " by the FactoryFinder.");
      out.println("Expected: " + TCKRenderKitFactory.class);
      out.println("Received: " + factory.getClass());
      return;
    }

    out.println(JSFTestUtil.PASS);

  } // END factorFinderConfig4Test

  public void getFactoryTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    String[] factoryTypes = { FactoryFinder.APPLICATION_FACTORY,
        FactoryFinder.CLIENT_WINDOW_FACTORY,
        FactoryFinder.EXCEPTION_HANDLER_FACTORY,
        FactoryFinder.EXTERNAL_CONTEXT_FACTORY,
        FactoryFinder.FACELET_CACHE_FACTORY, FactoryFinder.FACES_CONTEXT_FACTORY,
        FactoryFinder.FLASH_FACTORY, FactoryFinder.FLOW_HANDLER_FACTORY,
        FactoryFinder.LIFECYCLE_FACTORY,
        FactoryFinder.PARTIAL_VIEW_CONTEXT_FACTORY,
        FactoryFinder.RENDER_KIT_FACTORY,
        FactoryFinder.SEARCH_EXPRESSION_CONTEXT_FACTORY,
        FactoryFinder.TAG_HANDLER_DELEGATE_FACTORY,
        FactoryFinder.VIEW_DECLARATION_LANGUAGE_FACTORY,
        FactoryFinder.VISIT_CONTEXT_FACTORY };

    Class<?>[] classes = { jakarta.faces.application.ApplicationFactory.class,
        jakarta.faces.lifecycle.ClientWindowFactory.class,
        jakarta.faces.context.ExceptionHandlerFactory.class,
        jakarta.faces.context.ExternalContextFactory.class,
        jakarta.faces.view.facelets.FaceletCacheFactory.class,
        jakarta.faces.context.FacesContextFactory.class,
        jakarta.faces.context.FlashFactory.class,
        jakarta.faces.flow.FlowHandlerFactory.class,
        jakarta.faces.lifecycle.LifecycleFactory.class,
        jakarta.faces.context.PartialViewContextFactory.class,
        jakarta.faces.render.RenderKitFactory.class,
        jakarta.faces.component.search.SearchExpressionContextFactory.class,
        jakarta.faces.view.facelets.TagHandlerDelegateFactory.class,
        jakarta.faces.view.ViewDeclarationLanguageFactory.class,
        jakarta.faces.component.visit.VisitContextFactory.class };

    for (int i = 0; i < factoryTypes.length; i++) {
      Object o = FactoryFinder.getFactory(factoryTypes[i]);
      if (o == null) {
        pw.println(JSFTestUtil.FAIL + " Null factory returned when "
            + "requesting a factory of type: " + factoryTypes[i]);
        return;
      }
      if (!classes[i].isAssignableFrom(o.getClass())) {
        pw.println(JSFTestUtil.FAIL + " Factory object was not of the "
            + "expected type." + JSFTestUtil.NL + "Expected: " + classes[i].getName()
            + JSFTestUtil.NL + "Received: " + o.getClass().getName());
        return;
      }
    }
    pw.println(JSFTestUtil.PASS);
  }

  public void getFactoryNullPointerExceptionTest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    try {
      FactoryFinder.getFactory(null);
      pw.println("Test FAILED: FactoryFinder.getFactory(null) failed to "
          + "throw a NullPointerException");
    } catch (NullPointerException npe) {
      pw.println(JSFTestUtil.PASS);
    } catch (Exception e) {
      pw.println(JSFTestUtil.FAIL
          + " FactoryFinder.getFactory(null) did not throw NullPointerException.");
      pw.println("Expected: java.lang.NullPointerException");
      pw.println("Received: " + e.getClass().getName());
    }
  }

  public void getFactoryNoConfiguredIllegalArgumentExceptionTest(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    try {
      FactoryFinder.getFactory("FailingFactory");
      pw.println("Test FAILED: FactoryFinder.getFactory(<INVALID_FACTORY>) "
          + "failed to throw an IllegalArgumentException");
    } catch (IllegalArgumentException iae) {
      pw.println(JSFTestUtil.PASS);
    } catch (Exception e) {
      pw.println(JSFTestUtil.FAIL + " FactoryFinder.getFactory(<INVALID_FACTORY>) "
          + "raised an Exception, but not of the expected type.");
      pw.println("Expected: java.lang.IllegalArgumentException");
      pw.println("Received: " + e.getClass().getName());
    }
  }

  public void setFactoryNPETest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    try {
      FactoryFinder.setFactory(null, "bogus");
      pw.println("Test FAILED: FactoryFinder.setFactory(null, implName) "
          + "failed to throw a NullPointerException");
    } catch (NullPointerException npe) {
      pw.println(JSFTestUtil.PASS);
    } catch (Exception e) {
      pw.println(JSFTestUtil.FAIL + " FactoryFinder.setFactory(null, implName) "
          + "raised an Exception, but not of the expected type.");
      pw.println("Expected: java.lang.NullPointerException");
      pw.println("Received: " + e.getClass().getName());
    }
  }

  public void setFactoryIAETest(HttpServletRequest request,
      HttpServletResponse response) throws ServletException, IOException {
    PrintWriter pw = response.getWriter();
    try {
      FactoryFinder.setFactory("bogus", "bogus");
      pw.println("Test FAILED: FactoryFinder.setFactory(bogusName, implName) "
          + "failed to throw an IllegalArgumentException");
    } catch (IllegalArgumentException iae) {
      pw.println(JSFTestUtil.PASS);
    } catch (Exception e) {
      pw.println(JSFTestUtil.FAIL + " FactoryFinder.setFactory(bogusName, implName) "
          + "raised an Exception, but not of the expected type.");
      pw.println("Expected: java.lang.IllegalArgumentException");
      pw.println("Received: " + e.getClass().getName());
    }
  }

}
