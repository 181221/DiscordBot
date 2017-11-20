package no.pk.model;

public class Mail {
    public static final String FRAMAIL = System.getenv("MAIL_FRA").toString();
    public static final String PASSORD = System.getenv("MAIL_PW").toString();
    public static final String TILMAIL = System.getenv("MAIL_TIL").toString();

    private String til;
    private String msg;
    private String subject;
    private String result;

    public Mail(String til, String msg, String subject) {
        this.til = til;
        this.msg = msg;
        this.subject = subject;
        result = "";
    }

    public static String getFRAMAIL() {
        return FRAMAIL;
    }

    public static String getPASSORD() {
        return PASSORD;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTil() {
        return til;
    }

    public void setTil(String til) {
        this.til = til;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
