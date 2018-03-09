package com.toyama.finagle.api.response

case class AdRes(adResBody: DspAdResBody)

case class DspAdResBody(url: String, price: Int)

case class SspAdResBody(url: String)