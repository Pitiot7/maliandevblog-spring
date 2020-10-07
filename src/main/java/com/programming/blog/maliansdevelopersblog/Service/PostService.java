package com.programming.blog.maliansdevelopersblog.Service;

import com.programming.blog.maliansdevelopersblog.DTO.PostDto;
import com.programming.blog.maliansdevelopersblog.Exception.PostNotFoundException;
import com.programming.blog.maliansdevelopersblog.Repository.PostRepository;
import com.programming.blog.maliansdevelopersblog.Service.AuthService;
import com.programming.blog.maliansdevelopersblog.Model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    @Autowired
    private AuthService authService;
    @Autowired
    PostRepository postRepository;

    public List<PostDto> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::mapFromPostToDto).collect(toList());
    }

    public void createPost(PostDto postDto){
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }
    private PostDto mapFromPostToDto(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setContent(post.getContent());
        postDto.setTitle(post.getTitle());
        postDto.setUsername(post.getUsername());
        return postDto;
    }
    private Post mapFromDtoToPost(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User loggedInUser = (User) authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("No User logged in"));
        post.setUsername(loggedInUser.getUsername());
        post.setCreatedOn(Instant.now());
        return post;
    }

    public PostDto readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFoundException("For id " + id));
        return mapFromPostToDto(post);
    }
}
