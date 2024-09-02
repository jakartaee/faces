/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2021 Contributors to Eclipse Foundation.
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

package jakarta.faces.webapp;


import java.io.IOException;
import jakarta.faces.FactoryFinder;
import static jakarta.faces.FactoryFinder.FACES_SERVLET_FACTORY;
import jakarta.faces.context.FacesContext;
import jakarta.faces.lifecycle.Lifecycle;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.MultipartConfig;

/**
 * <p>
 * <strong class="changed_modified_2_0 changed_modified_2_0_rev_a changed_modified_2_1 changed_modified_2_2
 * changed_modified_2_3">FacesServlet</strong> is a Jakarta Servlet servlet that manages the request processing
 * lifecycle for web applications that are utilizing Jakarta Faces to construct the user interface.
 * </p>
 *
 * <div class="changed_added_2_1">
 *
 * <p>
 * If the application is running in a Jakarta Servlet 3.0 (and beyond) container, the runtime must provide an
 * implementation of the {@link jakarta.servlet.ServletContainerInitializer} interface that declares the following
 * classes in its {@link jakarta.servlet.annotation.HandlesTypes} annotation.
 * </p>
 *
 * <ul>
 *
 * <li class="changed_added_2_3">{@link jakarta.faces.annotation.FacesConfig}</li>
 *
 * <li>{@link jakarta.faces.application.ResourceDependencies}</li>
 *
 * <li>{@link jakarta.faces.application.ResourceDependency}</li>
 *
 * <li>jakarta.faces.bean.ManagedBean</li>
 *
 * <li>{@link jakarta.faces.component.FacesComponent}</li>
 *
 * <li>{@link jakarta.faces.component.UIComponent}</li>
 *
 * <li>{@link jakarta.faces.convert.Converter}</li>
 *
 * <li>{@link jakarta.faces.convert.FacesConverter}</li>
 *
 * <li>{@link jakarta.faces.event.ListenerFor}</li>
 *
 * <li>{@link jakarta.faces.event.ListenersFor}</li>
 *
 * <li>{@link jakarta.faces.render.FacesBehaviorRenderer}</li>
 *
 * <li>{@link jakarta.faces.render.Renderer}</li>
 *
 * <li>{@link jakarta.faces.validator.FacesValidator}</li>
 *
 * <li>{@link jakarta.faces.validator.Validator}</li>
 *
 * </ul>
 *
 * <p>
 * This Jakarta Servlet servlet must automatically be mapped if it is <strong>not</strong> explicitly mapped in
 * <code>web.xml</code> or <code>web-fragment.xml</code> and one or more of the following conditions are true.
 * </p>
 *
 * <ul>
 *
 * <li>
 * <p>
 * A <code>faces-config.xml</code> file is found in <code>WEB-INF</code>
 * </p>
 * </li>
 *
 * <li>
 * <p>
 * A <code>faces-config.xml</code> file is found in the <code>META-INF</code> directory of a jar in the application's
 * classpath.
 * </p>
 * </li>
 *
 * <li>
 * <p>
 * A filename ending in <code>.faces-config.xml</code> is found in the <code>META-INF</code> directory of a jar in the
 * application's classpath.
 * </p>
 * </li>
 *
 * <li>
 * <p>
 * The <code>jakarta.faces.CONFIG_FILES</code> context param is declared in <code>web.xml</code> or
 * <code>web-fragment.xml</code>.
 * </p>
 * </li>
 *
 * <li>
 * <p>
 * The <code>Set</code> of classes passed to the <code>onStartup()</code> method of the
 * <code>ServletContainerInitializer</code> implementation is not empty.
 * </p>
 * </li>
 *
 * </ul>
 *
 * <p>
 * If the runtime determines that the servlet must be automatically mapped, it must be mapped to the following
 * &lt;<code>url-pattern</code>&gt; entries.
 * </p>
 *
 * <ul>
 * <li>/faces/*</li>
 * <li>*.jsf</li>
 * <li>*.faces</li>
 * <li class="changed_added_2_3">*.xhtml</li>
 * </ul>
 *
 * </div>
 *
 * <p class="changed_added_2_3">
 * Note that the automatic mapping to {@code *.xhtml} can be disabled with the context param
 * {@link #DISABLE_FACESSERVLET_TO_XHTML_PARAM_NAME}.
 * </p>
 *
 * <div class="changed_added_2_2">
 *
 * <p>
 * This class must be annotated with {@code jakarta.servlet.annotation.MultipartConfig}. This causes the Jakarta Servlet
 * container in which the Jakarta Faces implementation is running to correctly handle multipart form data.
 * </p>
 *
 * <p>
 * <strong>Some security considerations relating to this class</strong>
 * </p>
 *
 * <p>
 * The topic of web application security is a cross-cutting concern and every aspect of the specification address it.
 * However, as with any framework, the application developer needs to pay careful attention to security. Please consider
 * these topics among the rest of the security concerns for the application. This is by no means a complete list of
 * security concerns, and is no substitute for a thorough application level security review.
 * </p>
 *
 * <blockquote>
 *
 * <p>
 * <strong>Prefix mappings and the <code>FacesServlet</code></strong>
 * </p>
 *
 * <p>
 * If the <code>FacesServlet</code> is mapped using a prefix <code>&lt;url-pattern&gt;</code>, such as
 * <code>&lt;url-pattern&gt;/faces/*&lt;/url-pattern&gt;</code>, something must be done to prevent access to the view
 * source without its first being processed by the <code>FacesServlet</code>. One common approach is to apply a
 * &lt;security-constraint&gt; to all facelet files and flow definition files. Please see the <strong>Deployment
 * Descriptor</strong> chapter of the Jakarta Servlet Specification for more information the use of
 * &lt;security-constraint&gt;.
 * </p>
 *
 * <p>
 * <strong>Allowable HTTP Methods</strong>
 * </p>
 *
 * <p>
 * The Jakarta Faces Specification only requires the use of the GET and POST http methods. If your web
 * application does not require any other http methods, such as PUT and DELETE, please consider restricting the
 * allowable http methods using the &lt;http-method&gt; and &lt;http-method-omission&gt; elements. Please see the
 * <strong>Security</strong> sections of the Jakarta Servlet Specification for more information about the use of these
 * elements.
 * </p>
 *
 *
 * </blockquote>
 *
 * </div>
 */
@MultipartConfig
public final class FacesServlet implements Servlet {

    /**
     * <p>
     * Context initialization parameter name for a comma delimited list of context-relative resource paths (in addition to
     * <code>/WEB-INF/faces-config.xml</code> which is loaded automatically if it exists) containing Jakarta Faces
     * configuration information.
     * </p>
     */
    public static final String CONFIG_FILES_ATTR = "jakarta.faces.CONFIG_FILES";

    /**
     * <p>
     * Context initialization parameter name for the lifecycle identifier of the {@link Lifecycle} instance to be utilized.
     * </p>
     */
    public static final String LIFECYCLE_ID_ATTR = "jakarta.faces.LIFECYCLE_ID";

    /**
     * <p class="changed_added_2_3">
     * The <code>ServletContext</code> init parameter consulted by the runtime to tell if the automatic mapping of the
     * {@code FacesServlet} to the extension {@code *.xhtml} should be disabled. The implementation must disable this
     * automatic mapping if and only if the value of this parameter is equal, ignoring case, to {@code true}.
     * </p>
     *
     * <p>
     * If this parameter is not specified, this automatic mapping is enabled as specified above.
     * </p>
     */
    public static final String DISABLE_FACESSERVLET_TO_XHTML_PARAM_NAME = "jakarta.faces.DISABLE_FACESSERVLET_TO_XHTML";

    /**
     * <p class="changed_added_4_0">
     * The <code>ServletContext</code> init parameter consulted by the runtime to tell if the automatic mapping of the
     * {@code FacesServlet} to the extensionless variant (without {@code *.xhtml}) should be enabled. The implementation
     * must enable this automatic mapping if and only if the value of this parameter is equal, ignoring case, to {@code true}.
     * </p>
     *
     * <p>
     * If this parameter is not specified, this automatic mapping is not enabled.
     * </p>
     */
    public static final String AUTOMATIC_EXTENSIONLESS_MAPPING_PARAM_NAME = "jakarta.faces.AUTOMATIC_EXTENSIONLESS_MAPPING";

    /**
     * Stores the Faces Servlet.
     */
    private Servlet facesServlet;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        FacesServletFactory factory = (FacesServletFactory) FactoryFinder.getFactory(FACES_SERVLET_FACTORY);
        facesServlet = factory.getFacesServlet(servletConfig);
    }

    /**
     * <p class="changed_modified_2_0">
     * <span class="changed_modified_2_2">Process</span> an incoming request, and create the corresponding response
     * according to the following specification.
     * </p>
     *
     * <div class="changed_modified_2_0">
     *
     * <p>
     * If the <code>request</code> and <code>response</code> arguments to this method are not instances of
     * <code>HttpServletRequest</code> and <code>HttpServletResponse</code>, respectively, the results of invoking this
     * method are undefined.
     * </p>
     *
     * <p>
     * This method must respond to requests that <span class="changed_modified_2_2">contain</span> the following strings by
     * invoking the <code>sendError</code> method on the response argument (cast to <code>HttpServletResponse</code>),
     * passing the code <code>HttpServletResponse.SC_NOT_FOUND</code> as the argument.
     * </p>
     *
     * <pre>
     * <code>
     * /WEB-INF/
     * /WEB-INF
     * /META-INF/
     * /META-INF
     * </code>
     * </pre>
     *
     * <p>
     * If none of the cases described above in the specification for this method apply to the servicing of this request, the
     * following action must be taken to service the request.
     * </p>
     *
     * <p>
     * Acquire a {@link FacesContext} instance for this request.
     * </p>
     *
     * <p>
     * Acquire the <code>ResourceHandler</code> for this request by calling
     * {@link jakarta.faces.application.Application#getResourceHandler}. Call
     * {@link jakarta.faces.application.ResourceHandler#isResourceRequest}.
     *
     * If this returns <code>true</code> call {@link jakarta.faces.application.ResourceHandler#handleResourceRequest}.
     *
     * If this returns <code>false</code>, <span class="changed_added_2_2">call
     * {@link jakarta.faces.lifecycle.Lifecycle#attachWindow} followed by </span>
     * {@link jakarta.faces.lifecycle.Lifecycle#execute} followed by {@link jakarta.faces.lifecycle.Lifecycle#render}.
     *
     * If a {@link jakarta.faces.FacesException} is thrown in either case, extract the cause from the
     * <code>FacesException</code>.
     *
     * If the cause is <code>null</code> extract the message from the <code>FacesException</code>, put it inside of a new
     * <code>ServletException</code> instance, and pass the <code>FacesException</code> instance as the root cause, then
     * rethrow the <code>ServletException</code> instance. If the cause is an instance of <code>ServletException</code>,
     * rethrow the cause. If the cause is an instance of <code>IOException</code>, rethrow the cause.
     *
     * Otherwise, create a new <code>ServletException</code> instance, passing the message from the cause, as the first
     * argument, and the cause itself as the second argument.
     * </p>
     *
     * <p class="changed_modified_2_0_rev_a">
     * The implementation must make it so {@link jakarta.faces.context.FacesContext#release} is called within a finally
     * block as late as possible in the processing for the Jakarta Faces related portion of this request.
     * </p>
     *
     * </div>
     *
     * @param req The Jakarta Servlet request we are processing
     * @param resp The Jakarta Servlet response we are creating
     *
     * @throws IOException if an input/output error occurs during processing
     * @throws ServletException if a Jakarta Servlet error occurs during processing
     *
     */
    @Override
    public void service(ServletRequest req, ServletResponse resp) throws IOException, ServletException {
        facesServlet.service(req, resp);
    }

    /**
     * <p>
     * Release all resources acquired at startup time.
     * </p>
     */
    @Override
    public void destroy() {
        facesServlet.destroy();
        facesServlet = null;
    }

    /**
     * <p>
     * Return the <code>ServletConfig</code> instance for this servlet.
     * </p>
     */
    @Override
    public ServletConfig getServletConfig() {
        return facesServlet.getServletConfig();
    }

    /**
     * <p>
     * Return information about this Servlet.
     * </p>
     */
    @Override
    public String getServletInfo() {
        return facesServlet.getServletInfo();
    }
}
