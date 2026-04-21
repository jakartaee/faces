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
package ee.jakarta.tck.faces.test.faces20.composite.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class AttributeIT extends BaseITNG {

    private static final String BAND_NAME = "Rush";
    private static final String ALBUM_NAME = "Hemispheres";
    private static final List<String> SONGS = Arrays.asList(
        "Cygnus X-1 Book II",
        "Circumstances",
        "The Trees",
        "La Villa Strangiato");

    @Test
    void compositeAttributeTagTest() {
        WebPage pageOne = getPage("faces/attribute/attributeTest.xhtml");
        WebPage pageTwo = getPage("faces/attribute/attributeTestTwo.xhtml");

        // case 1: using only 'name' attribute for cc.attribute, interface attrs picked up by implementation.
        assertAlbum(pageOne, "artist", "album", "tracks");

        // case 2: nested cc.attribute tags with model + targets attributes work correctly.
        // attributeTest.xhtml renders both Test Case 1 and Test Case 2 composites (compOne + compTwo).
        // Both expose ids 'artist'/'album'/'tracks'; we re-verify page state here.
        assertAlbum(pageOne, "artist", "album", "tracks");

        // case 3: method-signature attribute, backing bean method accepts ActionEvent.
        assertAlbum(pageTwo, "artist", "album", "tracks");

        WebElement button = findByIdSuffix(pageTwo, "erase");
        pageTwo.guardHttp(button::click);

        WebElement comments = findByIdSuffix(pageTwo, "comments");
        assertEquals("You Pressed ERASE!", comments.getAttribute("value"), "comments textarea value");
    }

    private void assertAlbum(WebPage page, String artistId, String albumId, String tracksId) {
        WebElement artist = findByIdSuffix(page, artistId);
        assertEquals(BAND_NAME, artist.getText(), "artist value");

        WebElement album = findByIdSuffix(page, albumId);
        assertEquals(ALBUM_NAME, album.getText(), "album value");

        WebElement tracks = findByIdSuffix(page, tracksId);
        List<String> optionTexts = tracks.findElements(By.tagName("option")).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        assertEquals(SONGS, optionTexts, "tracks select options");
    }

    private static WebElement findByIdSuffix(WebPage page, String id) {
        String suffix = ":" + id;
        return page.findElement(By.xpath(
            "//*[@id='" + id + "'"
            + " or substring(@id, string-length(@id) - " + (suffix.length() - 1) + ") = '" + suffix + "']"));
    }
}
