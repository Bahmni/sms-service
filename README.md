This repo features sending SMS and used D7 networks as the default SMS sender. Anyone can override the functionality with their own implementation to send sms.

## Running From Source

## Test / Build

To run the tests / build <br />
```
./gradlew clean build
./gradlew clean test
```

## Rest Api exposed to send sms

```
http://sms-service:8080/notification/sms
```
Note: This API accepts a json body with phone number and message content. Currently this reference implementation does not have any authentication check, it's meant to be used internally and not to be exposed to public at all

## Running From The Docker Image

Create docker image

```
docker build -t sms -f package/docker/Dockerfile . 
```

To run the image

```
docker run -d -p 8080:8080 sms
```

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
## Api Documentation [here](https://editor.swagger.io/?url=https://raw.githubusercontent.com/Bahmni/sms-service/BAH-3330/src/main/resources/openapi.yaml)
## Purpose of token generation
```
The purpose of the token generation script is to ensure that only authorized entities can send SMS messages and prevent any unauthorized intruders from spamming recipients with fake SMS. The script generates tokens that are subsequently verified within the SMS service.
Verification involves checking if the token is signed with a valid key and if it contains the correct claims. If the verification process confirms these criteria, it signifies that the token is valid and originates from the Bahmni module.
```
