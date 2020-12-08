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


/**
 *
 * @param GeneralPaymentInstructions General payment instructions (shown on the Invoices & Payments page and Manual invoices)
 * @param EventPaymentInstructions For event registrations (can be changed for each event)
 * @param MembershipPaymentInstructions For membership applications, renewals and level changes
 */
data class PaymentSettings(
    /* General payment instructions (shown on the Invoices & Payments page and Manual invoices) */
    val GeneralPaymentInstructions: kotlin.String? = null,
    /* For event registrations (can be changed for each event) */
    val EventPaymentInstructions: kotlin.String? = null,
    /* For membership applications, renewals and level changes */
    val MembershipPaymentInstructions: kotlin.String? = null
) {

}

