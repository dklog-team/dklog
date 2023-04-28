package kr.dklog.common.exception;

import kr.dklog.dto.common.ResponseErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseErrorDto> runtime(RuntimeException e) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto("400", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseErrorDto);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ResponseErrorDto> studentNotFound(StudentNotFoundException e) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto("404", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseErrorDto);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SmsException.class)
    public ResponseEntity<ResponseErrorDto> sms(SmsException e) {
        ResponseErrorDto responseErrorDto = new ResponseErrorDto("500", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseErrorDto);
    }
}
