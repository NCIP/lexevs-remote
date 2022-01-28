package test.gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import junit.framework.Assert;
import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Bank;
import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Cash;
import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Credit;
import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Payment;
import test.gov.nih.nci.cacoresdk.SDKWritableApiBaseTest;

public class ChildWithAssociationWritableApiTest extends SDKWritableApiBaseTest {
	private static Logger log = LogManager.getLogger(ChildWithAssociationWritableApiTest.class);
	public static String getTestCaseName() {
		return "Child With Association WritableApi Test Case";
	}
	
	public void testSaveObjectPayment() {
		log.debug("\n----testSaveObjectPayment()-----\n");
		Payment payment = new Payment();
		payment.setAmount(200);

		save(payment);

		Assert.assertTrue(payment.getId() > 0);
		
		Payment result = (Payment) getObject(Payment.class, payment.getId());
		Assert.assertEquals(payment.getAmount(), result.getAmount());
		
		//Clean up
		delete(result);
	}
	
	public void testSaveObjectCash() {
		log.debug("\n----testSaveObjectCash()-----\n");
		Cash cash = new Cash();
		cash.setAmount(300);
		
		save(cash);

		Cash result = (Cash) getObject(Cash.class, cash.getId());
		Assert.assertEquals(cash.getAmount(), result.getAmount());
		
		//Clean up
		delete(result);
	}

	public void testSaveObjectCreditWithAssociation() {
		log.debug("\n----testSaveObjectCreditWithAssociation()-----\n");
		Credit credit = new Credit();
		credit.setAmount(300);
		credit.setCardNumber("1234567");
		Bank bank=new Bank();
		bank.setName("bank");
		credit.setIssuingBank(bank);
		
		save(bank);
		save(credit);

		Credit result = (Credit) getObjectAndLazyObject(Credit.class, credit.getId(),"issuingBank");
		Assert.assertEquals(credit.getAmount(), result.getAmount());
		Assert.assertEquals(credit.getIssuingBank().getName(), result.getIssuingBank().getName());
		
		//Clean up
		delete(result);
	}
	
	public void testUpdateObjectCreditWithAssociation() {
		log.debug("\n----testUpdateObjectCreditWithAssociation()-----\n");
		Credit credit = new Credit();
		credit.setAmount(300);
		credit.setCardNumber("1234567");
		Bank bank=new Bank();
		bank.setName("bank");
		credit.setIssuingBank(bank);
		
		save(bank);
		save(credit);

		Credit updateCredit = (Credit) getObjectAndLazyObject(Credit.class, credit.getId(),"issuingBank");
		updateCredit.setCardNumber("9876543");
		updateCredit.setAmount(500);
		Bank updateBank=updateCredit.getIssuingBank();
		updateBank.setName("updateBank");
		
		update(updateBank);
		update(updateCredit);

		Credit result = (Credit) getObjectAndLazyObject(Credit.class, credit.getId(),"issuingBank");
		Assert.assertEquals(updateCredit.getAmount(), result.getAmount());
		Assert.assertEquals(updateCredit.getCardNumber(), result.getCardNumber());
		Assert.assertEquals(updateCredit.getIssuingBank().getName(), result.getIssuingBank().getName());
		
		//Clean up
		delete(result);
	}
	
	public void testDeleteObjectCreditWithAssociation() {
		log.debug("\n----testDeleteObjectCreditWithAssociation()-----\n");
		Credit credit = new Credit();
		credit.setAmount(300);
		credit.setCardNumber("1234567");
		Bank bank=new Bank();
		bank.setName("bank");
		credit.setIssuingBank(bank);
		
		save(bank);
		save(credit);

		Credit deleteCredit = (Credit) getObjectAndLazyObject(Credit.class, credit.getId(),"issuingBank");
		deleteCredit.getIssuingBank();
		delete(deleteCredit);
		delete(deleteCredit.getIssuingBank());
		
		Credit resultCredit = (Credit) getObject(Credit.class, credit.getId());
		Bank resultBank = (Bank) getObject(Bank.class, bank.getId());
		
		Assert.assertNull(resultCredit);
		Assert.assertNull(resultBank);
	}
}
