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
 * @param Id If email was sent to individual recipient, this field contains contact identifier. If email was sent to all recipients from some saved search, this field contains saved search identifier.
 * @param Type Recipient group type.
 * @param Name Display name of recipient or name of saved search.
 * @param Email recipient email. for IndividualRecipient and SentEmailRecipient types only
 */
data class EmailRecipient(
    /* If email was sent to individual recipient, this field contains contact identifier. If email was sent to all recipients from some saved search, this field contains saved search identifier.  */
    val Id: kotlin.Int? = null,
    /* Recipient group type. */
    val Type: kotlin.Int? = null,
    /* Display name of recipient or name of saved search. */
    val Name: kotlin.String? = null,
    /* recipient email. for IndividualRecipient and SentEmailRecipient types only */
    val Email: kotlin.String? = null
) {

}
