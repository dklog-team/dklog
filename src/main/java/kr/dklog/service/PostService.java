package kr.dklog.service;

import kr.dklog.common.util.PagingUtil;
import kr.dklog.dto.PostDto;
import kr.dklog.dto.request.RequestPostDto;
import kr.dklog.dto.common.RequestListDto;
import kr.dklog.dto.response.PreviewPostDto;
import kr.dklog.dto.response.ResponsePostDto;
import kr.dklog.dto.response.ResponsePostListDto;
import kr.dklog.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostMapper postMapper;

    public ResponsePostDto get(Long postId) {
        PostDto postDto = postMapper.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        ResponsePostDto responsePostDto = new ResponsePostDto();
        responsePostDto.setPostId(postDto.getPostId());
        responsePostDto.setTitle(postDto.getTitle());
        responsePostDto.setContentMd(postDto.getContentMd());
        responsePostDto.setContentHtml(postDto.getContentHtml());
        responsePostDto.setCreatedDate(postDto.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm")));
//        responsePostDto.setModifiedDate(postDto.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
        responsePostDto.setMemberId(postDto.getMemberId());
        responsePostDto.setUsername(postDto.getUsername());
        responsePostDto.setPicture(postDto.getPicture());

        return responsePostDto;
    }

    public ResponsePostListDto getList(RequestListDto requestListDto) {
        List<PostDto> postDtoList = postMapper.findAll(requestListDto);
        Long totalCount = postMapper.countBy(requestListDto.getKeyword());

        int totalPages = (int) (totalCount % requestListDto.getPageSize() == 0 ?
                        totalCount / requestListDto.getPageSize() : totalCount / requestListDto.getPageSize() + 1);

        PagingUtil pagingUtil = new PagingUtil(totalCount, totalPages, requestListDto.getPage(), requestListDto.getPageSize());

        ResponsePostListDto responsePostListDto = new ResponsePostListDto();
        responsePostListDto.setPagingUtil(pagingUtil);
        List<PreviewPostDto> previewPostDtoList = postDtoList.stream().map((postDto -> {
            // getPreviewContent
            String contentHtml = postDto.getContentHtml();
            String previewContent = getPreviewContent(contentHtml);
            // getImage
            String previewImage = getPreviewImage(contentHtml);

            PreviewPostDto previewPostDto = new PreviewPostDto();
            previewPostDto.setPostId(postDto.getPostId());
            previewPostDto.setTitle(postDto.getTitle());
            previewPostDto.setPreviewContent(previewContent);
            previewPostDto.setPreviewImage(previewImage);
            previewPostDto.setCreatedDate(postDto.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
            previewPostDto.setMemberId(postDto.getMemberId());
            previewPostDto.setUsername(postDto.getUsername());
            previewPostDto.setPicture(postDto.getPicture());

            return previewPostDto;
        })).collect(Collectors.toList());

        responsePostListDto.setPostList(previewPostDtoList);

        return responsePostListDto;
    }

    private String getPreviewContent(String contentHtml) {
        Pattern pattern = Pattern.compile("(<p>)(.*?)(\n)");
        Matcher matcher = pattern.matcher(contentHtml);

        String result = "";
        while (matcher.find()) {
            String group = matcher.group(2);
            String s = group
                    .replaceAll("(<)(.*?)(>)", "")
                    .replaceAll("(</)(.*?)(>)", "")
                    .replace("&lt;/br&gt;", "");

            result += s + " ";
            if (matcher.group(1) == null) {
                break;
            }
        }
        return result;
    }

    private String getPreviewImage(String contentHtml) {
        Pattern pattern = Pattern.compile("(<img src=\")(.*?)(\")");
        Matcher matcher = pattern.matcher(contentHtml);

        String imageSrc = null;
        if (matcher.find()) {
            imageSrc = matcher.group(2);
        }

        return imageSrc;
    }

    public boolean write(RequestPostDto requestPostDto) {
        requestPostDto.setCreatedDate(LocalDateTime.now());

        int result = postMapper.save(requestPostDto);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }
}
