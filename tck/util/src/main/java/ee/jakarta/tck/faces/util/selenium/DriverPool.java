/*
 * Copyright (c) 2023, 2025 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GPL-2.0 with Classpath-exception-2.0 which
 * is available at https://openjdk.java.net/legal/gplv2+ce.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0 or Apache-2.0
 */
package ee.jakarta.tck.faces.util.selenium;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriverException;

/**
 * a helper class providing pool management for our drivers Note, the pool itself is thread safe (and must be), the
 * drivers are not!
 */
public class DriverPool {

    private static final Logger LOG = Logger.getLogger(DriverPool.class.getName());

    ConcurrentLinkedQueue<ExtendedWebDriver> allDrivers = new ConcurrentLinkedQueue<>();
    ConcurrentLinkedQueue<ExtendedWebDriver> availableDrivers = new ConcurrentLinkedQueue<>();

    private volatile Future<ExtendedWebDriver> warming;

    /**
     * Eagerly boots one Chrome instance on a background thread so the cold start (the
     * Chrome process launch in {@link ChromeDevtoolsDriver#stdInit}) overlaps the
     * Arquillian deployment instead of serialising on the first
     * {@link #getOrNewInstance()} call. No-op if a driver is already available or warming.
     */
    public synchronized void prewarm() {
        if (warming == null && availableDrivers.isEmpty()) {
            warming = CompletableFuture.supplyAsync(ChromeDevtoolsDriver::stdInit);
        }
    }

    /**
     * Claims the pre-warmed driver, blocking until its background boot completes.
     * Returns {@code null} (so the caller falls back to a synchronous boot) if no
     * pre-warm is pending or the boot failed.
     */
    private ExtendedWebDriver takeWarming() {
        Future<ExtendedWebDriver> pending = warming;
        warming = null;
        if (pending == null) {
            return null;
        }
        try {
            ExtendedWebDriver webDriver = pending.get();
            allDrivers.add(webDriver);
            return webDriver;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return null;
        } catch (ExecutionException ex) {
            LOG.warning(() -> "Driver pre-warm boot failed, falling back to synchronous init: " + ex.getCause());
            return null;
        }
    }

    /**
     * creates or activates a new driver instance
     *
     * @return a new or recycled driver instance
     */
    public synchronized ExtendedWebDriver getOrNewInstance() {
        // synchronized to avoid get race conditions.... there is a non synchonzed part between the check and remove
        // to make this easy we simply synchronize the get to fix it
        ExtendedWebDriver webDriver = availableDrivers.isEmpty() ? null : availableDrivers.remove();
        if (webDriver == null) {
            webDriver = takeWarming();
        }
        if (webDriver == null) {
            webDriver = ChromeDevtoolsDriver.stdInit();
            allDrivers.add(webDriver);
        }

        try {
            webDriver.postInit();
        } catch (WebDriverException ex) {
            webDriver = replace(webDriver, "postInit failed (" + ex.getClass().getSimpleName() + ")");
            webDriver.postInit();
        }
        return webDriver;
    }

    /**
     * Discards a driver whose CDP session has gone unresponsive (TimeoutException
     * on send / postInit / get / etc.) and returns a fresh one. Retains pool size
     * by removing the old from {@code allDrivers} before adding the replacement.
     */
    public ExtendedWebDriver replace(ExtendedWebDriver bad, String reason) {
        LOG.warning(() -> "Replacing driver: " + reason);
        try {
            bad.quit();
        } catch (RuntimeException ignored) {
            // best-effort cleanup
        }
        allDrivers.remove(bad);
        ExtendedWebDriver fresh = ChromeDevtoolsDriver.stdInit();
        allDrivers.add(fresh);
        return fresh;
    }

    /**
     * resets a driver and keeps it in the pool for recycling
     *
     * @param driver
     */
    public void returnInstance(ExtendedWebDriver driver) {
        driver.reset();
        availableDrivers.add(driver);
    }

    /**
     * closes a driver but keeps it in the pool
     *
     * @param driver
     */
    public void returnAndCloseInstance(ExtendedWebDriver driver) {
        driver.close();
        availableDrivers.add(driver);
    }

    /**
     * quits a driver and removes it from the pool
     *
     * @param driver
     */
    public void quitInstance(ExtendedWebDriver driver) {
        driver.quit();
        allDrivers.remove(driver);
    }

    public void removeInstance(ExtendedWebDriver driver) {
        allDrivers.remove(driver);
    }

    /**
     * cleans up the pool
     */
    public void quitAll() {
        takeWarming(); // drain any pending pre-warm into allDrivers so it gets quit too
        allDrivers.stream().forEach(ExtendedWebDriver::quit);
        allDrivers.clear();
        availableDrivers.clear();
    }
}
