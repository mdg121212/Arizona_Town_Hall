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
 *
 * @param Id Unique identifier.
 * @param Url
 * @param Timestamp Item creation date.
 * @param Contact
 * @param FirstName Contact first name
 * @param LastName Contact last name
 * @param Organization Contact organization
 * @param Email Contact primary email.
 * @param Message Log item text.
 * @param Severity Log item severity level.
 * @param OrderType The origin of the audit log entry.
 * @param Properties Collection of audit log item properties that contains all technical information about transaction and other linked documents.
 * @param Document
 * @param DocumentType Related document type.
 * @param DocumentAction Action performed on related document type.
 */
data class AuditLogItem(
    /* Unique identifier. */
    val Id: kotlin.Int? = null,
    val Url: ResourceUrl? = null,
    /* Item creation date. */
    val Timestamp: java.time.LocalDateTime? = null,
    val Contact: BundleAdministrator? = null,
    /* Contact first name */
    val FirstName: kotlin.String? = null,
    /* Contact last name */
    val LastName: kotlin.String? = null,
    /* Contact organization */
    val Organization: kotlin.String? = null,
    /* Contact primary email. */
    val Email: kotlin.String? = null,
    /* Log item text. */
    val Message: kotlin.String? = null,
    /* Log item severity level. */
    val severity: AuditLogItem.Severity? = null,
    /* The origin of the audit log entry. */
    val orderType: AuditLogItem.OrderType? = null,
    /* Collection of audit log item properties that contains all technical information about transaction and other linked documents. */
    val Properties: kotlin.Any? = null,
    val Document: BundleAdministrator? = null,
    /* Related document type. */
    val documentType: AuditLogItem.DocumentType? = null,
    /* Action performed on related document type. */
    val documentAction: AuditLogItem.DocumentAction? = null
) {

    /**
     * Log item severity level.
     * Values: verbose,information,attentionRequired,warning,error,critical
     */
    enum class Severity(val value: kotlin.String) {

        @Json(name = "Verbose")
        verbose("Verbose"),

        @Json(name = "Information")
        information("Information"),

        @Json(name = "AttentionRequired")
        attentionRequired("AttentionRequired"),

        @Json(name = "Warning")
        warning("Warning"),

        @Json(name = "Error")
        error("Error"),

        @Json(name = "Critical")
        critical("Critical");

    }

    /**
     * The origin of the audit log entry.
     * Values: membershipApplication,membershipRenewal,membershipLevelChange,eventRegistration,donation,changeBillingPlan,changeBillingInfo,lockInPlan,associationRenewal
     */
    enum class OrderType(val value: kotlin.String) {

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

        @Json(name = "ChangeBillingPlan")
        changeBillingPlan("ChangeBillingPlan"),

        @Json(name = "ChangeBillingInfo")
        changeBillingInfo("ChangeBillingInfo"),

        @Json(name = "LockInPlan")
        lockInPlan("LockInPlan"),

        @Json(name = "AssociationRenewal")
        associationRenewal("AssociationRenewal");

    }

    /**
     * Related document type.
     * Values: invoice,payment,creditMemo,refund,adjustment,donationPayment,invoicePayment
     */
    enum class DocumentType(val value: kotlin.String) {

        @Json(name = "Invoice")
        invoice("Invoice"),

        @Json(name = "Payment")
        payment("Payment"),

        @Json(name = "CreditMemo")
        creditMemo("CreditMemo"),

        @Json(name = "Refund")
        refund("Refund"),

        @Json(name = "Adjustment")
        adjustment("Adjustment"),

        @Json(name = "DonationPayment")
        donationPayment("DonationPayment"),

        @Json(name = "InvoicePayment")
        invoicePayment("InvoicePayment");

    }

    /**
     * Action performed on related document type.
     * Values: created,changed,deleted,voided,custom
     */
    enum class DocumentAction(val value: kotlin.String) {

        @Json(name = "Created")
        created("Created"),

        @Json(name = "Changed")
        changed("Changed"),

        @Json(name = "Deleted")
        deleted("Deleted"),

        @Json(name = "Voided")
        voided("Voided"),

        @Json(name = "Custom")
        custom("Custom");

    }

}
