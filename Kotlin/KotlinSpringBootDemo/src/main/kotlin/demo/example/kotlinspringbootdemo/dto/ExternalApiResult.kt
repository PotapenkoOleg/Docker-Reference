package demo.example.kotlinspringbootdemo.dto

class ExternalApiResult {
    var type: String? = null
    var value: Value? = null

    override fun toString(): String {
        return "ExternalApiResult(type=$type, value=$value)"
    }
}