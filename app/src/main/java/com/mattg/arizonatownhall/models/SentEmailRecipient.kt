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
 * @param ContactId Contact identifier with used email address.
 * @param EventRegistrationId Event regisration Id if the email was sent to an event attendee.
 * @param FirstName first name of recipient.
 * @param LastName last name of recipient.
 * @param Organization organization of recipient.
 * @param Email recipient email.
 * @param RecipientName display name of recipient (Last, First names (if set) or Organization).
 * @param IsDelivered email has been successfully sent.
 * @param IsOpened email has been opened/viewed.
 * @param ClickedLinks
 */
data class SentEmailRecipient(
    /* Contact identifier with used email address.  */
    val ContactId: kotlin.Int? = null,
    /* Event regisration Id if the email was sent to an event attendee.  */
    val EventRegistrationId: kotlin.Int? = null,
    /* first name of recipient. */
    val FirstName: kotlin.String? = null,
    /* last name of recipient. */
    val LastName: kotlin.String? = null,
    /* organization of recipient. */
    val Organization: kotlin.String? = null,
    /* recipient email. */
    val Email: kotlin.String? = null,
    /* display name of recipient (Last, First names (if set) or Organization). */
    val RecipientName: kotlin.String? = null,
    /* email has been successfully sent. */
    val IsDelivered: kotlin.Boolean? = null,
    /* email has been opened/viewed. */
    val IsOpened: kotlin.Boolean? = null,
    val ClickedLinks: kotlin.Array<ClickedLink>? = null
) {

}

