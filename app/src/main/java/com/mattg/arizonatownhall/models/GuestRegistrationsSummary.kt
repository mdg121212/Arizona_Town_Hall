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
 * @param NumberOfGuests Number of guests registered along with the registrant. If event registration type requires guests registration with details (contact information, etc.), this value is 0.
 * @param NumberOfGuestsCheckedIn Number of guests who already checked in. If event registration type requires guests registration with details (contact information, etc.), this value is null.
 * @param GuestRegistrations List of guest registrations links. If event registration type expects guest registration by number (without contact information), this value is null. This value cannot be saved or updated using API.
 */
data class GuestRegistrationsSummary(
    /* Number of guests registered along with the registrant. If event registration type requires guests registration with details (contact information, etc.), this value is 0. */
    val NumberOfGuests: kotlin.Int? = null,
    /* Number of guests who already checked in. If event registration type requires guests registration with details (contact information, etc.), this value is null. */
    val NumberOfGuestsCheckedIn: kotlin.Int? = null,
    /* List of guest registrations links. If event registration type expects guest registration by number (without contact information), this value is null. This value cannot be saved or updated using API.  */
    val GuestRegistrations: kotlin.Array<LinkedResource>? = null
) {

}
