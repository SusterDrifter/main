package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
  
    public static final Prefix PREFIX_SYLLABUS = new Prefix("sy/");

  
  
    /* For payment */
    public static final Prefix PAYMENT_INDEX = new Prefix("idx/");
    public static final Prefix PAYMENT_AMOUNT = new Prefix("amt/");
    public static final Prefix PAYMENT_MONTH = new Prefix("mth/");
    public static final Prefix PAYMENT_YEAR = new Prefix("yr/");

}
