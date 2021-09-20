# Github Users

In the first screen, the app has to fetch GitHub users list, parse
it and display in the list.
REQUIREMENT 2. Selecting a user has to fetch users profile data and open a
profile view displaying the user's profile data.
REQUIREMENT 3. The design must loosely follow the wireframe (at the bottom of
this document) but you must demonstrate a high level of knowledge about best
practises of Android UX and UI principles (e.g. Material design). The app must look
good, work smoothly and the design must follow platform defaults.
REQUIREMENT 4. Own code logic should be commented on.

# Generic 
 1. Code must be done in Kotlin 1.4.x. using AndroidStudio.
 2. Data must be persisted using Room.
 3. UI must be done using ConstraintLayout where appropriate.
 4. All network calls must be queued and limited to 1 request at
a time.
 5. All media has to be cached on disk.

 6. Write Unit tests for data processing logic & models, Room
models (validate creation & update).
 7. Screen rotation change must be supported.

# GitHub users
 1. The app has to be able to work offline if data has been
previously loaded.
 2. The app must handle no internet scenario, show appropriate
UI indicators.
 3. The app must automatically retry loading data once the
connection is available.
 4. When there is data available (saved in the database) from
previous launches, that data should be displayed first, then (in parallel) new data
should be fetched from the backend.

# Users list
 1. Github users list can be obtained from
https://api.github.com/users?since=0 in JSON format.
 2. The list must support pagination (scroll to load more) utilizing
since parameter as the integer ID of the last User loaded.
 3. Page size has to be dynamically determined after the first
batch is loaded.
 4. The list has to display a spinner while loading data as the last
list item.
 5. Every fourth avatar's (the image - not the background!) colour
should have its (image) colours inverted.
 6. List item view should have a note icon if there is note
information saved for the given user.
 7. Users list has to be searchable - local search only; in search
mode, there is no pagination; username and note (see Profile section) fields should
be used when searching, precise match as well as contains should be used.

Profile
 1. Profile info can be obtained from
https://api.github.com/users/[username] in JSON format (e.g.
https://api.github.com/users/tawk).
 2. The view should have the user's avatar as a header view
followed by information fields (UIX is up to you).
 3. The section must have the possibility to retrieve and save back
to the database the Note data (not available in GitHub api; local database only).


If you can't see any list that means the access token has been blocked by github.

Go to com.tawk.to.utils.Constants file and replace the **API_TOKEN** with your personal token if you can't see the list

A Demo APK can be found in this link
https://www.dropbox.com/s/oh4grfrx920ng93/tawk.apk?dl=0

## BONUS

1. Empty views such as list items (while data is still loading) should have Loading Shimmer aka Skeletons
3. MVVM Architecture.  
4. Users list UI has been done in code and Profile - with Layout Editor. 
5. Supports dark mode.
 

![Screenshot_1](https://user-images.githubusercontent.com/8910479/127963408-63542475-8c29-4274-b963-8013f552d6ec.jpg)
![Screenshot_2](https://user-images.githubusercontent.com/8910479/127963421-a0a6c9a0-d3dc-4893-9cad-1bf054806ada.jpg)
