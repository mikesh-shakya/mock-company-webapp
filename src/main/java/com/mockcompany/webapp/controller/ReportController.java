package com.mockcompany.webapp.controller;

import com.mockcompany.webapp.api.SearchReportResponse;
import com.mockcompany.webapp.model.ProductItem;
import com.mockcompany.webapp.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Management decided it is super important that we have lots of products that match the following terms.
 * So much so, that they would like a daily report of the number of products for each term along with the total
 * product count.
 *
 * TODO: Refactor this class by rewritting the runReport method to use the SearchService
 */
@RestController
public class ReportController {

    /**
     * The people that wrote this code didn't know about JPA Spring Repository interfaces!
     */
    private final EntityManager entityManager;
    private final SearchService searchService;

    /**
     * TODO: Declare the SearchService similar to EntityManager and add as a constructor argument
     */

    @Autowired
    public ReportController(EntityManager entityManager, SearchService searchService) {
        this.entityManager = entityManager;
        this.searchService = searchService;
    }


    @GetMapping("/api/products/report")
    public SearchReportResponse runReport() {
        /** TODO: This method needs to be rewritten to use the SearchService */

        Map<String, Integer> hits = new HashMap<>();
        SearchReportResponse response = new SearchReportResponse();
        response.setSearchTermHits(hits);

        int count = this.entityManager.createQuery("SELECT item FROM ProductItem item").getResultList().size();
        response.setProductCount(count);

        Collection<ProductItem> coolitems = searchService.search("Cool");
        Collection<ProductItem> kidsitems = searchService.search("Kids");
        Collection<ProductItem> perfectitems = searchService.search("Perfect");
        Collection<ProductItem> amazingitems = searchService.search("Amazing");

        response.getSearchTermHits().put("Cool", coolitems.size());
        response.getSearchTermHits().put("Kids", kidsitems.size());
        response.getSearchTermHits().put("Amazing", amazingitems.size());
        response.getSearchTermHits().put("Perfect", perfectitems.size());

        return response;
    }
}
