package kr.dklog.common.exception;

public class AlreadyAuthStudentException extends RuntimeException{

    private static final String MESSAGE = "이미 인증된 교육생입니다.";

    public AlreadyAuthStudentException() {
        super(MESSAGE);
    }
}
