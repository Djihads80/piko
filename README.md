# Empty explore page (Piko add-on)

Explore shows only the top bar and search box. No trends, no "Posts For You".
Made for X 12.19.1-release.0 (same target as Piko).

## Add to Piko
Copy the two files into your Piko fork, same paths:

- patches/.../timeline/emptyExplorePage/EmptyExplorePagePatch.kt
- extensions/twitter/.../patches/ExplorePage.java

Build Piko (`./gradlew build`), add your own .mpp in Morphe Manager,
and turn on "Empty explore page" together with Piko-Shim.

## If the list still shows
Run `adb logcat -s EmptyExplorePage` while you open Explore and send the output.

## Tip
Piko has "Hide FAB" for the red + button.
