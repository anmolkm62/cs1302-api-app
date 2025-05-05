# Deadline

Modify this file to satisfy a submission requirement related to the project
deadline. Please keep this file organized using Markdown. If you click on
this file in your GitHub repository website, then you will see that the
Markdown is transformed into nice-looking HTML.

## Part 1.1: App Description

> Please provide a friendly description of your app, including
> the primary functions available to users of the app. Be sure to
> describe exactly what APIs you are using and how they are connected
> in a meaningful way.

> **Also, include the GitHub  https://github.com/anmolkm62/cs1302-api-app.git .**

>My app Taste of Music is a matching application which combines a food and music! The app allows> users to input a type of such as chicken or pasta, recipes will generate, and it will be piare>d with music to match the origin of the dish!
>The functions available to users are 1) search for recipe by typing on food name 2) Pick recipe> based off of populated results 3) View a detailed recipe with cuisine type, instructions, and >a category 4) View songs curated to match dish

 >   For this project, I integrated TheMealDB API which I used to search/retrieve recipe informa> tion (meal name, cateogry, instructions, and cuisine type).
    > I used the iTunes Search API to find music tracks which match cuisine's culture to bridge  >music and food in a whimsical and delectable way!

   >  I connect the API by this method: User searches for food with TheMealDB API, once they cho >ose a recipy, the cuisine area is extracted and then used as search term in Itunes to find music.

   >  For my UI, I decided it in a 1200x700 pixel layout with 3 panels. There is a header sectio >n with the Title and Search Bar. There is also a go button to initiate search. The progress ba >r DOES NOT update to show how many recipes download progress is going. It will flash blue and  >load up until completion.
   >  The main content area has a home screen with a welcome message and instruction to use the  >app. The search results has a scrollable list of reipes with buttons to select recipe. There i >s a split design for recipe and music to view both at same time. Finally the footer provided u >pdates!.

   >  MY GITHUB REPO LINK https://github.com/anmolkm62/cs1302-api-app.git
## Part 1.2: APIs

> For each RESTful JSON API that your app uses (at least two are required),
> include an example URL for a typical request made by your app. If you
> need to include additional notes (e.g., regarding API keys or rate
> limits), then you can do that below the URL/URI. Placeholders for this
> information are provided below. If your app uses more than two RESTful
> JSON APIs, then include them with similar formatting.

### API 2- iTunes Search


     >https://itunes.apple.com/search?term=Italian&media=music&entity=song&limit=20
 >API searched for music track using cuisine area as search term. No API key needed and no rate  >limit but apple states to request a reasonable rate.
  >   https://itunes.apple.com/search?term=Mexican&media=music&entity=song&limit=20&country=US
   >  this returns country code

> Replace this line with notes (if needed) or remove it (if not needed).

### API 1 - TheMealDB
 >https://www.themealdb.com/api/json/v1/1/search.php?s=chicken

   >  API searched for meals via name. No API key needed and there were no documented Rate Limits.
     >https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood

     >this endpoint returns meal via category.

> Replace this line with notes (if needed) or remove it (if not needed).

## Part 2: New

> What is something new and/or exciting that you learned from working
> on this project?


  >During this project, I learned a lot about how to go about a project in scrath. I got to design my own UML, UI aspect, and backend. There was a lot of error handling initially because I was  >coding too much at once. It is better to test more frequently in smaller sections which was something I could get away with not doing in past projects. I learned how to integrate different API's, JSON data modeling and the implications of API's.
## Part 3: Retrospect

> If you could start the project over from scratch, what do
> you think might do differently and why?

 >I would test my API's more rigorously from the start. I had started this project a while ago b >ut had to restart 3 times. I was too ambitious the first attempt, my secnond attempt the API I > chose was no longer available. Going forward, I was to implement atronger testing from the st >art to prevent having to restart so many times. Luckily I got faster each time as I was used t o the strucutre. Also, I was like to create more UI aspects.

   >  NOTE: Since all my files are in API, when I run check1302 cs1302-api-app/src/main/java there are no files to check. But if I run check1302 src there are no errors.