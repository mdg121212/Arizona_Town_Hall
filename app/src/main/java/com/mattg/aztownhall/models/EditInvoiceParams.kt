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
 * @param Value
 * @param DocumentDate Document date.
 * @param Contact
 * @param CreatedBy
 * @param UpdatedDate Date and time when the document was last modified.  Could be null.
 * @param UpdatedBy
 * @param DocumentNumber Invoice number.
 * @param OrderType
 * @param OrderDetails
 * @param Memo Internal note on invoice. Visible to administrators only.
 * @param PublicMemo Comment on invoice. Visible to both administrators and the person being invoicec.
 */
data class EditInvoiceParams(
    val Value: kotlin.Float? = null,
    /* Document date. */
    val DocumentDate: java.time.LocalDateTime? = null,
    val Contact: EditInvoiceParamsContact? = null,
    val CreatedBy: EditInvoiceParamsCreatedBy? = null,
    /* Date and time when the document was last modified.  Could be null. */
    val UpdatedDate: java.time.LocalDateTime? = null,
    val UpdatedBy: EditInvoiceParamsUpdatedBy? = null,
    /* Invoice number. */
    val DocumentNumber: kotlin.String? = null,
    val OrderType: InvoiceOrderType? = null,
    val OrderDetails: kotlin.Array<OrderDetailRecord>? = null,
    /* Internal note on invoice. Visible to administrators only. */
    val Memo: kotlin.String? = null,
    /* Comment on invoice. Visible to both administrators and the person being invoicec. */
    val PublicMemo: kotlin.String? = null
) {

}
