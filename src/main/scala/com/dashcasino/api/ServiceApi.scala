package com.dashcasino.api

import argonaut.DecodeJson
import spray.http.{HttpEntity, ContentTypes}
import spray.httpx.marshalling.Marshaller
import spray.httpx.unmarshalling.{ContentExpected, UnsupportedContentType, MalformedContent, Unmarshaller}
import spray.routing.HttpService

/**
  * Created by freezing on 2/12/16.
  */
trait ServiceApi extends HttpService { self: HttpApiServiceActor =>
  implicit val jsonMarshaller = Marshaller.of[argonaut.Json](ContentTypes.`application/json`) {
    case (json, ContentTypes.`application/json`, ctx) => ctx.marshalTo(HttpEntity(ContentTypes.`application/json`, json.spaces2))
    case (_, _, ctx) => ctx.rejectMarshalling(List(ContentTypes.`application/json`))
  }

  implicit val jsonUnmarshaller: Unmarshaller[argonaut.Json] = new Unmarshaller[argonaut.Json] {
    override def apply(entity: HttpEntity) = entity match {
      case HttpEntity.NonEmpty(ContentTypes.`application/json`, data) => argonaut.JsonParser.parse(data.asString).leftMap(error => MalformedContent(error)).toEither
      case HttpEntity.NonEmpty(tpe, _) => Left(UnsupportedContentType(s"Can't unmarshal from $tpe"))
      case HttpEntity.Empty => Left(ContentExpected)
    }
  }

  def fromJsonUnmarshaller[A](implicit decodeJson: DecodeJson[A]): Unmarshaller[A] = new Unmarshaller[A] {
    override def apply(entity: HttpEntity) = entity match {
      case HttpEntity.NonEmpty(ContentTypes.`application/json`, data) => argonaut.JsonParser.parse(data.asString).flatMap(json => json.as(decodeJson).toDisjunction.leftMap{ case (error, history) =>
        error
      }).leftMap(error => MalformedContent(error)).toEither
      case HttpEntity.NonEmpty(tpe, _) => Left(UnsupportedContentType(s"Can't unmarshal from $tpe"))
      case HttpEntity.Empty => Left(ContentExpected)
    }
  }
}
