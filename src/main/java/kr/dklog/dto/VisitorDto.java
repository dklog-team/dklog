package kr.dklog.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VisitorDto {

    private Long visitorId;

    private String ip;

    private String os;

    private LocalDateTime visitedDate;
}
