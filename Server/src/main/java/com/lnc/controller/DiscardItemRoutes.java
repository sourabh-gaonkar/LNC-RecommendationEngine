package com.lnc.controller;

import com.lnc.service.discardItem.DiscardItemDeletion;
import com.lnc.service.discardItem.ImproviseItemFeedbackFetcher;
import com.lnc.service.discardItem.ImproviseQuestions;
import com.lnc.service.discardItem.ItemDiscard;
import com.lnc.service.employee.improviseItem.AnswerSubmission;
import com.lnc.service.employee.improviseItem.ImproviseItemList;
import com.lnc.service.employee.improviseItem.ImproviseItemQuestions;

public class DiscardItemRoutes implements RouteHandler {
    private final ItemDiscard itemDiscard = new ItemDiscard();
    private final DiscardItemDeletion discardItemDeletion = new DiscardItemDeletion();
    private final ImproviseQuestions improviseQuestions = new ImproviseQuestions();
    private final ImproviseItemList improviseItemList = new ImproviseItemList();
    private final ImproviseItemQuestions improviseItemQuestions = new ImproviseItemQuestions();
    private final AnswerSubmission answerSubmission = new AnswerSubmission();
    private final ImproviseItemFeedbackFetcher improviseItemFeedbackFetcher = new ImproviseItemFeedbackFetcher();

    @Override
    public String handle(String path, String data) throws Exception {
        return switch (path) {
            case "/getDiscardItems" -> itemDiscard.getDiscardItemList();
            case "/discardItem/deleteItem" -> discardItemDeletion.deleteDiscardedItem(data);
            case "/discardItem/addQuestions" -> improviseQuestions.addQuestions(data);
            case "/discardItem/viewImproviseItem" -> improviseItemList.getImproviseItemList();
            case "/discardItem/getQuestions" -> improviseItemQuestions.getQuestions(data);
            case "/discardItem/submitAnswers" -> answerSubmission.submitAnswer(data);
            case "/discardItem/getFeedback" -> improviseItemFeedbackFetcher.fetchFeedbacks(data);
            default -> throw new IllegalArgumentException("Invalid path for DiscardItemRoutes: " + path);
        };
    }
}
