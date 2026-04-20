/*
 * Copyright (c) 2008, 2020 Oracle and/or its affiliates. All rights reserved.
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.faces.event.ActionEvent;

@jakarta.inject.Named("album") @jakarta.enterprise.context.RequestScoped
public class AlbumBean implements Serializable {

  private String artist;

  private String album;

  private List<String> songs;

  private String comments;

  public AlbumBean() {
    this.initialSetup();
  }

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public List<String> getSongs() {
    return songs;
  }

  public void setSongs(List<String> song) {
    this.songs = song;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public void eraseComments(ActionEvent ae) {
    comments = "You Pressed ERASE!";
  }

  private void initialSetup() {
    this.artist = "Rush";
    this.album = "Hemispheres";

    this.songs = new ArrayList<String>();
    this.songs.add("Cygnus X-1 Book II");
    this.songs.add("Circumstances");
    this.songs.add("The Trees");
    this.songs.add("La Villa Strangiato");
  }
}
