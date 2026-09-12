package com.example.skbazaar.controller;

import com.example.skbazaar.constants.AppConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/info")
public class InfoController {

    @GetMapping("/branding")
    public Map<String, String> getBrandingInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("appName", AppConstants.APP_NAME);
        info.put("tagline", AppConstants.TAGLINE);
        info.put("website", AppConstants.WEBSITE_NAME);
        info.put("sellerPortal", AppConstants.SELLER_PORTAL_NAME);
        info.put("deliveryApp", AppConstants.DELIVERY_APP_NAME);
        info.put("adminPortal", AppConstants.ADMIN_PORTAL_NAME);
        return info;
    }
}
