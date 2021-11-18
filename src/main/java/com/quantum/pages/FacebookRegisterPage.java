package com.quantum.pages;

import com.custom.ap.CommonDriver;
import com.custom.ap.GenObjGui;
import com.qmetry.qaf.automation.ui.annotations.FindBy;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;

public class FacebookRegisterPage extends CommonDriver{

	GenObjGui genObjGui;
	
	public FacebookRegisterPage () {
		genObjGui = new GenObjGui();
	}
	
	@FindBy(locator = "facebook.fbbtncreatenewaccount")
	private QAFExtendedWebElement fbbtncreatenewaccount;
	@FindBy(locator = "facebook.fbinputfirstname")
	private QAFExtendedWebElement fbinputfirstname;
	@FindBy(locator = "facebook.fbinputsurname")
	private QAFExtendedWebElement fbinputsurname;
	@FindBy(locator = "facebook.fbinputnumberemail")
	private QAFExtendedWebElement fbinputnumberemail;
	@FindBy(locator = "facebook.fbinputnewpass")
	private QAFExtendedWebElement fbinputnewpass;
	@FindBy(locator = "facebook.fbinputdobdate")
	private QAFExtendedWebElement fbinputdobdate;
	@FindBy(locator = "facebook.fbinputdobmonth")
	private QAFExtendedWebElement fbinputdobmonth;
	@FindBy(locator = "facebook.fbinputdobyear")
	private QAFExtendedWebElement fbinputdobyear;
	@FindBy(locator = "facebook.fboptiongenderfemale")
	private QAFExtendedWebElement fboptiongenderfemale;
	@FindBy(locator = "facebook.fboptiongendermale")
	private QAFExtendedWebElement fboptiongendermale;
	@FindBy(locator = "facebook.fboptiongendercustom")
	private QAFExtendedWebElement fboptiongendercustom;
	@FindBy(locator = "facebook.fboptiongendercustomselect")
	private QAFExtendedWebElement fboptiongendercustomselect;
	@FindBy(locator = "facebook.fbinputgendercustom")
	private QAFExtendedWebElement fbinputgendercustom;
	@FindBy(locator = "facebook.fbbtnsignup")
	private QAFExtendedWebElement fbbtnsignup;
	@FindBy(locator = "facebook.fbbtncontinueregist")
	private QAFExtendedWebElement fbbtncontinueregist;
	@FindBy(locator = "facebook.fbbtnupdatecontactinfo")
	private QAFExtendedWebElement fbbtnupdatecontactinfo;
	@FindBy(locator = "facebook.fbbtnconfirmcontinue")
	private QAFExtendedWebElement fbbtnconfirmcontinue;
	
	
	public void accessFacebookHomePage() throws Throwable {
		genObjGui.waitForPageToLoad();
		genObjGui.delay(2);
	}

	public void clickCreateNewAccount() throws Throwable {
		fbbtncreatenewaccount.click();
		genObjGui.delay(2);
	}
	
	public void inputFirstName(String FirstName) throws Throwable {
		fbinputfirstname.click();
		fbinputfirstname.sendKeys(FirstName);
		genObjGui.delay(1);
		
	}

	public void inputSurname(String Surame) throws Throwable {
		fbinputsurname.click();
		fbinputsurname.sendKeys(Surame);
		genObjGui.delay(1);
	}

	public void inputNumberEmail(String NumberEmail) throws Throwable {
		fbinputnumberemail.click();
		fbinputnumberemail.sendKeys(NumberEmail);
		genObjGui.delay(1);
	}

	public void inputNewPassword(String NewPassword) throws Throwable {
		fbinputnewpass.click();
		fbinputnewpass.sendKeys(NewPassword);
		genObjGui.delay(1);
	}

	public void iIputDateofBirth(String DateofBirth) throws Throwable {
		fbinputdobdate.click();
		fbinputdobdate.sendKeys(DateofBirth);
		genObjGui.delay(1);
	}

	public void inputMonthofBirth(String MonthofBirth) throws Throwable {
		fbinputdobmonth.click();
		fbinputdobmonth.sendKeys(MonthofBirth);
		genObjGui.delay(1);
	}

	public void inputYearofBirth(String YearofBirth) throws Throwable {
		fbinputdobyear.click();
		fbinputdobyear.sendKeys(YearofBirth);
		genObjGui.delay(1);
	}

	public void inputGender(String Gender) throws Throwable {
		if(Gender.equals("Male")) {
			fboptiongendermale.click();
		}else if (Gender.equals("Female")) {
			fboptiongenderfemale.click();
		}else {
			fboptiongendercustom.click();
		}
		genObjGui.delay(1);
	}

	public void iClickSignUp() throws Throwable {
		fbbtnsignup.click();
		genObjGui.delay(15);
	}

	public void verifySignUpIsSuccess() throws Throwable {
		if(fbbtncontinueregist.isDisplayed()) {
			System.out.println("Success");
		}else {
			System.out.println("Failed");
		}
	}
}
