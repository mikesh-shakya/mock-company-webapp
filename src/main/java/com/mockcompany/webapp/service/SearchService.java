package com.mockcompany.webapp.service;

import com.mockcompany.webapp.data.ProductItemRepository;
import com.mockcompany.webapp.model.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private final ProductItemRepository productItemRepository;

    public SearchService(ProductItemRepository productItemRepository) {
        this.productItemRepository = productItemRepository;
    }

    public Collection<ProductItem> search(String query){
        Iterable<ProductItem> allItems = this.productItemRepository.findAll();
        List<ProductItem> itemList = new ArrayList<>();

        query = query.replaceAll("\"","").trim().toLowerCase();

        for(ProductItem item: allItems){
            if(item.getName().equalsIgnoreCase(query) || item.getDescription().equalsIgnoreCase(query)){
                itemList.add(item);
            } else if(item.getName().toLowerCase().contains(query) || item.getDescription().toLowerCase().contains(query)) {
                itemList.add(item);
            }
        }
        return itemList;
    }
}
