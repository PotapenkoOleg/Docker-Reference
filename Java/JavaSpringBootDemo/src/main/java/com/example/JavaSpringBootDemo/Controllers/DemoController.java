package com.example.JavaSpringBootDemo.Controllers;

import com.example.JavaSpringBootDemo.DataAccess.Cookie;
import com.example.JavaSpringBootDemo.DataAccess.CookieDataAccessService;
import com.example.JavaSpringBootDemo.Dto.ExternalApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Controller
public class DemoController {

    Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Value("${external.api.uri}")
    private String uri;

    private final CookieDataAccessService cookieDataAccessService;

    public DemoController(@Autowired CookieDataAccessService cookieDataAccessService) {
        this.cookieDataAccessService = cookieDataAccessService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/externalapi")
    public String externalApi(Model model) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        ExternalApiResult externalApiResult = objectMapper.readValue(response.body(), ExternalApiResult.class);
        model.addAttribute("result", externalApiResult.getValue().getQuote());
        return "external_api_result";
    }

    @GetMapping("/createcookieshop")
    @ResponseBody
    public String createCookieShop() {
        try {
            cookieDataAccessService.createCookieRepository();
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "error";
        }
        return "ok";
    }

    @GetMapping("/cookieshoplist")
    public String cookieShopList(Model model) {
        Iterable<Cookie> cookies = cookieDataAccessService.getAllCookies();
        model.addAttribute("cookies", cookies);
        return "cookie_shop_list";
    }

    @GetMapping("/addcookie")
    public String addCookie(Model model) {
        model.addAttribute("cookie", new Cookie());
        return "add_cookie";
    }

    @PostMapping("/addcookie")
    public String addCookiePost(@ModelAttribute Cookie cookie) {
        cookieDataAccessService.addCookie(cookie);
        return "redirect:/cookieshoplist";
    }
}
