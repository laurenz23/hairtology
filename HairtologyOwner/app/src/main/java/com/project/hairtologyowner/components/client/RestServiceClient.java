package com.project.hairtologyowner.components.client;

import com.project.hairtologyowner.components.app.AppConfig;

public class RestServiceClient extends WebServiceClient<RestApiClient> {

    private static final RestServiceClient instance = new RestServiceClient(AppConfig.Api.BASE_URL, RestApiClient.class);

    public static RestServiceClient getInstance() {
        return instance;
    }

    private RestServiceClient(String baseUrl, Class<RestApiClient> clientClass) {
        super(baseUrl, clientClass);
    }

}
