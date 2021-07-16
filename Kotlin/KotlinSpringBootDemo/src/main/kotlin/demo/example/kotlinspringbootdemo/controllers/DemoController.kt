package demo.example.kotlinspringbootdemo.controllers


import demo.example.kotlinspringbootdemo.dataaccess.CookieDataAccessService
import demo.example.kotlinspringbootdemo.dto.ExternalApiResult
import com.fasterxml.jackson.databind.ObjectMapper
import demo.example.kotlinspringbootdemo.dataaccess.Cookie
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


@Controller
class DemoController(private @Autowired val cookieDataAccessService: CookieDataAccessService) {

    val logger: Logger = LoggerFactory.getLogger(DemoController::class.java)

    @Value("\${external.api.uri}")
    private val uri: String? = null

    @GetMapping("/")
    fun index(): String {
        return "index"
    }

    @GetMapping("/externalapi")
    fun externalApi(model: Model): String {
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder()
            .uri(URI.create(uri!!))
            .build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        val objectMapper = ObjectMapper()
        val externalApiResult: ExternalApiResult =
            objectMapper.readValue(response.body(), ExternalApiResult::class.java)
        model.addAttribute("result", externalApiResult.value?.quote)
        return "external_api_result"
    }

    @GetMapping("/createcookieshop")
    @ResponseBody
    fun createCookieShop(): String {
        try {
            cookieDataAccessService.createCookieRepository()
        } catch (e: Exception) {
            logger.error(e.message)
            return "error"
        }
        return "ok"
    }

    @GetMapping("/cookieshoplist")
    fun cookieShopList(model: Model): String {
        val cookies = cookieDataAccessService.getAllCookies()
        model.addAttribute("cookies", cookies)
        return "cookie_shop_list"
    }

    @GetMapping("/addcookie")
    fun addCookie(model: Model): String {
        model.addAttribute("cookie", Cookie())
        return "add_cookie"
    }

    @PostMapping("/addcookie")
    fun addCookiePost(@ModelAttribute cookie: Cookie?): String {
        cookieDataAccessService.addCookie(cookie)
        return "redirect:/cookieshoplist"
    }
}
