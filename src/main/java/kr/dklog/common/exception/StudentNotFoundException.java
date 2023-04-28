package kr.dklog.common.exception;

public class StudentNotFoundException extends RuntimeException{

    private static final String MESSAGE = "존재하지 않는 학생입니다.";

    public StudentNotFoundException() {
        super(MESSAGE);
    }
}
