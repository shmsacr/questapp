package com.project.questapp.services;

import com.project.questapp.entities.Like;
import com.project.questapp.entities.Post;
import com.project.questapp.entities.User;
import com.project.questapp.repos.LikeRepositoryc;
import com.project.questapp.requests.LikeCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServices {
    private LikeRepositoryc likeRepositoryc;
    private UserService userService;
    private PostService postService;

    public LikeServices(LikeRepositoryc likeRepositoryc, UserService userService, PostService postService) {
        this.likeRepositoryc = likeRepositoryc;
        this.userService = userService;
        this.postService = postService;
    }

    public Like getOneLikeById(Long likeId) {
        return likeRepositoryc.findById(likeId).orElse(null);
    }

    public List<Like> getAllLikeWithParam(Optional<Long> userId, Optional<Long> postId) {
        if (userId.isPresent() && postId.isPresent()){
            return likeRepositoryc.findByUserIdAndPostId(userId.get(),postId.get());
        } else if (userId.isPresent()) {
            return likeRepositoryc.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            return likeRepositoryc.findByPostId(postId.get());
        }else return likeRepositoryc.findAll();
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
