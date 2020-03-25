# Tweet Gateway Server

Offers a simple HTTP interface, without any authentication,
and forwards/tweets every message to a well known twitter account.

If you like this project, endorse please: [![endorse](https://api.coderwall.com/nitram509/endorsecount.png)](https://coderwall.com/nitram509)


##### License

[![License](https://img.shields.io/:license-The%20MIT%20License-blue.svg)](http://opensource.org/licenses/MIT)


## Example: Forwarding incoming SMS

For example, you can use Tweet Gateway to automatically tweet the text of incoming SMS from your smartphone.
I've used an app like  [SMS Gateway](https://play.google.com/store/apps/details?id=eu.apksoft.android.smsgateway) for this, many times.

Once installed on your smartphone, you configure this app like this:

1. Settings -> Activate "Forward incoming SMS to HTTP"
2. HTTP Settings -> Set your URL for "Forward incoming SMS to HTTP" -> "http://myserver/gw/1234567890abcdef"
3. Start the gateway

   SMS Gateway will append the URL request parameters like this "http://myserver/smsgateway?text=testTEXT&phone=123456789&smscenter=xxxxxxx"
   Thus Tweet Gateway will post the text 'testTEXT' for you.

## If you want to host your own Server - Configuration

**Only needed, if yout want to build and run your own server, read this ;-)**

**Else, simply use the service at https://tweet-gateway.herokuapp.com/**

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

### Database URL

Tweet Gateway uses a PostgreSQL database. Configure your JDBC-URL here.
Example "postgres://user3123:passkja83kd8@ec2-117-21-174-214.compute-1.amazonaws.com:6212/db982398"

````
Environment variable 'DATABASE_URL'
````

### Database personal secret (optional)

This key is used to encrypt all your users data. Use a strong passphrase ;-)
Example: "personal.database.secret=sjdfhgkj2hkjhkljhkl23klj2h34jh2g"
If this config is absent, a default encryption key is used.

````
Environment variable 'personal.database.secret'
````

### reCAPTCHA (optional)

In order to prevent bots from automatically using this service, reCAPTCHA is used.
Grab your keys from reCAPTCHA and configure this service.
If you provide keys, reCAPTCHA will be used - else not.

````
Environment variable 'reCaptcha.public.key'
Environment variable 'reCaptcha.private.key'
````

## Privacy

This service can only be accessed when signed in with Twitter.
The user's secret access credentials are stored and transmitted encrypted.
There is a lot of effort done to protect you and your data.

After sign in, you're able to create your own gateway URLs.
These URLs are 16 characters long and contain letters and digits.
This means, there are 5,227,573,613,485,916,806,405,226,496 different combinations possible.
This is equal to a strength of 92bit.
Brute forcing a URL will take way more the 100 years of computing power.

## Development

### Requirements

* Java JDK 11 (or newer)
* Maven 3.1 (or newer)

### Building

````$> mvn package````

### Run development server

Windows:

````$> java -cp target\classes;target\dependency\* net.nitram509.TweetGatewayServerLauncher````

*Nix:

````$> java -cp target/classes:target/dependency/* net.nitram509.TweetGatewayServerLauncher````




