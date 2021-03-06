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
 * @param DraftId Unique email draft identifier.
 * @param ScheduleDate Date and time scheduled for sending.
 * @param IsScheduled Indicates if draft is scheduled for delivery. Set False to pause.
 */
data class EmailScheduleParams(
    /* Unique email draft identifier. */
    val DraftId: kotlin.Int? = null,
    /* Date and time scheduled for sending. */
    val ScheduleDate: kotlin.String? = null,
    /* Indicates if draft is scheduled for delivery. Set False to pause. */
    val IsScheduled: kotlin.Boolean? = null
) {

}

