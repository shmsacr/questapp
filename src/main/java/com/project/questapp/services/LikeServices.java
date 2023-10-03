package com.project.questapp.services;

import com.project.questapp.entities.Like;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.LikeRepositoryc;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.response.LikeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeServices {
    private final LikeRepositoryc likeRepositoryc;
    private final UserService userService;
    private PostService postService;

    public void setPostService(PostService postService){
        this.postService = postService;
    }
    public LikeServices(LikeRepositoryc likeRepositoryc, UserService userService ) {
        this.likeRepositoryc = likeRepositoryc;
        this.userService = userService;
    }

    public Like getOneLikeById(Long likeId) {
        return likeRepositoryc.findById(likeId).orElse(null);
    }

    public List<LikeResponse> getAllLikeWithParam(Optional<Long> userId, Optional<Long> postId) {
        List<Like> list;
        if (userId.isPresent() && postId.isPresent()){
            list = likeRepositoryc.findByUserIdAndPostId(userId.get(),postId.get());
        } else if (userId.isPresent()) {
            list =  likeRepositoryc.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            list =  likeRepositoryc.findByPostId(postId.get());
        }else list =  likeRepositoryc.findAll();

        return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

        public Like createOneLike(LikeCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Post post = postService.getOnePostById(request.getPostId());
        if (user != null && post != null){
            Like likeToSave = new Like();
            likeToSave.setId(request.getId());
            likeToSave.setPost(post);
            likeToSave.setUser(user);
            return likeRepositoryc.save(likeToSave);
        }else return null;
    }

    public void deleteOnelike(Long likeId) {
        likeRepositoryc.deleteById(likeId);
    }
}
