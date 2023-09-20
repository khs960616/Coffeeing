package com.ssafy.coffeeing.modules.tag.mapper;

import com.ssafy.coffeeing.modules.tag.domain.Tag;
import com.ssafy.coffeeing.modules.tag.dto.TagsResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagMapper {

    public static TagsResponse supplyTagsResponseFrom(List<Tag> tags) {
        return new TagsResponse(tags);
    }
}