# Twitter Gateway Server

Offers a simple HTTP interface, without any authentication,
and forwards/tweets every message to a well known twitter account.

## Configuration

When running this service, you have to provide some
OAuth tokens for accessing Twitter.

### ConsumerKey only & dynamic access token

````
Environment variable 'consumerKey' --> you will get one from Twitter
Environment variable 'consumerSecret' --> you will get one from Twitter
````

Once the service is up and running, goto ````http://localhost:5000/twitter````
You will be redirected and need to sign in to Twitter. Afterwards,
Twitter redirects you back to _localhost_ and thus you will have an access token in memory.

### ConsumerKey and AccessToken provided

````
Environment variable 'consumerKey' --> you will get one from Twitter
Environment variable 'consumerSecret' --> you will get one from Twitter
Environment variable 'accessToken' --> you will get one from Twitter
Environment variable 'accessTokenSecret' --> you will get one from Twitter
````

When providing all 4 variables, there's no need to sign in and the service
is able to post tweets from the beginning.

## Example: Forwarding incoming SMS

For example, you can use this gateway to automatically tweet incoming SMS from your smartphone.
We've used [SMS Gateway](https://play.google.com/store/apps/details?id=eu.apksoft.android.smsgateway) tool for this.

Once installed on your smartphone, you configure this app like this:

1. Settings -> Activate "Forward incoming SMS to HTTP"
2. HTTP Settings -> Set your URL for "Forward incoming SMS to HTTP" -> "http://myserver/smsgateway"
3. Start the gateway

   SMS Gateway will append the URL request parameters like this "http://myserver/smsgateway?phone=123456789&text=testTEXT&smscenter=xxxxxxx"
