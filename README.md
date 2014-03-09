# Tweet Gateway Server

Offers a simple HTTP interface, without any authentication,
and forwards/tweets every message to a well known twitter account.

## Configuration

### Twitter OAuth configuration (ConsumerKey & ConsumerSecret)

This server needs an OAuth 'consumerKey' and 'consumerSecret' to authenticate against Twitter.
In order to provide these credentials, you simply set environment variables before you run the server.

````
Environment variable 'twitter4j.oauth.consumerKey'
Environment variable 'twitter4j.oauth.consumerSecret'
````

You can grab these credentials for your own from Twitter's dev site https://dev.twitter.com.
Create a new Twitter application there and follow these steps:

1. Name -> provide a simple name
2. Description -> obvious ;-)
3. Website -> put in some valid site there (can also be a blog or something)
4. Application Type -> Read only
5. Callback URL -> http://example.com  (important: don't leave this field empty! The callback URL actually used is overwritten at runtime)
6. Allow this application to be used to Sign in with Twitter -> Check
7. done.

## Example: Forwarding incoming SMS

For example, you can use Tweet Gateway to automatically tweet the text of incoming SMS from your smartphone.
Iv've used an app like  [SMS Gateway](https://play.google.com/store/apps/details?id=eu.apksoft.android.smsgateway) for this, many times.

Once installed on your smartphone, you configure this app like this:

1. Settings -> Activate "Forward incoming SMS to HTTP"
2. HTTP Settings -> Set your URL for "Forward incoming SMS to HTTP" -> "http://myserver/gw/1234567890abcdef"
3. Start the gateway

   SMS Gateway will append the URL request parameters like this "http://myserver/smsgateway?text=testTEXT&phone=123456789&smscenter=xxxxxxx"
   Thus Tweet Gateway will post the text 'testTEXT' for you.

## Development

### Requirements

* Java JDK 1.7 (or newer)
* Maven 3.1 (or newer)

### Building

````$> mvn package````

### Run development server

Windows:

````$> java -cp target\classes;target\dependency\* net.nitram509.TweetGatewayServerLauncher````

*Nix:

````$> java -cp target/classes:target/dependency/* net.nitram509.TweetGatewayServerLauncher````


[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/nitram509/tweet-gateway-server/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

