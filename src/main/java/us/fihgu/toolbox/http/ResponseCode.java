package us.fihgu.toolbox.http;


public enum ResponseCode
{
	/**
	 * The request has succeeded
	 */
	OK(200),
	/**
	 * The request could not be understood by the server due to malformed syntax. The client SHOULD NOT repeat the request without modifications.
	 */
	BAD_REQUEST(400),
	/**
	 * The server has not found anything matching the Request-URI. No indication is given of whether the condition is temporary or permanent. The 410 (Gone) status code SHOULD be used if the server knows, through some internally configurable mechanism, that an old resource is permanently unavailable and has no forwarding address. This status code is commonly used when the server does not wish to reveal exactly why the request has been refused, or when no other response is applicable.
	 */
	NOT_FOUND(404),
	/**
	 * The server encountered an unexpected condition which prevented it from fulfilling the request.
	 */
	INTERNAL_SERVER_ERROR(500),
	/**
	 * The server does not support, or refuses to support, the HTTP protocol version that was used in the request message.
	 */
	HTTP_VERSION_NOT_SUPPORTED(505),
	/**
	 * The client did not produce a request within the time that the server was prepared to wait. The client MAY repeat the request without modifications at any later time.
	 */
	REQUEST_TIMEOUT(408);
	private int code;
	ResponseCode(int code)
	{
		this.code = code;
	}
	
	public int getCode()
	{
		return this.code;
	}
	
	@Override
	public String toString()
	{
		return this.name().replace('_', ' ');
	}
}
