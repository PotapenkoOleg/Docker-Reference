package demo.example.kotlinspringbootdemo.dataaccess

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.stream.Stream


@Service
class CookieDataAccessService(private @Autowired val repository: CookieRepository) {
    // region DEMO Data
    private val data = arrayOf(
        arrayOf(
            "peanut butter",
            "https://some.aweso.me/cookie/peanut.html",
            "PB01",
            "24",
            "0.25"
        ), arrayOf(
            "oatmeal raisin",
            "https://some.okay.me/cookie/raisin.html",
            "EWW01",
            "100",
            "1.00"
        ), arrayOf(
            "chocolate chip",
            "https://some.aweso.me/cookie/recipe.html",
            "CC01",
            "12",
            "0.50"
        ), arrayOf(
            "dark chocolate chip",
            "https://some.aweso.me/cookie/recipe_dark.html",
            "CC02",
            "1",
            "0.75"
        )
    )
    // endregion

    fun createCookieRepository() {
        Stream.of(*data).forEach { array: Array<String> ->
            val cookie = Cookie(
                array[0],
                array[1],
                array[2], array[3].toInt(),
                BigDecimal(array[4])
            )
            repository.save(cookie)
        }
    }

    fun getAllCookies(): MutableIterable<Cookie?> {
        return repository.findAll()
    }

    fun addCookie(cookie: Cookie?) {
        if (cookie != null) {
            repository.save(cookie)
        }
    }
}