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
 * @param Id Unique bundle identifier.
 * @param Url
 * @param Email Email of bundle administrator.
 * @param ParticipantsCount The number of members in the bundle.
 * @param Administrator
 * @param MembershipLevel
 * @param SpacesLeft If bundle size is limited, then this field indicates number of vacant spaces left in the bundle.
 * @param Members Collection of links to members of the bundle.
 */
data class Bundle(
    /* Unique bundle identifier. */
    val Id: kotlin.Int? = null,
    val Url: ResourceUrl? = null,
    /* Email of bundle administrator. */
    val Email: kotlin.String? = null,
    /* The number of members in the bundle. */
    val ParticipantsCount: kotlin.Int? = null,
    val Administrator: BundleAdministrator? = null,
    val MembershipLevel: BundleAdministrator? = null,
    /* If bundle size is limited, then this field indicates number of vacant spaces left in the bundle. */
    val SpacesLeft: kotlin.Int? = null,
    /* Collection of links to members of the bundle. */
    val Members: kotlin.Array<LinkedResource>? = null
) {

}

