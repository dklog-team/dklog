package kr.dklog.common.exception;

public class SmsException extends RuntimeException {

    private static final String MESSAGE = "인증번호 전송을 실패했습니다.";

    public SmsException() {
        super(MESSAGE);
    }
}
