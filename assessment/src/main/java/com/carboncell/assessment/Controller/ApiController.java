package com.carboncell.assessment.Controller;

import com.carboncell.assessment.Model.Entry;
import com.carboncell.assessment.Model.EntryResponse;
import com.carboncell.assessment.Service.ApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("Api")
@Tag(name = "Entries",description = "In this controller we have one api entries")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Operation(description = "This api will give the list of entries(public apis available) and uses filtering data based on the parameters passed like which catogery and how many results you want")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "401", description = "UnAuthorized access!!")
    @GetMapping("/entries")
    public List<Entry> getEntries(@RequestParam(required = false) String category, @RequestParam(required = false, defaultValue = "10") int limit) {
        EntryResponse response = apiService.fetchEntries();
        List<Entry> entries = response.getEntries();
        boolean checkEntriesEmpty= false;
        // Apply filtering based on category
        if (category != null && !category.isEmpty()) {

            entries = entries.stream()
                    .filter(entry -> entry.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
            if(entries.isEmpty()){
                checkEntriesEmpty=true;
               entries.add(new Entry(null,null,null,null,null,null,"category not found"));
            }
        }

        // Apply limit
        if (!checkEntriesEmpty && limit > 0 && limit <= entries.size()) {
            return entries.subList(0,limit);
        } else {
            return entries; // Return all entries if limit is invalid or not provided
        }
    }
}