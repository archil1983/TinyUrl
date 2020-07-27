package org.neueda.quiz.controller;


import org.neueda.quiz.exception.TinyUrlNotFoundException;
import org.neueda.quiz.service.TinyUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/v1")
public class TinyUrlController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TinyUrlController.class);

    private final TinyUrlService urlService;

    public TinyUrlController(TinyUrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{path}")
    public void redirectUrl(@NotBlank @PathVariable String path,HttpServletResponse response) throws TinyUrlNotFoundException {
        final String longUrl = urlService.getOriginalUrl(path);
        LOGGER.debug("The url with path {} was redirected to the url {}",path,longUrl);
        response.setHeader("Location", longUrl);
        response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
    }

    @GetMapping("/origin_url/{path}")
    public ResponseEntity<String>  exposeUrl( @NotBlank @PathVariable  String path) throws TinyUrlNotFoundException {
        final String longUrl = urlService.getOriginalUrl(path);
        LOGGER.debug("The url with path {} was redirected to the url {}",path,longUrl);
        return new ResponseEntity<>(longUrl,HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> createTinyUrlPath(@NotBlank @RequestBody String url)  {
        final String tinyUrlPath = urlService.createTinyUrlPath(url);
        LOGGER.debug("From the URL {}  was created path {}",url,tinyUrlPath);
        return new ResponseEntity<>(tinyUrlPath,HttpStatus.OK);
    }



}
