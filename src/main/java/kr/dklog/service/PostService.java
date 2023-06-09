package kr.dklog.service;

import kr.dklog.common.util.PagingUtil;
import kr.dklog.dto.PostDto;
import kr.dklog.dto.common.RequestListDto;
import kr.dklog.dto.request.RequestPostDto;
import kr.dklog.dto.request.RequestUpdatePostDto;
import kr.dklog.dto.response.PreviewPostDto;
import kr.dklog.dto.response.ResponsePostDto;
import kr.dklog.dto.response.ResponsePostListDto;
import kr.dklog.mapper.CommentMapper;
import kr.dklog.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private final CommentMapper commentMapper;

    public ResponsePostDto get(Long postId) {
        PostDto postDto = postMapper.findById(postId)
                .orElseThrow(() -> new RuntimeException("게시글 없음"));

        ResponsePostDto responsePostDto = new ResponsePostDto();
        responsePostDto.setPostId(postDto.getPostId());
        responsePostDto.setTitle(postDto.getTitle());
        responsePostDto.setContentMd(postDto.getContentMd());
        responsePostDto.setContentHtml(postDto.getContentHtml());
        responsePostDto.setViews(postDto.getViews());
        responsePostDto.setCreatedDate(postDto.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm")));
        if(postDto.getModifiedDate() != null){
            responsePostDto.setModifiedDate(postDto.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH:mm")));
        }
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
            String previewContent = getTextContent(contentHtml);
            // getImage
            String previewImage = getPreviewImage(contentHtml);

            PreviewPostDto previewPostDto = new PreviewPostDto();
            previewPostDto.setPostId(postDto.getPostId());
            previewPostDto.setTitle(postDto.getTitle());
            previewPostDto.setPreviewContent(previewContent);
            previewPostDto.setPreviewImage(previewImage);
            previewPostDto.setViews(postDto.getViews());
            previewPostDto.setCommentCount(commentMapper.countBy(postDto.getPostId()));
            previewPostDto.setCreatedDate(postDto.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
            previewPostDto.setMemberId(postDto.getMemberId());
            previewPostDto.setUsername(postDto.getUsername());
            previewPostDto.setPicture(postDto.getPicture());

            return previewPostDto;
        })).collect(Collectors.toList());

        responsePostListDto.setPostList(previewPostDtoList);

        return responsePostListDto;
    }

    public int getPageNum(Long postId){
        List<Long> postIdList = postMapper.findAllId();

        int pageNum = postIdList.indexOf(postId) + 1;

        if(pageNum % 10 == 0){
            return pageNum / 10;
        }else {
            return pageNum / 10 + 1;
        }

    }

    private String getTextContent(String contentHtml) {
        String result = contentHtml
                .replaceAll("<(/)?([a-zA-Z0-9]*)(\\s[a-zA-Z0-9]*=[^>]*)?(\\s)*(/)?>", "")
                .replaceAll("-&gt;", "")
                .replaceAll("&lt;/br&gt;", "");

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
        requestPostDto.setContentText(getTextContent(requestPostDto.getContentHtml()));

        int result = postMapper.save(requestPostDto);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean modify(RequestUpdatePostDto requestUpdatePostDto) {
        requestUpdatePostDto.setModifiedDate(LocalDateTime.now());
        requestUpdatePostDto.setContentText(getTextContent(requestUpdatePostDto.getContentHtml()));

        int result = postMapper.update(requestUpdatePostDto);
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }

    public void delete(Long postId)  throws Exception{
        postMapper.delete(postId);
    }

    public void countViews(HttpServletRequest request, HttpServletResponse response, Long postId) {
        Cookie newCookie = null;
        Cookie[] nowCookies = request.getCookies();
        if (nowCookies != null) {
            for (Cookie cookie : nowCookies) {
                if (cookie.getName().equals("postView")) {
                    newCookie = cookie;
                }
            }
        }
        if (newCookie != null) {
            if (!newCookie.getValue().contains("[" + postId + "]")) {
                postMapper.updateViews(postId);
                newCookie.setValue(newCookie.getValue() + "_[" + postId + "]");
                newCookie.setPath("/");
                newCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(newCookie);
            }
        } else {
            postMapper.updateViews(postId);
            Cookie cookie = new Cookie("postView", "[" + postId + "]");
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24);
            response.addCookie(cookie);
        }
    }
}
