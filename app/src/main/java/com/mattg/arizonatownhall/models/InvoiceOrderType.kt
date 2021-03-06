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
 * Type of invoice.
 * Values: undefined,membershipApplication,membershipRenewal,membershipLevelChange,eventRegistration,donation,onlineStore
 */
enum class InvoiceOrderType(val value: kotlin.String) {

    @Json(name = "Undefined")
    undefined("Undefined"),

    @Json(name = "MembershipApplication")
    membershipApplication("MembershipApplication"),

    @Json(name = "MembershipRenewal")
    membershipRenewal("MembershipRenewal"),

    @Json(name = "MembershipLevelChange")
    membershipLevelChange("MembershipLevelChange"),

    @Json(name = "EventRegistration")
    eventRegistration("EventRegistration"),

    @Json(name = "Donation")
    donation("Donation"),

    @Json(name = "OnlineStore")
    onlineStore("OnlineStore");

}

