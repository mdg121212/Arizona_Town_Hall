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
 * @param Id Contact unique identifier.
 * @param Url
 * @param DisplayName Combination of names or organization or email. Value depends on presence of values. It could be used to display contact record on lists.
 * @param FirstName Shortcut for custom field value with system code 'FirstName'
 * @param LastName Shortcut for custom field value with system code 'LastName'
 * @param Email Contact primary email, shortcut for custom field value with system code 'Email'. Should be unique in account.
 * @param Organization Shortcut for custom field value with system code 'Organization'
 * @param Status The status of the contact's membership. The status is only included in the results if the contact is a member.
 * @param MembershipLevel
 * @param IsAccountAdministrator Indicates if the contact is an account administrator.
 * @param TermsOfUseAccepted Indicates if the contact already accepted Wild Apricot's terms of use.
 */
data class ContactsMe(
    /* Contact unique identifier. */
    val Id: kotlin.Int? = null,
    val Url: ResourceUrl? = null,
    /* Combination of names or organization or email. Value depends on presence of values. It could be used to display contact record on lists. */
    val DisplayName: kotlin.String? = null,
    /* Shortcut for custom field value with system code 'FirstName' */
    val FirstName: kotlin.String? = null,
    /* Shortcut for custom field value with system code 'LastName' */
    val LastName: kotlin.String? = null,
    /* Contact primary email, shortcut for custom field value with system code 'Email'. Should be unique in account. */
    val Email: kotlin.String? = null,
    /* Shortcut for custom field value with system code 'Organization' */
    val Organization: kotlin.String? = null,
    /* The status of the contact's membership. The status is only included in the results if the contact is a member. */
    val status: ContactsMe.Status? = null,
    val MembershipLevel: BundleAdministrator? = null,
    /* Indicates if the contact is an account administrator. */
    val IsAccountAdministrator: kotlin.Boolean? = null,
    /* Indicates if the contact already accepted Wild Apricot's terms of use. */
    val TermsOfUseAccepted: kotlin.Boolean? = null
) {

    /**
     * The status of the contact's membership. The status is only included in the results if the contact is a member.
     * Values: active,lapsed,pendingNew,pendingRenewal,pendingUpgrade
     */
    enum class Status(val value: kotlin.String) {

        @Json(name = "Active")
        active("Active"),

        @Json(name = "Lapsed")
        lapsed("Lapsed"),

        @Json(name = "PendingNew")
        pendingNew("PendingNew"),

        @Json(name = "PendingRenewal")
        pendingRenewal("PendingRenewal"),

        @Json(name = "PendingUpgrade")
        pendingUpgrade("PendingUpgrade");

    }

}
