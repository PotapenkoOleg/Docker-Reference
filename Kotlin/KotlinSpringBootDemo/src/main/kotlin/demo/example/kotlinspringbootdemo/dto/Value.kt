package demo.example.kotlinspringbootdemo.dto

class Value {
    var id: Int? = null
    var quote: String? = null

    override fun toString(): String {
        return "Value(id=$id, quote=$quote)"
    }
}
