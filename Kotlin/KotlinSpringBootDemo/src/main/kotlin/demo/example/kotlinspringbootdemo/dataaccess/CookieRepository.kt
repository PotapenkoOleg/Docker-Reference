package demo.example.kotlinspringbootdemo.dataaccess

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


@Repository
interface CookieRepository : CrudRepository<Cookie?, Long?>