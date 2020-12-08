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
 * @param Id Unique email identifier.
 * @param Url
 * @param SentDate Date when email sending was started.
 * @param Subject Email subject.
 * @param Body Email body.
 * @param ReplyToName
 * @param ReplyToAddress
 * @param Type
 * @param IsTrackingAllowed
 * @param IsCopySentToAdmins
 * @param SenderId
 * @param SenderName
 * @param SendingType
 * @param Origin
 * @param SubOriginId
 * @param RecipientCount
 * @param ReadCount
 * @param UniqueLinkClickCount
 * @param SuccessfullySentCount
 * @param RecipientsThatClickedAnyLinkCount
 * @param FailedCount
 * @param InProgress
 * @param Recipient only if email has a single recipient
 */
data class EmailLogRecord(
    /* Unique email identifier. */
    val Id: kotlin.Int? = null,
    val Url: ResourceUrl? = null,
    /* Date when email sending was started. */
    val SentDate: kotlin.String? = null,
    /* Email subject. */
    val Subject: kotlin.String? = null,
    /* Email body. */
    val Body: kotlin.String? = null,
    val ReplyToName: kotlin.String? = null,
    val ReplyToAddress: kotlin.String? = null,
    val Type: EmailType? = null,
    val IsTrackingAllowed: kotlin.Boolean? = null,
    val IsCopySentToAdmins: kotlin.Boolean? = null,
    val SenderId: kotlin.Int? = null,
    val SenderName: kotlin.String? = null,
    val SendingType: InitializationSourceType? = null,
    val Origin: EmailOrigin? = null,
    val SubOriginId: kotlin.Int? = null,
    val RecipientCount: kotlin.Int? = null,
    val ReadCount: kotlin.Int? = null,
    val UniqueLinkClickCount: kotlin.Int? = null,
    val SuccessfullySentCount: kotlin.Int? = null,
    val RecipientsThatClickedAnyLinkCount: kotlin.Int? = null,
    val FailedCount: kotlin.Int? = null,
    val InProgress: kotlin.Boolean? = null,
    /* only if email has a single recipient */
    val Recipient: EmailRecipient? = null
) {

}

