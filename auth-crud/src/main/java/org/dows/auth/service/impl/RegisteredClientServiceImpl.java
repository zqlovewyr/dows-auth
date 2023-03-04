package org.dows.auth.service.impl;

import org.dows.auth.crud.service.RegisteredClientService;

public class RegisteredClientServiceImpl implements RegisteredClientService {
    @Override
    public void saveClient(OAuth2ClientDTO client) {

    }

    @Override
    public void update(OAuth2ClientDTO client) {

    }

    @Override
    public Page<OAuth2Client> page(Pageable pageable) {
        return null;
    }

    @Override
    public OAuth2Client findClientById(String id) {
        return null;
    }

    @Override
    public void removeByClientId(String id) {

    }
}
