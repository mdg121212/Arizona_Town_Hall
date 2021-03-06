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
 * @param EventId Identifier of existing event you want to clone.
 * @param TitlePrefix Prefix to be added to the event title to form the new title. For example if you are cloning the event \"Annual meetup\" and the prefix is \"yet another \", then the new event will have the title \"yet another Annual meetup\".
 */
data class CloneEvent(
    /* Identifier of existing event you want to clone. */
    val EventId: kotlin.Int? = null,
    /* Prefix to be added to the event title to form the new title. For example if you are cloning the event \"Annual meetup\" and the prefix is \"yet another \", then the new event will have the title \"yet another Annual meetup\". */
    val TitlePrefix: kotlin.String? = null
) {

}

