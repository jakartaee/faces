/*
 * Copyright (c) 2008, 2018 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.test.faces20.jstl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

@jakarta.inject.Named("Band")
@jakarta.enterprise.context.RequestScoped
public class BandBean {

  private String[] firstNames = { "Geddy", "Alex", "Neil" };

  private List<String> lastNames = new ArrayList<String>();

  private Vector<String> albums = new Vector<String>();

  private LinkedList<String> songs = new LinkedList<String>();

  private HashSet<String> releaseYears = new HashSet<String>();

  private TreeSet<String> ratings = new TreeSet<String>();

  public BandBean() {
    this.initialSetup();
  }

  private void initialSetup() {
    lastNames.add("Lee");
    lastNames.add("Lifeson");
    lastNames.add("Peart");

    albums.add("Exit Stage Left");
    albums.add("Hemispheres");
    albums.add("Farewell To Kings");

    songs.add("Freewill");
    songs.add("2112");
    songs.add("Subdivisions");

    releaseYears.add("1971");
    releaseYears.add("1972");
    releaseYears.add("1973");

    ratings.add("8");
    ratings.add("9");
    ratings.add("10");
  }

  public String[] getFirstNames() {
    return firstNames;
  }

  public void setFirstNames(String[] firstNames) {
    this.firstNames = firstNames;
  }

  public List<String> getLastNames() {
    return lastNames;
  }

  public void setLastNames(List<String> lastNames) {
    this.lastNames = lastNames;
  }

  public Vector getAlbums() {
    return albums;
  }

  public void setAlbums(Vector albums) {
    this.albums = albums;
  }

  public LinkedList getSongs() {
    return songs;
  }

  public void setSongs(LinkedList songs) {
    this.songs = songs;
  }

  public HashSet getReleaseYears() {
    return releaseYears;
  }

  public void setReleaseYears(HashSet releaseYears) {
    this.releaseYears = releaseYears;
  }

  public TreeSet getRatings() {
    return ratings;
  }

  public void setRatings(TreeSet ratings) {
    this.ratings = ratings;
  }
}
