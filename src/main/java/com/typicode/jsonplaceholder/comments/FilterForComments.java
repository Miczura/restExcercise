package com.typicode.jsonplaceholder.comments;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterForComments {
    public static final Predicate<Comments> POSTID_1_AND_NON_IN_BODY = comment->comment.getPostId()==1&&comment.getBody().contains("non");
    public static <T extends Comments> List<T> filterCommentsAccordingToCondition(List<T> list, Predicate<T> condition){
        return list.stream().filter(condition).
                collect(Collectors.toList());
    }
}
