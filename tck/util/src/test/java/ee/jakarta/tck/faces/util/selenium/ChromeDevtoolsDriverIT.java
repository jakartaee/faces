/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package ee.jakarta.tck.faces.util.selenium;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.openqa.selenium.devtools.v139.network.Network;

import com.sun.net.httpserver.HttpServer;

/**
 * A driver reports each request a page sends once, however often the pool handed it out before, so the requests a test sees are the requests its page sent.
 */
@EnabledIfSystemProperty(named = "test.selenium", matches = "true")
class ChromeDevtoolsDriverIT {

    private static final int ACQUISITIONS = 3;
    private static final long LATE_EVENT_WAIT_MILLIS = 500;
    private static final String PAGE = "/page.html";
    private static final byte[] PAGE_BODY = "<!DOCTYPE html><html><head><title>page</title></head><body>page</body></html>".getBytes(UTF_8);
    private static final int HTTP_OK = 200;
    private static final InetAddress LOOPBACK = InetAddress.getLoopbackAddress();

    private static final DriverPool driverPool = new DriverPool();
    private static HttpServer server;

    @BeforeAll
    static void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(LOOPBACK, 0), 0);
        server.createContext(PAGE, exchange -> {
            exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(HTTP_OK, PAGE_BODY.length);
            try (OutputStream body = exchange.getResponseBody()) {
                body.write(PAGE_BODY);
            }
        });
        server.start();
    }

    @AfterAll
    static void stop() {
        driverPool.quitAll();
        server.stop(0);
    }

    /**
     * Each repetition acquires the pooled driver again, and its page load is reported once.
     */
    @RepeatedTest(ACQUISITIONS)
    void eachRequestIsReportedOnce() throws InterruptedException {
        ExtendedWebDriver driver = driverPool.getOrNewInstance();
        try {
            AtomicInteger reported = new AtomicInteger();
            ((ChromeDevtoolsDriver) driver).getDevTools().addListener(Network.requestWillBeSent(), request -> {
                if (request.getRequest().getUrl().endsWith(PAGE)) {
                    reported.incrementAndGet();
                }
            });

            driver.get("http://" + LOOPBACK.getHostAddress() + ":" + server.getAddress().getPort() + PAGE);
            Thread.sleep(LATE_EVENT_WAIT_MILLIS); // Copies of the event from other CDP sessions may arrive after the page loaded.

            assertEquals(1, reported.get());
        }
        finally {
            driverPool.returnInstance(driver);
        }
    }

}
