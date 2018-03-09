package com.toyama.finagle.api.request

case class AdReq(adReqBody: SspAdReqBody)

case class SspAdReqBody(app_id: Int)

case class DspAdReqBody(app_id: Int)