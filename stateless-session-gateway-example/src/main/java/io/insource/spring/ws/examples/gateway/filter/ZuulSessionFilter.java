package io.insource.spring.ws.examples.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
public class ZuulSessionFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER + 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        doFilter();

        return null;
    }

    private void doFilter() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            // Should never happen, but could be null (see javadoc for SecurityContext#getAuthentication)
            return;
        }

        Object principal = authentication.getPrincipal();

        // Add header if user is not anonymous
        if (principal instanceof User) {
            User user = (User) principal;

            RequestContext requestContext = RequestContext.getCurrentContext();
            requestContext.addZuulRequestHeader("X-USERNAME", user.getUsername());
        }
    }
}
