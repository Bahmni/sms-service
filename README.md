This repo features sending SMS and used D7 networks as the default SMS sender. Anyone can override the functionality with their own implementation to send sms.

## Running From The Docker Image

Create docker image

```
docker build -t sms -f package/docker/Dockerfile . 
```

To run the image

```
docker run -d -p 8001:8080 sms
```

## Running From Source

## Test / Build

To run the tests / build <br />
```
./gradlew clean build
./gradlew clean test
```

## Rest Api exposed to send sms

```
http://localhost:8001/sms/send?phoneNumber=&message=
```
Note: currently this reference implementation does not have any authentication check, it's meant to be used internally and not to be exposed to public at all

## Sample json format for D7 message content <br />
```
{
    "messages": [
        {
            "channel": "sms",
            "originator": "SMS",
            "recipients": [
                "+919876543210"
            ],
            "content": "Greetings from D7 SMS",
            "msg_type": "text"
        }
    ]
}
```
