package com.toyama.finagle.api

import cats.data.OptionT
import cats.instances.list._
import cats.syntax.list._
import com.toyama.finagle.api.request.SspAdReqBody
import com.toyama.finagle.api.response.{DspAdResBody, SspAdResBody}
import com.twitter.finagle._
import com.twitter.util._
import com.twitter.conversions.time._
import com.twitter.finagle.http.{Request, Response}
import io.finch._
import io.finch.circe._
import io.finch.syntax._
import io.circe.generic.auto._
import io.circe.parser._

import scala.language.postfixOps

object Api extends App {
  def requestToDsp(host: String, port: String, json: String): Future[Response] = {
    val client: Service[Request, Response] = Http.client
      .withRequestTimeout(100 milliseconds)
      .newService(s"""$host:$port""")

    val request = http.Request(http.Method.Get, "/css/test.json").host(host)
    request.setContentString(json)
    request.setContentTypeJson()

    val response: Future[Response] = client(request)
    response
  }

  val response: Endpoint[SspAdResBody] = post("v1" :: "ad" :: jsonBody[SspAdReqBody]) {
    (request: SspAdReqBody) =>
      val listOfFutures = List(requestToDsp("ss.matome-plus.info", "80", request.toString))
      //      val futureOfList: Future[Seq[Response]] = Future.collect(listOfFutures)
//      val a = for {
//        responseNel <- listOfFutures.par.flatMap(awaitEachFuture).toList.toNel
//      } yield {
//        val dspAdResBodyList = for {
//          response <- responseNel.toList
//          dspAdResBody <- decode[DspAdResBody](response.contentString).toOption
//        } yield dspAdResBody
//
//        dspAdResBodyList.toNel.map { dspAdResBodyNel =>
//          Ok(
//            SspAdResBody(dspAdResBodyNel.toList.maxBy(body => body.price).url)
//          )
//        }.getOrElse(NoContent)
//      }
//      a.getOrElse(NoContent)

      println(awaitEachFuture(listOfFutures.head))
//      println(listOfFutures.par.flatMap(awaitEachFuture).toList.toNel)
      val dspAdResList: List[DspAdResBody] = (for {
        responseNel <- OptionT.fromOption[List](listOfFutures.par.flatMap(awaitEachFuture).toList.toNel)
        response <- OptionT.liftF(responseNel.toList)
        dspAdResBody <- OptionT.fromOption(decode[DspAdResBody](response.contentString).toOption)
      } yield dspAdResBody).value.flatten

      dspAdResList.toNel.map { dspAdResNel =>
        Ok(
          SspAdResBody(dspAdResNel.toList.maxBy(body => body.price).url)
        )
      }.getOrElse(NoContent)
  }

  val server: ListeningServer = Http.server.serve(":9000", response.toServiceAs[Application.Json])

  Await.ready(server)

  private def awaitEachFuture(f: Future[Response]): Option[Response] =
    Try(Await.result(f, 1000 millisecond)).toOption
}
