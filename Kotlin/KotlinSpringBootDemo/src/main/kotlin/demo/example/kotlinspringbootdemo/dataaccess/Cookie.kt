package demo.example.kotlinspringbootdemo.dataaccess

import java.math.BigDecimal
import javax.persistence.*


@Entity
@Table(schema = "public", name = "cookie")
class Cookie() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cookie_id")
    var cookieID: Long? = null

    @Column(name = "cookie_name")
    var cookieName: String? = null

    @Column(name = "cookie_recipe_url")
    var cookieRecipeUrl: String? = null

    @Column(name = "cookie_sku")
    var cookieSku: String? = null

    @Column(name = "quantity")
    var quantity = 0

    @Column(name = "unit_cost", columnDefinition = "NUMERIC")
    var unitCost: BigDecimal? = null

    constructor(
        cookieName: String?,
        cookieRecipeUrl: String?,
        cookieSku: String?,
        quantity: Int,
        unitCost: BigDecimal?
    ) : this() {
        this.cookieName = cookieName
        this.cookieRecipeUrl = cookieRecipeUrl
        this.cookieSku = cookieSku
        this.quantity = quantity
        this.unitCost = unitCost
    }

    override fun toString(): String {
        return "Cookie(" +
                "cookieID=$cookieID, " +
                "cookieName=$cookieName, " +
                "cookieRecipeUrl=$cookieRecipeUrl, " +
                "cookieSku=$cookieSku, " +
                "quantity=$quantity, " +
                "unitCost=$unitCost" +
                ")"
    }
}
