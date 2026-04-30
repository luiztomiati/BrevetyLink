package com.brevitylink.api.service;
import com.brevitylink.api.dto.LinkRequest;
import com.brevitylink.api.dto.LinkResponse;
import com.brevitylink.api.model.Link;
import com.brevitylink.api.model.Users;
import com.brevitylink.api.repository.LinkRepository;
import com.brevitylink.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.sqids.Sqids;
import java.util.List;
import java.util.Optional;

@Service
public class LinkService {

    private final Sqids sqids;
    private final LinkRepository linkRepository;

    @Value("${APP_URL}")
    String baseUrl;

    @Value("${app.links.prefix}")
    private String linksPrefix;

    public LinkService(Sqids sqids, LinkRepository linkRepository) {
        this.sqids = sqids;
        this.linkRepository = linkRepository;
    }

    public LinkResponse newLink(LinkRequest url, Users user){
        String validatedUrl = validateLink(url);
        Link link = createdLink(validatedUrl, user);
        String newString = baseUrl + linksPrefix + "/" + link.getShortCode();
        return new LinkResponse(link.getLinkOriginal(), link.getShortCode(), newString);
    }

    public String redirect(String code) {
        Optional<Link> link = Optional.of(linkRepository.findByShortCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Link não encontrado")));
        Long click = link.get().getClickCount() + 1;
        link.get().setClickCount(click);
        linkRepository.save(link.get());
       return link.get().getLinkOriginal();
    }
    public void deleteLink(Long id, Users user){
        if (!linkRepository.existsByIdAndUserId(id, user.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Link não encontrado ou não pertence ao usuário"
            );
        }
        linkRepository.deleteById(id);
    }

    public String validateLink(LinkRequest link){
        return link.urlOriginal().trim().toLowerCase();
    }

    @Transactional
    public Link createdLink(String url, Users user) {
        Link link = new Link(url);
        link.setUser(user);
        link = linkRepository.save(link);
        String shortCode = sqids.encode(List.of(link.getId()));
        link.setShortCode(shortCode);
        return linkRepository.save(link);
    }
}
