Feature: Sample API File


Scenario: Get List of users
Given request specifications is loaded with "NULL" headers
When user calls the "listUsers" endpoint with "GET" http request
Then "total" in response body should be "15"

@setPayload @resetPayload
Scenario: Creating a new user via API
Given "CreateUser" request payload is available
When user calls the "createUsers" endpoint with "POST" http request




