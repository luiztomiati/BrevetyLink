package com.brevitylink.api.controller;

import com.brevitylink.api.dto.LinkRequest;
import com.brevitylink.api.dto.LinkResponse;
import com.brevitylink.api.dto.PageResponse;
import com.brevitylink.api.model.Users;
import com.brevitylink.api.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
@Tag(name = "Links", description = "Operações de encurtamento de URLs")
@RestController
@RequestMapping("/links")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @Operation(
            summary = "Criar link encurtado",
            description = "Cria um novo link encurtado para o usuário autenticado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Link criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "URL inválida"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping()
    public ResponseEntity<LinkResponse> create(@RequestBody @Valid LinkRequest url, @AuthenticationPrincipal Users user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(linkService.newLink(url,user));
    }

    @Operation(
            summary = "Redirecionar link",
            description = "Redireciona para a URL original com base no código encurtado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Redirecionamento realizado"),
            @ApiResponse(responseCode = "404", description = "Link não encontrado")
    })
    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code){
        String url = linkService.redirect(code);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }

    @Operation(
            summary = "Deletar link",
            description = "Remove um link encurtado do usuário autenticado"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Link removido com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado"),
            @ApiResponse(responseCode = "403", description = "Sem permissão"),
            @ApiResponse(responseCode = "404", description = "Link não encontrado")
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id, @AuthenticationPrincipal Users user) {
        linkService.deleteLink(id,user);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar links do usuário",
            description = "Retorna os links do usuário autenticado de forma paginada"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public ResponseEntity<PageResponse<LinkResponse>> getUserLinks(@AuthenticationPrincipal Users user, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<LinkResponse> result = linkService.getUserLinks(user.getId(), pageRequest);
        return ResponseEntity.ok(new PageResponse<>(
                result.getContent(),
                result.getNumber(),
                result.getSize(),
                result.getTotalElements(),
                result.getTotalPages()
        ));
    }
}