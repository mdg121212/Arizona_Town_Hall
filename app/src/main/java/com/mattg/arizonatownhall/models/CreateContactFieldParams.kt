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
 * @param FieldType Field data type.
 * @param MemberOnly Indicates whether a field is available only to members.
 */
data class CreateContactFieldParams(
    /* Field data type. */
    val fieldType: CreateContactFieldParams.FieldType,
    /* New field name. Must be unique. */
    val FieldName: kotlin.String,
    /* Field data type. */

    /* Indicates whether a field is available only to members. */
    val memberOnly: kotlin.Boolean? = null,
    /* Indicates whether a field has to be filled before the form is submitted. This limitation affects member-facing functionality only, admin always can submit form even without required fields. */
    val IsRequired: kotlin.Boolean? = null,
    /* Indicates that the field is accessible only by administrators. */
    val AdminOnly: kotlin.Boolean? = null,
    val Access: ContactFieldAccessLevel? = null,
    /* Shown as a tip when members or visitors fill in forms. Max length is 250 characters. */
    val FieldInstructions: kotlin.String? = null,
    /* Sorting order to display the field in UI. */
    val Order: kotlin.Int? = null,
    /* List of allowed values for this field. This data is used for Choice and MultipleChoice fields, otherwise it will be ignored. */
    val AllowedValues: kotlin.Array<OptionsListItem>? = null,
    val RulesAndTermsInfo: RulesAndTermsInfo? = null,
    /* Collection of membership levels where the field exists. If empty collection provided, it means the field would be available for all levels. */
    val ExistsInLevels: kotlin.Array<EditContactFieldParamsExistsInLevels>? = null,
    val MemberAccess: MemberFieldAccess? = null,
    val RenewalPolicy: RenewalPolicy? = null,
    /* Whether the cost should be prorated over a partial period of time. Used only by fields with an associated cost. */
    val ProrateInApplication: kotlin.Boolean? = null,
    val ExtraCharge: ExtraCharge? = null,
    /* Indicates whether a field is available only to members. */

) {

    /**
     * Field data type.
     * Values: text,multilineText,multipleChoice,multipleChoiceWithExtraCharge,radioButtons,radioButtonsWithExtraCharge,dropdown,picture,rulesAndTerms,date,extraChargeCalculation,sectionDivider
     */
    enum class FieldType(val value: kotlin.String) {

        @Json(name = "Text")
        text("Text"),

        @Json(name = "MultilineText")
        multilineText("MultilineText"),

        @Json(name = "MultipleChoice")
        multipleChoice("MultipleChoice"),

        @Json(name = "MultipleChoiceWithExtraCharge")
        multipleChoiceWithExtraCharge("MultipleChoiceWithExtraCharge"),

        @Json(name = "RadioButtons")
        radioButtons("RadioButtons"),

        @Json(name = "RadioButtonsWithExtraCharge")
        radioButtonsWithExtraCharge("RadioButtonsWithExtraCharge"),

        @Json(name = "Dropdown")
        dropdown("Dropdown"),

        @Json(name = "Picture")
        picture("Picture"),

        @Json(name = "RulesAndTerms")
        rulesAndTerms("RulesAndTerms"),

        @Json(name = "Date")
        date("Date"),

        @Json(name = "ExtraChargeCalculation")
        extraChargeCalculation("ExtraChargeCalculation"),

        @Json(name = "SectionDivider")
        sectionDivider("SectionDivider");

    }

}

