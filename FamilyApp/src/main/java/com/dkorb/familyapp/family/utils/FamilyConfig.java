package com.dkorb.familyapp.family.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "family-member.app")
public class FamilyConfig {

    private String hostName;

    private int port;

}