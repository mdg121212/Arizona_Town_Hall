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
 * @param Tender
 * @param Comment Internal note on refund. Visible to administrators only.
 * @param PublicComment Comment on refund. Visible to both administrators and person being refunded.
 * @param SettledValue The previously settled amount of the payment.
 */
data class UpdateRefundParams(
    val Tender: LinkedResourceWithName? = null,
    /* Internal note on refund. Visible to administrators only. */
    val Comment: kotlin.String? = null,
    /* Comment on refund. Visible to both administrators and person being refunded. */
    val PublicComment: kotlin.String? = null,
    /* The previously settled amount of the payment. */
    val SettledValue: kotlin.Float? = null,
    /* Unique document identifier. */
    val Id: kotlin.Int? = null,
    val Url: ResourceUrl? = null,
    val Value: kotlin.Float? = null,
    /* Document date. */
    val DocumentDate: java.time.LocalDateTime? = null,
    val Contact: BundleAdministrator? = null,
    /* Date and time when the document was created. */
    val CreatedDate: java.time.LocalDateTime? = null,
    val CreatedBy: BundleAdministrator? = null,
    /* Date and time when the document was last modified.  Could be null. */
    val UpdatedDate: java.time.LocalDateTime? = null,
    val UpdatedBy: BundleAdministrator? = null,

    ) {

}

