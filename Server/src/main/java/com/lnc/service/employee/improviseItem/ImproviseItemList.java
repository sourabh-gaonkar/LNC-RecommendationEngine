package com.lnc.service.employee.improviseItem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lnc.DB.ImproviseMenuQueries;
import com.lnc.utils.ConversionToJson;

import java.util.List;
import java.util.logging.Logger;

public class ImproviseItemList {
    private final Logger logger = Logger.getLogger(ImproviseItemList.class.getName());
    private final ImproviseMenuQueries improviseMenuQueries = new ImproviseMenuQueries();
    private final ConversionToJson conversionToJson = new ConversionToJson();

    public String getImproviseItemList(){
        try{
            List<String> improviseItems = improviseMenuQueries.getAllImproviseList();
            return conversionToJson.codeItemList(improviseItems);
        } catch (JsonProcessingException | NullPointerException ex){
            logger.severe("Failed to get improvise items.\n" + ex.getMessage());
            return "Failed to get improvise items.";
        }
    }
}
