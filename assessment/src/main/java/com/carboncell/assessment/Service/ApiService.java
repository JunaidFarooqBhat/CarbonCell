package com.carboncell.assessment.Service;

import com.carboncell.assessment.Model.Entry;
import com.carboncell.assessment.Model.EntryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {
    private final String API_URL = "https://api.publicapis.org/entries";
    @Autowired
public RestTemplate restTemplate;
    public EntryResponse fetchEntries() {

        return restTemplate.getForEntity(API_URL, EntryResponse.class).getBody();
    }
}