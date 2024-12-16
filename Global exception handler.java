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
