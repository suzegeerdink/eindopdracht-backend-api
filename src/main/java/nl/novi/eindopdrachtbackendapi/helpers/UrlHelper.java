package nl.novi.eindopdrachtbackendapi.helpers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class UrlHelper {

    private final HttpServletRequest request;

    public UrlHelper(HttpServletRequest request) {
    this.request = request;
    }

    public URI getCurrentUrlWithId(Long id) {
        String url = request.getRequestURL().toString() + "/" + id;
        return URI.create(url);
    }

}
