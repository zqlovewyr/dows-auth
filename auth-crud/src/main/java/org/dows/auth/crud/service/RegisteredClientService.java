package org.dows.auth.crud.service;


public interface RegisteredClientService {

    /**
     * Save client.
     *
     * @param client the client
     */
    void saveClient(OAuth2ClientDTO client);

    /**
     * Update.
     *
     * @param client the client
     */
    void update(OAuth2ClientDTO client);

    /**
     * Page page.
     *
     * @param pageable the pageable
     * @return the page
     */
    Page<OAuth2Client> page(Pageable pageable);

    /**
     * Find client by id o auth 2 client.
     *
     * @param id the id
     * @return the o auth 2 client
     */
    OAuth2Client findClientById(String id);

    /**
     * Remove by client id.
     *
     * @param id the id
     */
    void removeByClientId(String id);
}
