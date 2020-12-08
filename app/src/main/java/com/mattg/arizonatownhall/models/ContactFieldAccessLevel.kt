/**
 * Wild Apricot Admin API
 *  This is Wild Apricot's API for administrators. You can use it if you already have a Wild Apricot account (typically with a website on  *.wildapricot.org). Otherwise, please use https://register.wildapricot.com to create a new account.  If you have any questions about this API, please contact our support team at support@wildapricot.com.
 *
 * OpenAPI spec version: 7.14.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
package io.swagger.client.models


import com.squareup.moshi.Json

/**
 *  Indicates who is allowed to view the field value. - **Public** - value is visible to anyone who has access to members directory - **Members** - value is visible only to members - **Nobody* - value is visible only to account administrators
 * Values: `public`,members,nobody
 */
enum class ContactFieldAccessLevel(val value: kotlin.String) {

    @Json(name = "Public")
    `public`("Public"),

    @Json(name = "Members")
    members("Members"),

    @Json(name = "Nobody")
    nobody("Nobody");

}

