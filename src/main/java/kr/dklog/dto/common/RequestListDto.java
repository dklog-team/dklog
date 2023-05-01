package kr.dklog.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestListDto {

    private Integer page = 1;

    private Integer pageSize = 10;

    public Integer getPage() {
        if (page - 1 < 0) {
            page = 0;
        }
        return page - 1;
    }

    public Integer getStartIndex() {
        return getPage() * getPageSize();
    }
}
