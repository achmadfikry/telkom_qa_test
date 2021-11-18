Feature: facebook

@TC_001
Scenario: [facebook] Register New User
Given I access Facebook Home Page
And I click "Create New Account"
And I input value for field "First Name" with "John"
And I input value for field "Surame" with "Pantau"
And I input value for field "Mobile Number / Email Address" with "089881238234"
And I input value for field "New Password" with "Catalunya08"
And I input value for field "Date of Birth" with "8"
And I input value for field "Month of Birth" with "Aug"
And I input value for field "Year of Birth" with "1998"
And I input value for field "Gender" with "Male"
When I click "Sign Up"
Then I verify Sign Up is Success
