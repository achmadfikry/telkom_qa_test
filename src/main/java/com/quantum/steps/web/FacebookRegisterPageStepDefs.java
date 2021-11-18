package com.quantum.steps.web;

import com.qmetry.qaf.automation.step.QAFTestStep;
import com.quantum.pages.FacebookRegisterPage;

public class FacebookRegisterPageStepDefs {
	
	FacebookRegisterPage facebookRegisterPage;
	
	public FacebookRegisterPageStepDefs() {
		facebookRegisterPage = new FacebookRegisterPage();
	}
	
	@QAFTestStep(description = "I access Facebook Home Page")
		public void facebookHomePage() throws Throwable{
			facebookRegisterPage.accessFacebookHomePage();
		}

	@QAFTestStep(description = "I click \"Create New Account\"")
	public void iClickCreateNewAccount() throws Throwable{
		facebookRegisterPage.clickCreateNewAccount();
	}

	@QAFTestStep(description = "I input value for field \"First Name\" with {0}")
	public void iInputFirstName(String FirstName) throws Throwable{
		facebookRegisterPage.inputFirstName(FirstName);
	}

	@QAFTestStep(description = "I input value for field \"Surame\" with {0}")
	public void iInputSurname(String Surname) throws Throwable{
		facebookRegisterPage.inputSurname(Surname);
	}

	@QAFTestStep(description = "I input value for field \"Mobile Number / Email Address\" with {0}")
	public void iInputNumberEmail(String NumberEmail) throws Throwable{
		facebookRegisterPage.inputNumberEmail(NumberEmail);
	}

	@QAFTestStep(description = "I input value for field \"New Password\" with {0}")
	public void iInputNewPassword(String NewPassword) throws Throwable{
		facebookRegisterPage.inputNewPassword(NewPassword);
	}

	@QAFTestStep(description = "I input value for field \"Date of Birth\" with {0}")
	public void iInputDateofBirth(String DateofBirth) throws Throwable{
		facebookRegisterPage.iIputDateofBirth(DateofBirth);
	}

	@QAFTestStep(description = "I input value for field \"Month of Birth\" with {0}")
	public void iInputMonthofBirth(String DateofBirth) throws Throwable{
		facebookRegisterPage.inputMonthofBirth(DateofBirth);
	}

	@QAFTestStep(description = "I input value for field \"Year of Birth\" with {0}")
	public void iInputYearofBirth(String DateofBirth) throws Throwable{
		facebookRegisterPage.inputYearofBirth(DateofBirth);
	}

	@QAFTestStep(description = "I input value for field \"Gender\" with {0}")
	public void iInputGender(String DateofBirth) throws Throwable{
		facebookRegisterPage.inputGender(DateofBirth);
	}

	@QAFTestStep(description = "I click \"Sign Up\"")
	public void iClickSignUp() throws Throwable{
		facebookRegisterPage.iClickSignUp();
	}

	@QAFTestStep(description = "I verify Sign Up is Success")
	public void iVerifySignUpIsSuccess() throws Throwable{
		facebookRegisterPage.verifySignUpIsSuccess();
	}
}
