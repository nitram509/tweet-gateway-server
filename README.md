
# Twitter Gateway Server

Offers a simple HTTP interface, without any authentication,
and forwards/tweets every message to a well known twitter account.


## Configuration

When running this service, you have to provide some
OAuth tokens for accessing Twitter.

### ConsumerKey only & dynamic access token

Environment variable 'consumerKey' --> you will get one from Twitter
Environment variable 'consumerSecret' --> you will get one from Twitter

Once the service is up and running, goto ````http://localhost:5000/rest/twitter````
You will be redirected and need to sign in to Twitter. Afterwards,
Twitter redirects you back to _localhost_ and thus you will have an access token in memory.

### ConsumerKey and AccessToken provided

Environment variable 'consumerKey' --> you will get one from Twitter
Environment variable 'consumerSecret' --> you will get one from Twitter
Environment variable 'accessToken' --> you will get one from Twitter
Environment variable 'accessTokenSecret' --> you will get one from Twitter

Providing all 4 variables, there's no need to sign in and the service
is able to post tweets from the beginning.

