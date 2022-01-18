Feature: facebook

@TC_001
Scenario: [midtrans] Buy Now Using BCA Transfer
Given I access Demo Midtrans Home Page
And I click "BUY NOW"
And I Check Out Shopping Chart
And I Continue Coco Store
And I Choose ATM/Bank Transfer Payment
And I Choose BCA ATM/Bank Transfer
And I See BCA Account Number
When I complete payment BCA ATM
Then I verify Booking is success

@TC_002
Scenario: [midtrans] Buy Now Using Mandiri Transfer
Given I access Demo Midtrans Home Page
And I click "BUY NOW"
And I Check Out Shopping Chart
And I Continue Coco Store
And I Choose ATM/Bank Transfer Payment
And I Choose Mandiri ATM/Bank Transfer
And I See BCA Account Number
When I complete payment BCA ATM
Then I verify Booking is success

@TC_003
Scenario: [midtrans] Buy Now Using BNI Transfer
Given I access Demo Midtrans Home Page
And I click "BUY NOW"
And I Check Out Shopping Chart
And I Continue Coco Store
And I Choose ATM/Bank Transfer Payment
And I Choose BNI ATM/Bank Transfer
And I See BCA Account Number
When I complete payment BCA ATM
Then I verify Booking is success

@TC_004
Scenario: [midtrans] Buy Now Using Permata Transfer
Given I access Demo Midtrans Home Page
And I click "BUY NOW"
And I Check Out Shopping Chart
And I Continue Coco Store
And I Choose ATM/Bank Transfer Payment
And I Choose Permata ATM/Bank Transfer
And I See BCA Account Number
When I complete payment BCA ATM
Then I verify Booking is success

@TC_005
Scenario: [midtrans] Buy Now Using BRI Transfer
Given I access Demo Midtrans Home Page
And I click "BUY NOW"
And I Check Out Shopping Chart
And I Continue Coco Store
And I Choose ATM/Bank Transfer Payment
And I Choose BRI ATM/Bank Transfer
And I See BCA Account Number
When I complete payment BCA ATM
Then I verify Booking is success
