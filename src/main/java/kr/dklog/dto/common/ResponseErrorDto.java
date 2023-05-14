package kr.dklog.dto.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseErrorDto {

    private String code;

    private String message;
}
