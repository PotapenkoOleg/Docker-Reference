package com.example.JavaSpringBootDemo.DataAccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Stream;

@Service
public class CookieDataAccessService {

    // region DEMO Data
    private final String[][] data = {
            {"peanut butter",
                    "https://some.aweso.me/cookie/peanut.html",
                    "PB01",
                    "24",
                    "0.25"
            },
            {
                    "oatmeal raisin",
                    "https://some.okay.me/cookie/raisin.html",
                    "EWW01",
                    "100",
                    "1.00"
            },
            {
                    "chocolate chip",
                    "https://some.aweso.me/cookie/recipe.html",
                    "CC01",
                    "12",
                    "0.50"
            },
            {
                    "dark chocolate chip",
                    "https://some.aweso.me/cookie/recipe_dark.html",
                    "CC02",
                    "1",
                    "0.75"
            }
    };
    // endregion

    private final CookieRepository repository;

    public CookieDataAccessService(@Autowired CookieRepository repository) {
        this.repository = repository;
    }

    public void createCookieRepository() {
        Stream.of(data).forEach(array -> {
            Cookie cookie = new Cookie(
                    array[0],
                    array[1],
                    array[2],
                    Integer.parseInt(array[3]),
                    new BigDecimal(array[4])
            );
            repository.save(cookie);
        });
    }

    public Iterable<Cookie> getAllCookies() {
        return repository.findAll();
    }

    public void addCookie(Cookie cookie) {
        repository.save(cookie);
    }
}
