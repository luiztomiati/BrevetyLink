package com.brevitylink.api.controller;

import com.brevitylink.api.dto.LinkRequest;
import com.brevitylink.api.dto.LinkResponse;
import com.brevitylink.api.model.Users;
import com.brevitylink.api.service.LinkService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/links")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<LinkResponse> create(@RequestBody @Valid LinkRequest url, @AuthenticationPrincipal Users user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(linkService.newLink(url,user));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code){
        String url = linkService.redirect(code);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id, @AuthenticationPrincipal Users user) {
        linkService.deleteLink(id,user);
        return ResponseEntity.noContent().build();
    }
}
