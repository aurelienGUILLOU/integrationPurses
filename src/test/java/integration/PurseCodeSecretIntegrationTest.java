package integration;

import argent.Purse;
import argent.TransactionRejetterException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import verrou.CodeSecret;

public class PurseCodeSecretIntegrationTest {

    Purse purse;
    CodeSecret codeSecret;
    private double montantCredit = 1000;
    private String goodCode;
    private String badCode;
    private double montantDebit = 50;


    @Before
    public void setUp(){
        codeSecret = new CodeSecret();
        purse = new Purse(codeSecret);
        purse.credite(montantCredit);
        goodCode = codeSecret.reveleCode();
        badCode = goodCode.substring(0,2)+(goodCode.charAt(3)!='0'?'0':'1');
    }

    @Test
    public void testDebitAvecCodeJusteAutorisé() throws Exception {
        purse.debite(montantDebit, goodCode);
        Assertions.assertEquals(montantCredit-montantDebit, purse.getSolde());
    }

    @Test
    public void testDebitAvecCodeFauxRejeté(){
        Assertions.assertThrows(TransactionRejetterException.class,
                ()->purse.debite(montantDebit, badCode));
    }

    @Test
    public void testDebitAvecCodeBloquéRejeté(){

    }

    @Test
    public void testCreditAvecCodeBloquéRejeté(){

    }
}
