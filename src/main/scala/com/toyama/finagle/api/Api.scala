package com.toyama.finagle.api

import com.toyama.finagle.api.request.AdReqBody
import com.twitter.finagle.Http
import com.twitter.util.Await
import io.finch._
import io.finch.circe._
import io.finch.syntax._
import io.circe.generic.auto._

object Api extends App {
  val rewarded: Endpoint[AdReqBody] = post("v1" :: "ad" :: jsonBody[AdReqBody]) {
    (body: AdReqBody) =>
      Ok(body)
  }

  Await.ready(Http.server.serve(":9000", rewarded.toServiceAs[Application.Json]))
}
