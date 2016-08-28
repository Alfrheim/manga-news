# manga-news

Checks unixmanga.nl for new mangas, when finds any that you want, sends a message by telegram.

## Installation

$lein uberjar

You need a bot and a channel id to send the message. 
Here you can find all the information: https://core.telegram.org/bots

## Usage

$java -Durl.mangas.files="https://dl.dropboxusercontent.com/u//manga-list" -Dtoken.bot=88888888:aSDffasDFASDFwASDFGQasdfSWELvbo -Dchat.id=8888888  -jar manga-news-0.1.0-SNAPSHOT.jar

You can also use jsvc.

## Options

Uses environ, so you can also use environment variables.
https://github.com/weavejester/environ

### Comments
It's just a project to learn clojure and try telegram
## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
