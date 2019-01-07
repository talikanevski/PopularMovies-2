# PopularMovies-2
Android Developer Nanodegree 3rd project. 
The app fetches data from the Internet with theMovieDB API, 
present the user with a grid arrangement of movie posters upon launch, 
allows users to view and play trailers,
and to read reviews of a selected movie.
Also the app user can change sort order via a setting: the sort order can be by most popular, by highest-rated or by favorites. 
When user tap on a movie poster the movie details screen is launched. UI contains a screen for displaying the details for a selected movie. Movie Details layout contains: original title, movie poster, overview, user rating, release date, trailers, and reviews. 
In the movies detail screen, a user can tap a button (a heart) to mark it as a Favorite. Tap the button on a favorite movie will unfavorite it.
I use adapters, loaders and custom list layouts to populate ListViews and RecyclerViews.
I make use of Android Architecture Components (Room, LiveData, ViewModel and Lifecycle).
I create a database using Room to store movie details of the user's favorite movies to display their favorites collection while offline.
I modify the existing sorting criteria for the main view to include an additional pivot to show favorites collection.

<img src="https://user-images.githubusercontent.com/36941009/50793880-661f0900-127e-11e9-98c2-52033ce1b898.png" alt="alt text" width="250"> <img src="https://user-images.githubusercontent.com/36941009/50794181-49cf9c00-127f-11e9-9589-1ef351c87cf0.png" alt="alt text" width="250"> <img src="https://user-images.githubusercontent.com/36941009/50794243-7aafd100-127f-11e9-9878-3a368d487199.png" alt="alt text" width="250"> <img src="https://user-images.githubusercontent.com/36941009/50794301-a337cb00-127f-11e9-8fff-cbba741553e1.png" alt="alt text" width="250">




