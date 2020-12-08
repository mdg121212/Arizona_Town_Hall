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
 * @param Id Item identifier unique in the list
 * @param Label Human-readable text label.
 * @param Position Option position among other options.
 * @param SelectedByDefault Indicates whether this option would be selected on form if user did not select another option.
 * @param ExtraCost Extra price for selecting this option. Used only by fields with an associated cost.
 */
data class OptionsListItem(
    /* Item identifier unique in the list */
    val Id: kotlin.Int? = null,
    /* Human-readable text label. */
    val Label: kotlin.String? = null,
    /* Option position among other options. */
    val Position: kotlin.Int? = null,
    /* Indicates whether this option would be selected on form if user did not select another option. */
    val SelectedByDefault: kotlin.Boolean? = null,
    /* Extra price for selecting this option. Used only by fields with an associated cost. */
    val ExtraCost: java.math.BigDecimal? = null
) {

}
