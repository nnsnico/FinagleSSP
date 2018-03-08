package com.toyama.finagle.controller

import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Http, ListeningServer, Service, http}
import com.twitter.util.{Await, Future}

trait MainController {
  val service: Service[Request, Response] {
    def apply(request: Request): Future[Response]
  } = new Service[http.Request, http.Response] {
    override def apply(request: Request): Future[Response] = Future.value(http.Response(request.version, http.Status.Ok))
  }

  val server: ListeningServer = Http.serve(":9000", service)
  Await.ready(server)

//  def getFromClient() =
}
