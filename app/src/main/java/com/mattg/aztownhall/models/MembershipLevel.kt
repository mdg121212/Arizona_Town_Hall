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
 * @param Id Unique membership level identifier
 * @param Name level name
 * @param Description level description
 * @param PublicCanApply Indicates if non-member can apply for membership with this level.
 * @param Type Indicates if membership level is individual or bundle.
 * @param BundleMembersLimit Maximum number of bundle members. Empty for individuals.
 * @param MembershipFee Price of being a member with this level of membership. Price is for RenewalPeriod.
 * @param MemberCanChangeToLevels Member can switch from current level to one of levels from this list.
 * @param RenewalPeriod
 */
data class MembershipLevel(
    /* Unique membership level identifier */
    val Id: kotlin.Int? = null,
    /* level name */
    val Name: kotlin.String? = null,
    /* level description */
    val Description: kotlin.String? = null,
    /* Indicates if non-member can apply for membership with this level. */
    val PublicCanApply: kotlin.Boolean? = null,
    /* Indicates if membership level is individual or bundle. */
    val type: MembershipLevel.Type? = null,
    /* Maximum number of bundle members. Empty for individuals. */
    val BundleMembersLimit: kotlin.Int? = null,
    /* Price of being a member with this level of membership. Price is for RenewalPeriod. */
    val MembershipFee: java.math.BigDecimal? = null,
    /* Member can switch from current level to one of levels from this list. */
    val MemberCanChangeToLevels: kotlin.Array<LinkedResource>? = null,
    val RenewalPeriod: MembershipRenewalPeriod? = null
) {

    /**
     * Indicates if membership level is individual or bundle.
     * Values: individual,bundle
     */
    enum class Type(val value: kotlin.String) {

        @Json(name = "Individual")
        individual("Individual"),

        @Json(name = "Bundle")
        bundle("Bundle");

    }

}
