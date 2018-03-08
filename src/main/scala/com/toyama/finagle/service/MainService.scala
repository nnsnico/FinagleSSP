package com.toyama.finagle.service

import scala.concurrent.Future

trait MainService {
  def createRewarded(body: Any): Future[Any]
}
