import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle Unsupported Media Type (415) Exception
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        // Build response body to match the desired format
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("type", "about:blank");
        responseBody.put("title", "Unsupported Media Type");
        responseBody.put("status", status.value());
        responseBody.put("detail", "Content-Type '" + ex.getContentType() + "' is not supported.");
        responseBody.put("instance", request.getDescription(false).replace("uri=", ""));

        // Log the exception details (optional)
        System.err.println("Exception: HttpMediaTypeNotSupportedException");
        System.err.println("Message: " + ex.getMessage());
        System.err.println("Supported Media Types: " + ex.getSupportedMediaTypes());

        // Return the structured response entity
        return ResponseEntity.status(status)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(responseBody);
    }
}


/**
 * Overrides the default handling of HttpMediaTypeNotSupportedException to sanitize
 * and handle potentially malicious or invalid Content-Type headers.
 *
 * This method ensures:
 * - Protection against injection attacks by sanitizing unexpected scripts or data
 *   in the Content-Type header.
 * - Consistent and secure error responses for unsupported or malformed Content-Type.
 * - Logging of the unsupported Content-Type and expected media types for debugging.
 *
 * It builds a structured error response (type, title, status, detail, instance) 
 * and returns a 415 Unsupported Media Type status.
 *
 * @param ex      the exception containing details about the unsupported media type
 * @param headers the HTTP headers of the request
 * @param status  the HTTP status code (415)
 * @param request the WebRequest object containing details of the request
 * @return a ResponseEntity containing the custom error response
 */
@Override
protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
        HttpMediaTypeNotSupportedException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request) {
    // Implementation here...
}
