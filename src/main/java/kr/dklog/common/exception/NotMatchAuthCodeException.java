package kr.dklog.common.exception;

public class NotMatchAuthCodeException extends RuntimeException{

    private static final String MESSAGE = "인증번호가 일치하지 않습니다";

    public NotMatchAuthCodeException() {
        super(MESSAGE);
    }
}
