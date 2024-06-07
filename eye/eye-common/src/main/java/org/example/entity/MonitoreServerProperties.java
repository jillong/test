package org.example.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitoreServerProperties {

    private String serverUrl;

    private int connectTimeout;

    private int socketTimeout;

    private int connectionRequestTimeout;
}
