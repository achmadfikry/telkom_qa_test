package com.quantum.pages;

import com.custom.ap.CommonDriver;
import com.custom.ap.GenObjGui;
import com.qmetry.qaf.automation.ui.annotations.FindBy;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;

public class MidtransRegisterPage extends CommonDriver{

	GenObjGui genObjGui;
	
	public MidtransRegisterPage () {
		genObjGui = new GenObjGui();
	}
	
	@FindBy(locator = "midtrans.buynow")
	private QAFExtendedWebElement midtransbuynow;
	@FindBy(locator = "midtrans.checkoutshoppingchart")
	private QAFExtendedWebElement midtranscheckoutshoppingchart;
	@FindBy(locator = "midtrans.continuecocostore")
	private QAFExtendedWebElement midtranscontinuecocostore;
	@FindBy(locator = "midtrans.atmbanktransfer")
	private QAFExtendedWebElement midtransatmbanktransfer;

	@FindBy(locator = "midtrans.atmbanktransferbca")
	private QAFExtendedWebElement midtransatmbanktransferbca;
	@FindBy(locator = "midtrans.atmbanktransfermandiri")
	private QAFExtendedWebElement midtransatmbanktransfermandiri;
	@FindBy(locator = "midtrans.atmbanktransferbni")
	private QAFExtendedWebElement midtransatmbanktransferbni;
	@FindBy(locator = "midtrans.atmbanktransferpermata")
	private QAFExtendedWebElement midtransatmbanktransferpermata;
	@FindBy(locator = "midtrans.atmbanktransferbri")
	private QAFExtendedWebElement midtransatmbanktransferbri;

	@FindBy(locator = "midtrans.seeacctnumberatm")
	private QAFExtendedWebElement midtransseeacctnumberatm;
	@FindBy(locator = "midtrans.completepaymentatm")
	private QAFExtendedWebElement midtranscompletepaymentatm;
	@FindBy(locator = "midtrans.bookingnotification")
	private QAFExtendedWebElement midtransbookingnotification;
	
	@FindBy(locator = "midtrans.frameordersummary")
	private QAFExtendedWebElement frameordersummary;
	
	
	public void accessMidtransHomePage() throws Throwable {
		genObjGui.waitForPageToLoad();
		genObjGui.delay(2);
	}

	public void clickBuyNow() throws Throwable {
		midtransbuynow.click();
		genObjGui.delay(1);
	}
	
	public void checkOutShoppingChart() throws Throwable {
		midtranscheckoutshoppingchart.click();
		genObjGui.delay(1);
	}

	public void continueCocoStore() throws Throwable {
		genObjGui.switchToFrame(frameordersummary, 3);
		midtranscontinuecocostore.click();
		genObjGui.delay(1);
	}

	public void chooseATMBankTransferPayment() throws Throwable {
		midtransatmbanktransfer.click();
		genObjGui.delay(1);
	}

	public void chooseBCAATMBankTransferPayment() throws Throwable {
		midtransatmbanktransferbca.click();
		genObjGui.delay(1);
	}
	public void chooseMandiriATMBankTransferPayment() throws Throwable {
		midtransatmbanktransfermandiri.click();
		genObjGui.delay(1);
	}
	public void chooseBNIATMBankTransferPayment() throws Throwable {
		midtransatmbanktransferbri.click();
		genObjGui.delay(1);
	}
	public void choosePermataATMBankTransferPayment() throws Throwable {
		midtransatmbanktransferpermata.click();
		genObjGui.delay(1);
	}
	public void chooseBRIATMBankTransferPayment() throws Throwable {
		midtransatmbanktransferbri.click();
		genObjGui.delay(1);
	}

	public void seeBCAAccountNumber() throws Throwable {
		midtransseeacctnumberatm.click();
		genObjGui.delay(1);
	}

	public void completePaymentBCAATM() throws Throwable {
		midtranscompletepaymentatm.click();
		genObjGui.delay(1);
	}

	public void verifyBookingIsSuccess() throws Throwable {
		genObjGui.switchToDefaultContent();
		if (midtransbookingnotification.isPresent()) {
			System.out.println("Success");
		} else {
			System.out.println("Failed");
		}
	}

	

}
