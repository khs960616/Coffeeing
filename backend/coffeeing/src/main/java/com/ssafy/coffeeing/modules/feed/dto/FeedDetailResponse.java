package com.ssafy.coffeeing.modules.feed.dto;

import com.ssafy.coffeeing.modules.search.domain.Tag;

import java.util.List;

public record FeedDetailResponse(
        Long feedId,
        List<ImageElement> images,
        String content,
        Tag tag,
        Long registerId,
        Integer likeCount,
        String registerName,
        String registerProfileImg,
        Boolean isLike,
        Boolean isMine
) {
}
