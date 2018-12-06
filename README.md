# PopularMovies
Android Developer Nanodegree 2nd and 3rd project. 
The app fetches data from the Internet with theMovieDB API, 
present the user with a grid arrangement of movie posters upon launch, 
allows users to view and play trailers (either in the youtube app or a web browser),
to read reviews of a selected movie.
Also the app user can change sort order via a setting: the sort order can be by most popular or by highest-rated. 
When user tap on a movie poster the movie details screen is launched. UI contains a screen for displaying the details for a selected movie. Movie Details layout contains:original title, movie poster, overview, user rating, release date, trailers, and reviews. 
In the movies detail screen, a user can tap a button (for example, a star) to mark it as a Favorite. Tap the button on a favorite movie will unfavorite it.
I use adapters, loaders and custom list layouts to populate ListViews and RecyclerViews.
I make use of Android Architecture Components (Room, LiveData, ViewModel and Lifecycle) to create a robust an efficient application.
I create a database using Room to store the names and ids of the user's favorite movies to display their favorites collection while offline.
I modify the existing sorting criteria for the main view to include an additional pivot to show favorites collection.
//TODO change images


<img src="https://user-images.githubusercontent.com/36941009/46923051-28844a00-cfc7-11e8-8d25-9d5dbfc5f473.png" alt="alt text" width="250"> <img src="https://user-images.githubusercontent.com/36941009/46923053-2ae6a400-cfc7-11e8-9057-b208d19860a9.png" alt="alt text" width="250"> <img src="https://user-images.githubusercontent.com/36941009/47055262-4e4a5400-d16b-11e8-9e20-c9363d972b4d.png" alt="alt text" width="250">


