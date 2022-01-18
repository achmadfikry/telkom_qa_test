package com.quantum.steps.web;

import com.qmetry.qaf.automation.step.QAFTestStep;
import com.quantum.pages.FacebookRegisterPage;
import com.quantum.pages.MidtransRegisterPage;

public class MidtransPageStepDefs {
	
	FacebookRegisterPage facebookRegisterPage;
	MidtransRegisterPage midtransRegisterPage;
	
	public MidtransPageStepDefs() {
		facebookRegisterPage = new FacebookRegisterPage();
		midtransRegisterPage = new MidtransRegisterPage();
	}
	
	@QAFTestStep(description = "I access Demo Midtrans Home Page")
		public void midtransHomePage() throws Throwable{
		midtransRegisterPage.accessMidtransHomePage();
		}

	@QAFTestStep(description = "I click \"BUY NOW\"")
	public void iClickBuyNow() throws Throwable{
		midtransRegisterPage.clickBuyNow();
	}

	@QAFTestStep(description = "I Check Out Shopping Chart")
	public void iCheckOutShoppingChart() throws Throwable{
		midtransRegisterPage.checkOutShoppingChart();
	}

	@QAFTestStep(description = "I Continue Coco Store")
	public void iContinueCocoStore() throws Throwable{
		midtransRegisterPage.continueCocoStore();
	}

	@QAFTestStep(description = "I Choose ATM/Bank Transfer Payment")
	public void iChooseATMBankTransferPayment() throws Throwable{
		midtransRegisterPage.chooseATMBankTransferPayment();
	}

	@QAFTestStep(description = "I Choose BCA ATM/Bank Transfer")
	public void iChooseBCAATMBankTransferPayment() throws Throwable{
		midtransRegisterPage.chooseBCAATMBankTransferPayment();
	}

	@QAFTestStep(description = "I Choose Mandiri ATM/Bank Transfer")
	public void iChooseMandiriATMBankTransferPayment() throws Throwable{
		midtransRegisterPage.chooseMandiriATMBankTransferPayment();
	}
	@QAFTestStep(description = "I Choose BNI ATM/Bank Transfer")
	public void iChooseBNIATMBankTransferPayment() throws Throwable{
		midtransRegisterPage.chooseBNIATMBankTransferPayment();
	}
	@QAFTestStep(description = "I Choose Permata ATM/Bank Transfer")
	public void iChoosePermataATMBankTransferPayment() throws Throwable{
		midtransRegisterPage.choosePermataATMBankTransferPayment();
	}
	@QAFTestStep(description = "I Choose BRI ATM/Bank Transfer")
	public void iChooseBRIATMBankTransferPayment() throws Throwable{
		midtransRegisterPage.chooseBRIATMBankTransferPayment();
	}

	@QAFTestStep(description = "I See BCA Account Number")
	public void iSeeBCAAccountNumber() throws Throwable{
		midtransRegisterPage.seeBCAAccountNumber();
	}

	@QAFTestStep(description = "I complete payment BCA ATM")
	public void iCompletePaymentBCAATM() throws Throwable{
		midtransRegisterPage.completePaymentBCAATM();
	}

	@QAFTestStep(description = "I verify Booking is success")
	public void iVerifyBookingIsSuccess() throws Throwable{
		midtransRegisterPage.verifyBookingIsSuccess();
	}

}
