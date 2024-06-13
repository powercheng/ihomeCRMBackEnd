package com.ihomeCabinet.crm.tools;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtSubject {
    String username;
    Integer region;
    String email;

    public JwtSubject() {
    }

    public JwtSubject(String username, Integer region, String email) {
        this.username = username;
        this.region = region;
        this.email = email;
    }
}
