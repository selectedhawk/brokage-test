package com.ing.brokagetest.configuration;

import com.ing.brokagetest.dto.CustomerDTO;
import com.ing.brokagetest.enums.EnumCustomerType;
import com.ing.brokagetest.service.CustomerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@AllArgsConstructor
@Configuration
@Log
public class AuthorizationFilter extends OncePerRequestFilter {
    private final CustomerService customerService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        log.info(request.getRequestURI());

        h2Console(request, response, authHeader, filterChain);
    }
    private void h2Console(HttpServletRequest request, HttpServletResponse response, String authHeader, FilterChain filterChain) throws IOException, ServletException {
        if ( request.getRequestURI().startsWith("/h2")) {
            grantPermission(request, response, new CustomerDTO(), filterChain);
            return;
        }
        unAuthorized(request, response, authHeader, filterChain);
    }

    private void unAuthorized(HttpServletRequest request, HttpServletResponse response, String authHeader, FilterChain filterChain) throws IOException, ServletException {
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Basic ") || !request.getRequestURI().startsWith("/h2")) {
            errorMessage(response, "Missing or invalid Authorization header");
            return;
        }
        invalidCredential(request, response, authHeader, filterChain);
    }

    private void invalidCredential(HttpServletRequest request, HttpServletResponse response, String authHeader, FilterChain filterChain) throws IOException, ServletException {
        String base64Credentials = authHeader.substring("Basic ".length());
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        String[] parts = credentials.split(":", 2);
        CustomerDTO customer = getCustomer(parts[0], parts[1]);
        if (parts.length != 2 || customer == null ) {
            errorMessage(response, "Invalid credentials");
            return;
        }
        denyPermission(request, response, customer, filterChain);
    }

    private void denyPermission(HttpServletRequest request, HttpServletResponse response, CustomerDTO customer, FilterChain filterChain) throws IOException, ServletException {
        if (customer.getCustomerType().equals(EnumCustomerType.USER) && !request.getRequestURI().startsWith("user-")){
            errorMessage(response, "Permission denied");
            return;
        }

        grantPermission(request, response, customer, filterChain);
    }

    private void grantPermission(HttpServletRequest request, HttpServletResponse response, CustomerDTO customer, FilterChain filterChain) throws IOException, ServletException {
        BaseHttpServletRequest httpRequest = new BaseHttpServletRequest(request);
        httpRequest.putHeader("customerId", Optional.ofNullable(customer.getId()).orElse(0L).toString());
        filterChain.doFilter(httpRequest, response);
    }

    private CustomerDTO getCustomer(String username, String password) {
        try {
            return customerService.findByNameAndPassword(username, password);
        }catch (Exception e) {
            return null;
        }
    }

    private void errorMessage(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized: " + message);
    }
}
