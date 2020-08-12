package io.github.jtsato.bookstore.entrypoint.rest.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Jorge Takeshi Sato
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpStatusConstants {

    public static final String CONTINUE_100 = "100";
    public static final String CONTINUE_100_MESSAGE = "Continue";

    public static final String SWITCHING_PROTOCOLS_101 = "101";
    public static final String SWITCHING_PROTOCOLS_101_MESSAGE = "Switching Protocols";

    public static final String PROCESSING_102 = "102";
    public static final String PROCESSING_102_MESSAGE = "Processing";

    public static final String CHECKPOINT_103 = "103";
    public static final String CHECKPOINT_103_MESSAGE = "Checkpoint";

    public static final String OK_200 = "200";
    public static final String OK_200_MESSAGE = "OK";

    public static final String CREATED_201 = "201";
    public static final String CREATED_201_MESSAGE = "Created";

    public static final String ACCEPTED_202 = "202";
    public static final String ACCEPTED_202_MESSAGE = "Accepted";

    public static final String NON_AUTHORITATIVE_INFORMATION_203 = "203";
    public static final String NON_AUTHORITATIVE_INFORMATION_203_MESSAGE = "Non-Authoritative Information";

    public static final String NO_CONTENT_204 = "204";
    public static final String NO_CONTENT_204_MESSAGE = "No Content";

    public static final String RESET_CONTENT_205 = "205";
    public static final String RESET_CONTENT_205_MESSAGE = "Reset Content";

    public static final String PARTIAL_CONTENT_206 = "206";
    public static final String PARTIAL_CONTENT_206_MESSAGE = "Partial Content";

    public static final String MULTI_STATUS_207 = "207";
    public static final String MULTI_STATUS_207_MESSAGE = "Multi-Status";

    public static final String ALREADY_REPORTED_208 = "208";
    public static final String ALREADY_REPORTED_208_MESSAGE = "Already Reported";

    public static final String IM_USED_226 = "226";
    public static final String IM_USED_MESSAGE_226 = "IM Used";

    public static final String MULTIPLE_CHOICES_300 = "300";
    public static final String MULTIPLE_CHOICES_300_MESSAGE = "Multiple Choices";

    public static final String MOVED_PERMANENTLY_301 = "301";
    public static final String MOVED_PERMANENTLY_301_MESSAGE = "Moved Permanently";

    public static final String FOUND_302 = "302";
    public static final String FOUND_302_MESSAGE = "Found";

    public static final String SEE_OTHER_303 = "303";
    public static final String SEE_OTHER_303_MESSAGE = "See Other";

    public static final String NOT_MODIFIED_304 = "304";
    public static final String NOT_MODIFIED_304_MESSAGE = "Not Modified";

    public static final String TEMPORARY_REDIRECT_307 = "307";
    public static final String TEMPORARY_REDIRECT_307_MESSAGE = "Temporary Redirect";

    public static final String PERMANENT_REDIRECT_308 = "308";
    public static final String PERMANENT_REDIRECT_308_MESSAGE = "Permanent Redirect";

    public static final String BAD_REQUEST_400 = "400";
    public static final String BAD_REQUEST_400_MESSAGE = "Bad Request";

    public static final String UNAUTHORIZED_401 = "401";
    public static final String UNAUTHORIZED_401_MESSAGE = "Unauthorized";

    public static final String PAYMENT_REQUIRED_402 = "402";
    public static final String PAYMENT_REQUIRED_402_MESSAGE = "Payment Required";

    public static final String FORBIDDEN_403 = "403";
    public static final String FORBIDDEN_403_MESSAGE = "Forbidden";

    public static final String NOT_FOUND_404 = "404";
    public static final String NOT_FOUND_404_MESSAGE = "Not Found";

    public static final String METHOD_NOT_ALLOWED_405 = "405";
    public static final String METHOD_NOT_ALLOWED_405_MESSAGE = "Method Not Allowed";

    public static final String NOT_ACCEPTABLE_406 = "406";
    public static final String NOT_ACCEPTABLE_406_MESSAGE = "Not Acceptable";

    public static final String PROXY_AUTHENTICATION_407_REQUIRED = "407";
    public static final String PROXY_AUTHENTICATION_407_REQUIRED_MESSAGE = "Proxy Authentication Required";

    public static final String REQUEST_TIMEOUT_408 = "408";
    public static final String REQUEST_TIMEOUT_408_MESSAGE = "Request Timeout";

    public static final String CONFLICT_409 = "409";
    public static final String CONFLICT_409_MESSAGE = "Conflict";

    public static final String GONE_410 = "410";
    public static final String GONE_410_MESSAGE = "Gone";

    public static final String LENGTH_REQUIRED_411 = "411";
    public static final String LENGTH_REQUIRED_411_MESSAGE = "Length Required";

    public static final String PRECONDITION_FAILED_412 = "412";
    public static final String PRECONDITION_FAILED_412_MESSAGE = "Precondition Failed";

    public static final String PAYLOAD_TOO_LARGE_413 = "413";
    public static final String PAYLOAD_TOO_LARGE_413_MESSAGE = "Payload Too Large";

    public static final String URI_TOO_LONG_414 = "414";
    public static final String URI_TOO_LONG_414_MESSAGE = "URI Too Long";

    public static final String UNSUPPORTED_MEDIA_TYPE_415 = "415";
    public static final String UNSUPPORTED_MEDIA_TYPE_415_MESSAGE = "Unsupported Media Type";

    public static final String REQUESTED_RANGE_NOT_SATISFIABLE_416 = "416";
    public static final String REQUESTED_RANGE_NOT_SATISFIABLE_416_MESSAGE = "Requested range not satisfiable";

    public static final String EXPECTATION_FAILED_417 = "417";
    public static final String EXPECTATION_FAILED_417_MESSAGE = "Expectation Failed";

    public static final String I_AM_A_TEAPOT_418 = "418";
    public static final String I_AM_A_TEAPOT_418_MESSAGE = "I'm a teapot";

    public static final String UNPROCESSABLE_ENTITY_422 = "422";
    public static final String UNPROCESSABLE_ENTITY_422_MESSAGE = "Unprocessable Entity";

    public static final String LOCKED_423 = "423";
    public static final String LOCKED_423_MESSAGE = "Locked";

    public static final String FAILED_DEPENDENCY_424 = "424";
    public static final String FAILED_DEPENDENCY_424_MESSAGE = "Failed Dependency";

    public static final String TOO_EARLY_425 = "425";
    public static final String TOO_EARLY_425_MESSAGE = "Too Early";

    public static final String UPGRADE_REQUIRED_426 = "426";
    public static final String UPGRADE_REQUIRED_426_MESSAGE = "Upgrade Required";

    public static final String PRECONDITION_REQUIRED_428 = "428";
    public static final String PRECONDITION_REQUIRED_428_MESSAGE = "Precondition Required";

    public static final String TOO_MANY_REQUESTS_429 = "429";
    public static final String TOO_MANY_REQUESTS_429_MESSAGE = "Too Many Requests";

    public static final String REQUEST_HEADER_FIELDS_TOO_LARGE_431 = "431";
    public static final String REQUEST_HEADER_FIELDS_TOO_LARGE_431_MESSAGE = "Request Header Fields Too Large";

    public static final String UNAVAILABLE_FOR_LEGAL_REASONS_451 = "451";
    public static final String UNAVAILABLE_FOR_LEGAL_REASONS_451_MESSAGE = "Unavailable For Legal Reasons";

    public static final String INTERNAL_SERVER_ERROR_500 = "500";
    public static final String INTERNAL_SERVER_ERROR_500_MESSAGE = "Internal Server Error";

    public static final String NOT_IMPLEMENTED_501 = "501";
    public static final String NOT_IMPLEMENTED_501_MESSAGE = "Not Implemented";

    public static final String BAD_GATEWAY_502 = "502";
    public static final String BAD_GATEWAY_502_MESSAGE = "Bad Gateway";

    public static final String SERVICE_UNAVAILABLE_503 = "503";
    public static final String SERVICE_UNAVAILABLE_503_MESSAGE = "Service Unavailable";

    public static final String GATEWAY_TIMEOUT_504 = "504";
    public static final String GATEWAY_TIMEOUT_504_MESSAGE = "Gateway Timeout";

    public static final String HTTP_VERSION_NOT_SUPPORTED_505 = "505";
    public static final String HTTP_VERSION_NOT_SUPPORTED_505_MESSAGE = "HTTP Version not supported";

    public static final String VARIANT_ALSO_NEGOTIATES_506 = "506";
    public static final String VARIANT_ALSO_NEGOTIATES_506_MESSAGE = "Variant Also Negotiates";

    public static final String INSUFFICIENT_STORAGE_507 = "507";
    public static final String INSUFFICIENT_STORAGE_507_MESSAGE = "Insufficient Storage";

    public static final String LOOP_DETECTED_508 = "508";
    public static final String LOOP_DETECTED_508_MESSAGE = "Loop Detected";

    public static final String BANDWIDTH_LIMIT_EXCEEDED_509 = "509";
    public static final String BANDWIDTH_LIMIT_EXCEEDED_509_MESSAGE = "Bandwidth Limit Exceeded";

    public static final String NOT_EXTENDED_510 = "510";
    public static final String NOT_EXTENDED_510_MESSAGE = "Not Extended";

    public static final String NETWORK_AUTHENTICATION_REQUIRED_511 = "511";
    public static final String NETWORK_AUTHENTICATION_REQUIRED_511_MESSAGE = "Network Authentication Required";
}
