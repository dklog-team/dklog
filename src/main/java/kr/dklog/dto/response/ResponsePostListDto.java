package kr.dklog.dto.response;

import kr.dklog.dto.common.ResponseListDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostListDto extends ResponseListDto {

    private List<PreviewPostDto> postList;
}
