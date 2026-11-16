package com.ssheavyhaulage.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssheavyhaulage.dto.Coordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@Service
public class OsrmRoutingService implements RoutingService{

    private static final Logger log = LoggerFactory.getLogger(OsrmRoutingService.class);
    private final RestClient restClient;

    public OsrmRoutingService(RestClient restClient) {
        this.restClient = restClient;
    }
}
