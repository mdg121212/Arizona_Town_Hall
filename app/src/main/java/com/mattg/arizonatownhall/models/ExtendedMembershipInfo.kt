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
 * Readonly object
 * @param PendingMembershipOrderStatusType
 * @param PendingMembershipInvoice
 * @param AllowedActions List of allowed actions related to contact. Each action is described with title and URL, which should be used in POST request.
 */
data class ExtendedMembershipInfo(
    val pendingMembershipOrderStatusType: ExtendedMembershipInfo.PendingMembershipOrderStatusType? = null,
    val PendingMembershipInvoice: BundleAdministrator? = null,
    /* List of allowed actions related to contact. Each action is described with title and URL, which should be used in POST request. */
    val AllowedActions: kotlin.Array<LinkedResourceWithName>? = null
) {

    /**
     *
     * Values: invisible,pendingNoCorrespondingInvoice,pendingNotPaidYet,invoicePaidManualApprovalRequired,freeOrderManualApprovalRequired,invoiceNotPaidButOperationApproved,multipleInvoiceNotPaidMemberActivated,multipleInvoiceNotPaidMemberPending
     */
    enum class PendingMembershipOrderStatusType(val value: kotlin.String) {

        @Json(name = "Invisible")
        invisible("Invisible"),

        @Json(name = "PendingNoCorrespondingInvoice")
        pendingNoCorrespondingInvoice("PendingNoCorrespondingInvoice"),

        @Json(name = "PendingNotPaidYet")
        pendingNotPaidYet("PendingNotPaidYet"),

        @Json(name = "InvoicePaidManualApprovalRequired")
        invoicePaidManualApprovalRequired("InvoicePaidManualApprovalRequired"),

        @Json(name = "FreeOrderManualApprovalRequired")
        freeOrderManualApprovalRequired("FreeOrderManualApprovalRequired"),

        @Json(name = "InvoiceNotPaidButOperationApproved")
        invoiceNotPaidButOperationApproved("InvoiceNotPaidButOperationApproved"),

        @Json(name = "MultipleInvoiceNotPaidMemberActivated")
        multipleInvoiceNotPaidMemberActivated("MultipleInvoiceNotPaidMemberActivated"),

        @Json(name = "MultipleInvoiceNotPaidMemberPending")
        multipleInvoiceNotPaidMemberPending("MultipleInvoiceNotPaidMemberPending");

    }

}

