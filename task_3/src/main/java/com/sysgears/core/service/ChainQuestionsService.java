package com.sysgears.core.service;

import com.sysgears.core.exception.QuestionException;
import com.sysgears.core.exception.ServiceException;
import com.sysgears.core.model.Options;
import com.sysgears.core.model.Question;
import com.sysgears.core.util.ServiceUtil;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CommonsLog
public class ChainQuestionsService {

    public List<List<Map<String, String>>> findChains(List<Question> questions) {
        log.info("Starting to find chains for the given questions.");

        validateQuestions(questions);

        List<List<Map<String, String>>> allChains = new ArrayList<>();

        Question sourceQuestion = getSourceQuestion(questions);
        String sourceQuestionText = sourceQuestion.getQuestion();
        log.info("Source question identified: " + sourceQuestionText);

        log.debug("Starting recursive chain generation...");
        findChainsRecursively(sourceQuestionText, questions, new ArrayList<>(), allChains);

        log.info("Completed chain generation. Total chains created: " + allChains.size());
        return allChains;
    }

    private void findChainsRecursively(String currentQuestion,
                                       List<Question> questions,
                                       List<Map<String, String>> currentChain,
                                       List<List<Map<String, String>>> allChains) {

        log.debug("Processing question: " + currentQuestion);
        Question questionEntry = questions.stream()
                .filter(q -> q.getQuestion().equals(currentQuestion))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Could not find question with text: " + currentQuestion);
                    return new QuestionException("Question with text '" + currentQuestion + "' not found.");
                });
        log.debug("Question entry found: " + questionEntry);
        List<Options> options = questionEntry.getOptions();


        options.forEach(option -> {
            Map<String, String> step = new HashMap<>();
            step.put(currentQuestion, option.getAnswer());

            List<Map<String, String>> newChain = new ArrayList<>(currentChain);
            newChain.add(step);

            if (option.getNextQuestion() != null) {
                String nextQuestion = option.getNextQuestion();
                log.debug("Moving to next question: " + nextQuestion);
                findChainsRecursively(nextQuestion, questions, newChain, allChains);
            } else {
                log.debug("Option does not have a next question. Completing chain.");
                if (ServiceUtil.isContainsQuestion(allChains, currentQuestion)) {
                    log.debug("Question already exists in chains. Merging answers.");
                    ServiceUtil.updateAndMergeOptions(allChains, currentQuestion, option.getAnswer());
                } else {
                    log.debug("Adding new chain to the list of all chains.");
                    allChains.add(newChain);
                }
            }
        });
    }

    private Question getSourceQuestion(List<Question> questions) {
        log.debug("Searching for source question...");
        return questions.stream()
                .filter(Question::getSource)
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Source question not found!");
                    return new QuestionException("Source question not found!");
                });
    }

    private void validateQuestions(List<Question> questions) {
        if (CollectionUtils.isEmpty(questions)) {
            log.error("Provided question list is null or empty.");
            throw new ServiceException("The list of questions cannot be null or empty.");
        }
    }
}
