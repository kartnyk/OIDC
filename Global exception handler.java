import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle Unsupported Media Type (415) Exception
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            org.springframework.web.context.request.WebRequest request) {

        // Log the exception details
        System.err.println("Exception: HttpMediaTypeNotSupportedException");
        System.err.println("Message: " + ex.getMessage());
        System.err.println("Supported Media Types: " + ex.getSupportedMediaTypes());

        // Prepare the response body
        String errorDetails = "Unsupported Media Type. Supported media types are: " + ex.getSupportedMediaTypes();

        // Return a custom response
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body("{\"error\":\"" + errorDetails + "\"}");
    }
}
