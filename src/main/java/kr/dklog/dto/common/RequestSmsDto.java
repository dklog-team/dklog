package kr.dklog.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestSmsDto {

    private String type;

    private String content;

    private String from;

    private List<Message> messages;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Message {
        private String to;
    }
}
