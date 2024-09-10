# SMS-Service

This repository provides functionality for sending SMS, using D7 Networks as the default SMS provider. However, it is designed with flexibility, allowing users to override the default implementation and integrate their own SMS sending functionality if needed.

## Running from Source

To run the service from the source code, follow the steps below:

### Test / Build

To run the tests and build the project, use the following commands:

``` shell
./gradlew clean build
./gradlew clean test
```

These commands will clean the existing build, run the test suite, and generate a fresh build.

## REST API for Sending SMS

The service exposes a REST API to send SMS messages. You can access it at `http://sms-service:8080/notification/sms`

### Note

- This API accepts a JSON body containing the phone number and message content.
- **Important:** The current reference implementation does not have any authentication mechanisms in place. It is intended for internal use only and should **not** be exposed publicly.

## Running from the Docker Image

To run the service as a Docker container, follow these steps:

### Build Docker Image

To create the Docker image, run the following command:

``` shell
docker build -t bahmni/sms-service -f package/docker/Dockerfile .
```

### Run Docker Image

To run the image as a container, execute the following:

``` shell
docker run -d -p 8080:8080 bahmni/sms-service
```

This will run the container in detached mode, mapping port `8080` on your machine to port `8080` on the container.

The bahmni/sms-service image runs the Bahmni SMS service, and the image is built and published via GitHub Actions from the sms-service repository. Below are the key environment configurations used to run the service.

Environment Configurations
| Variable Name | Description|
| - | - |
| SMS_SERVICE_IMAGE_TAG | Specifies the image version used for the SMS service. Available tags can be found here. |
| SMS_TOKEN | Points to the token used for sending SMS. Ensure you have valid tokens generated for accessing the SMS provider's API. |
| SMS_ORIGINATOR | Specifies the originator for the SMS header. You can configure this value to your brand name if you have a registered originator. |
| SMS_OPENMRS_HOST | The hostname of the OpenMRS application. |
| SMS_OPENMRS_PORT | The port number of the OpenMRS application. |

## Sample JSON Format for D7 Message Content

Below is an example of the JSON body that should be sent when using D7 Networks as the SMS provider:

``` json
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

This JSON structure includes:

- **channel**: The medium used for communication, which is "sms".
- **originator**: The sender name or identifier.
- **recipients**: An array of phone numbers to which the message will be sent.
- **content**: The actual SMS message content.
- **msg_type**: The type of message, typically "text".

## API Documentation

For detailed API documentation, you can refer to the OpenAPI specification available [here](https://editor.swagger.io/?url=https://raw.githubusercontent.com/Bahmni/sms-service/master/src/main/resources/openapi.yaml).

## Purpose of Token Generation

The token generation script plays a crucial role in ensuring that only authorized entities are allowed to send SMS messages. It prevents unauthorized access and spamming by validating the tokens before processing the requests.

The token is verified within the SMS service by checking:

1. Whether the token is signed with a valid key.
2. If the token contains the correct claims.

When both of these criteria are met, the token is considered valid and trusted, originating from the Bahmni module.

This ensures that only authenticated and authorized users are able to send SMS messages, protecting the service from misuse or spamming attempts.
