package com.francisco.blog.dto.response;

import java.util.List;

public record MyPostResponse(String title,
                             String description,
                             Boolean isActive,
                             List<String> path){
}
