package io.redbyzan.spystagram.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;

import java.util.Collection;

/**
 * Created by roger on 2016. 4. 27..
 */
public class SparklrUserApprovalHandler extends ApprovalStoreUserApprovalHandler {

    private boolean useApprovalStore = true;

    private ClientDetailsService clientDetailsService;


    /**
     * Service to load client details (optional) for auto approval checks.
     *
     * @param clientDetailsService a client details service
     */
    public void setClientDetailsService(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
        super.setClientDetailsService(clientDetailsService);
    }


    /**
     * @param useApprovalStore the useTokenServices to set
     */
    public void setUseApprovalStore(boolean useApprovalStore) {
        this.useApprovalStore = useApprovalStore;
    }

    /**
     * Allows automatic approval for a white list of clients in the implicit grant case.
     *
     * @param authorizationRequest The authorization request.
     * @param userAuthentication the current user authentication
     *
     * @return An updated request if it has already been approved by the current user.
     */
    @Override
    public AuthorizationRequest checkForPreApproval(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {

        boolean approved = false;

        if ( useApprovalStore ) {

            authorizationRequest = super.checkForPreApproval(authorizationRequest, userAuthentication);
            approved = authorizationRequest.isApproved();
        }
        else {

            if ( clientDetailsService != null ) {

                Collection<String> requestedScopes = authorizationRequest.getScope();

                try {

                    ClientDetails client = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());

                    for ( String scope : requestedScopes ) {

                        if ( client.isAutoApprove(scope) || client.isAutoApprove("all") ) {

                            approved = true;
                            break;
                        }
                    }
                }
                catch (ClientRegistrationException e) {
                    System.out.println(e);
                }
            }
        }

        authorizationRequest.setApproved(approved);

        return authorizationRequest;
    }
}
