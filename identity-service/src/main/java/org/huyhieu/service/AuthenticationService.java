package org.huyhieu.service;

import com.nimbusds.jose.JOSEException;
import org.huyhieu.dto.request.AuthenticationRequest;
import org.huyhieu.dto.request.IntrospectionRequest;
import org.huyhieu.dto.response.AuthenticationResponse;
import org.huyhieu.dto.response.IntrospectionResponse;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);

    IntrospectionResponse introspect(IntrospectionRequest request) throws JOSEException, ParseException;
}
