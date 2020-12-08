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
 * @param DescriptionHtml Full event description in HTML format. Important - Links to images can be relative.
 * @param PaymentInstructions A description how registrant can pay for attendance to this event. Payment instructions for this event only.
 * @param TimeZone
 * @param RegistrationTypes Collection of configured registration types for the event.
 * @param EventRegistrationFields Collection of fields registrant should fill during registration process.
 * @param TotalPaid Total sum paid for all registrations to this event. This is a read-only property.
 * @param TotalDue Total sum due, but not necessarily paid yet for all registrations related to this event. This is a read-only property.
 * @param AccessControl
 * @param GuestRegistrationSettings
 * @param Organizer
 * @param PaymentMethod
 * @param RegistrationConfirmationExtraInfo Additional event information to be inserted in registration confirmation email
 * @param RegistrationMessage This text will be shown above Register button on Event details
 * @param SendEmailCopy Indicates if email copy should be sent according to email routing settings.
 * @param IsWaitlistEnabled Indicates if waitlist feature is enabled for the event.
 * @param WaitlistSettings
 * @param MultipleRegistrationAllowed Indicates if multiple registrations are allowed for the same contact.
 * @param AttendeesDisplaySettings
 */
data class EventDetails(
    /* Full event description in HTML format. Important - Links to images can be relative. */
    val DescriptionHtml: kotlin.String? = null,
    /* A description how registrant can pay for attendance to this event. Payment instructions for this event only. */
    val PaymentInstructions: kotlin.String? = null,
    val TimeZone: TimeZone? = null,
    /* Collection of configured registration types for the event. */
    val RegistrationTypes: kotlin.Array<EventRegistrationType>? = null,
    /* Collection of fields registrant should fill during registration process. */
    val EventRegistrationFields: kotlin.Array<EventRegistrationFieldDescription>? = null,
    /* Total sum paid for all registrations to this event. This is a read-only property. */
    val TotalPaid: java.math.BigDecimal? = null,
    /* Total sum due, but not necessarily paid yet for all registrations related to this event. This is a read-only property. */
    val TotalDue: java.math.BigDecimal? = null,
    val AccessControl: EventAccessControl? = null,
    val GuestRegistrationSettings: EventGuestRegistrationSettings? = null,
    val Organizer: BundleAdministrator? = null,
    val PaymentMethod: EventPaymentMethodType? = null,
    /* Additional event information to be inserted in registration confirmation email */
    val RegistrationConfirmationExtraInfo: kotlin.String? = null,
    /* This text will be shown above Register button on Event details */
    val RegistrationMessage: kotlin.String? = null,
    /* Indicates if email copy should be sent according to email routing settings. */
    val SendEmailCopy: kotlin.Boolean? = null,
    /* Indicates if waitlist feature is enabled for the event. */
    val IsWaitlistEnabled: kotlin.Boolean? = null,
    val WaitlistSettings: EventWaitlistSettings? = null,
    /* Indicates if multiple registrations are allowed for the same contact. */
    val MultipleRegistrationAllowed: kotlin.Boolean? = null,
    val AttendeesDisplaySettings: EventAttendeesDisplaySettings? = null
) {

}

