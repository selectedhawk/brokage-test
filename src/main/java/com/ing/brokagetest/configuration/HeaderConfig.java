/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ing.brokagetest.configuration;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 *
 * @author atmc
 */

@Configuration
@Log
@AllArgsConstructor
public class HeaderConfig {

    private HttpServletRequest request;

    public Long getCustomerId() {
        return Long.valueOf(Objects.toString(request.getHeader("customerId"), "0"));
    }
}
