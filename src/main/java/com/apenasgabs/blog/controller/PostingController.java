package com.apenasgabs.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apenasgabs.blog.model.Posting;
import com.apenasgabs.blog.repository.PostingRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/post")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostingController {

  @Autowired
  private PostingRepository postingRepository;

  @GetMapping("/all")
  public ResponseEntity<List<Posting>> getAll() {
    return ResponseEntity.ok(postingRepository.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Posting> getById(@PathVariable Long id) {
    return postingRepository.findById(id).map(response -> ResponseEntity.ok(response))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/create")
  public ResponseEntity<Posting> createPost(@Valid @RequestBody Posting newPost) {
    Posting createdPost = postingRepository.save(newPost);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
  }

  @PutMapping("/update/{id}")
  public ResponseEntity<Posting> updatePost(@Valid @RequestBody Posting updatedPost) {
    return postingRepository.findById(updatedPost.getId()).map(response -> ResponseEntity.status(HttpStatus.OK).body(postingRepository.save(updatedPost)))
    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

}
