package com.project.questapp.controllers;

import com.project.questapp.entities.Like;
import com.project.questapp.requests.LikeCreateRequest;
import com.project.questapp.services.LikeServices;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeControl {
    private LikeServices likeServices;

    public LikeControl(LikeServices likeServices) {
        this.likeServices = likeServices;
    }
    @GetMapping("/{likeId}")
    public Like getOnaLike(@PathVariable Long likeId){
        return likeServices.getOneLikeById(likeId);
    }
    @GetMapping
    public List<Like> getAllLikes(@RequestParam Optional<Long> userId,@RequestParam Optional<Long> postId){
        return likeServices.getAllLikeWithParam(userId,postId);
    }

    @PostMapping
    public Like createOneLike(@RequestBody LikeCreateRequest request){
        return likeServices.createOneLike(request);
    }
    @DeleteMapping("/{likeId}")
    public void deleteOneLike(@PathVariable Long likeId){
        likeServices.deleteOnelike(likeId);
    }
}
