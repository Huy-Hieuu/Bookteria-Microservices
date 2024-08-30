package org.huyhieu.service;

import org.huyhieu.dto.request.AuthenticationRequest;

public interface AuthenticationService {
    boolean authenticate(AuthenticationRequest request);
}
