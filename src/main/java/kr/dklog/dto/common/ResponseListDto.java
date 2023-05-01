package kr.dklog.dto.common;

import kr.dklog.common.util.PagingUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseListDto {

    private PagingUtil pagingUtil;
}
