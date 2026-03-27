<div style="text-align:center">
  <img src="src/main/resources/spotify_icon.png" width="200" height="200" alt="Spotify Icon"/>
</div>

# Ebanina Std. - Spotify API Wrapper

---

## Description

This is an internal module of the Ebanina project used to retrieve information from the Spotify service via dubolt.com.

---

## Project Architecture

The main class is **Info**, which contains the core functionality for getting similar tracks.

---

## Getting Started

To use the module, simply add it to your _classpath_.  
The module already includes all required dependencies.  
For testing functionality, you can run the JAR file as a regular application.

---

# Spotify API Wrapper (dubolt.com)

Java wrapper for Spotify API through the dubolt.com service. Allows searching tracks and getting recommendations based on seed tracks with configurable parameters.

## Installation

Add the dependency to your project:

```xml
<dependency>
    <groupId>me.API</groupId>
    <artifactId>spotify-wrapper</artifactId>
    <version>1.0</version>
</dependency>
```

## Quick Start

```java
import me.API.Info;
import me.API.Track;

public class Main {
    public static void main(String[] args) {
        try {
            Track track = Info.info.search("Imagine Dragons Believer");
            System.out.println(track.getTitle() + " - " + track.getAuthor());
            
            Track[] recommendations = Info.info.getSimilarTracks("Bohemian Rhapsody", 10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

## Main Methods

### Track Search

```java
Track track = Info.info.search("track name");
System.out.println("Title: " + track.getTitle());
System.out.println("Author: " + track.getAuthor());
System.out.println("Popularity: " + track.getPopularity());
System.out.println("Duration: " + track.getDuration() + "ms");
System.out.println("Explicit: " + track.isExplicit());
```

### Simple Recommendations

```java
Track[] tracks = Info.info.getSimilarTracks("search name", 20);
for (Track track : tracks) {
    System.out.println(track.getTitle() + " - " + track.getAuthor());
}
```

### Advanced Recommendations

```java
import me.API.Params;

Params params = new Params(10)
    .setMinPopularity(50)
    .setMaxEnergy(0.8f)
    .setMinDanceability(0.6f)
    .setMaxTempo(140);

String seedId = Info.info.search("seed track").getId();
Track[] recommendations = Info.info.getRawSimilarTracks(seedId, params);
```

## Album Covers

```java
Track track = Info.info.search("query");
List<Art> arts = track.getAlbumArt();

for (Art art : arts) {
    System.out.println(art.getWidth() + "x" + art.getHeight() + ": " + art.getUrl());
}
```

## Full Example

```java
public class SpotifyDemo {
    public static void main(String[] args) {
        try {
            Track seedTrack = Info.info.search("Metallica Enter Sandman");
            System.out.println("Found: " + seedTrack.getTitle());
            
            Params params = new Params(8)
                .setMinPopularity(40)
                .setMinEnergy(0.5f)
                .setMaxDanceability(0.8f);
            
            Track[] similar = Info.info.getRawSimilarTracks(seedTrack.getId(), params);
            
            System.out.println("Recommendations:");
            for (int i = 0; i < similar.length; i++) {
                Track t = similar[i];
                System.out.printf("%d. %s - %s (pop: %d)\n", 
                    i+1, t.getTitle(), t.getAuthor(), t.getPopularity());
            }
            
        } catch (IOException e) {
            System.err.println("Network error: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("JSON error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

## Recommendation Parameters

| Parameter | Range | Description |
|-----------|-------|-------------|
| limit | 1-50 | Number of tracks |
| min_popularity | 0-100 | Minimum popularity |
| max_popularity | 0-100 | Maximum popularity |
| min_energy | 0.0-1.0 | Track energy |
| max_energy | 0.0-1.0 | Maximum energy |
| min_danceability | 0.0-1.0 | Danceability |
| min_tempo | 60-220 | Minimum tempo (BPM) |

## Error Handling

```java
try {
    Track[] tracks = Info.info.getSimilarTracks("query", 10);
} catch (IOException e) {
    // Network error
} catch (ParseException e) {
    // JSON parsing error
}
```

## Singleton

Use the ready instance:
```java
Info.info.search("query");  // Recommended
new Info().search("query"); // Works
```

---

**Author:** Ebanina Std.  
**Created:** 03.02.2025  
**Spotify API via:** dubolt.com

---

## Features

- Track information retrieval
- Similar tracks recommendations
- Album artwork parsing

---

*This module is not intended for production use.*