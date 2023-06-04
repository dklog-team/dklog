package kr.dklog.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SmsSendResponseDto {

    private Long smsSendResponseId;

    private String requestId;

    private String requestTime;

    private String statusCode;

    private String statusName;
}
