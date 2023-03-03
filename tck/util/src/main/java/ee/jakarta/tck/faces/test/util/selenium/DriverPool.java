/*
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
package ee.jakarta.tck.faces.test.util.selenium;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * a helper class providing pool management for our drivers
 * Note, the pool itself is thread safe (and must be), the drivers are not!
 */
@SuppressWarnings("unused")
public class DriverPool {

    ConcurrentLinkedQueue<ExtendedWebDriver> allDrivers = new ConcurrentLinkedQueue<>();
    ConcurrentLinkedQueue<ExtendedWebDriver> availableDrivers = new ConcurrentLinkedQueue<>();


    /**
     * creates or activates a new driver instance
     * @return a new or recycled driver instance
     */
    public synchronized ExtendedWebDriver getOrNewInstance() {
        //synchronized to avoid get race conditions.... there is a non synchonzed part between the check and remove
        //to make this easy we simply synchronize the get to fix it
        ExtendedWebDriver webDriver = (availableDrivers.size() > 0) ? availableDrivers.remove() : null;
        if(webDriver == null) {
            webDriver = ChromeDevtoolsDriver.stdInit();
            allDrivers.add(webDriver);
        }

        webDriver.postInit();
        return webDriver;
    }

    /**
     * resets a driver and keeps it in the pool for recycling
     * @param driver
     */
    public void returnInstance(ExtendedWebDriver driver) {
        driver.reset();
        availableDrivers.add(driver);
    }

    /**
     * closes a driver but keeps it in the pool
     * @param driver
     */
    public void returnAndCloseInstance(ExtendedWebDriver driver) {
        driver.close();
        availableDrivers.add(driver);
    }

    /**
     * quits a driver and removes it from the pool
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
        allDrivers.stream().forEach(webDriver -> webDriver.quit());
        allDrivers.clear();
        availableDrivers.clear();
    }
}
