package com.inbank.back;
import org.springframework.stereotype.Service;

@Service
public class Decision {
    private static final int MIN_AMOUNT = 2000;
    private static final int MAX_AMOUNT = 10000;
    private static final int MIN_PERIOD = 12;
    private static final int MAX_PERIOD = 60;


    public Decision() {}

    /**
    * Get the modifier associated with the personal code 
    * 
    * @param personalCode the personal code of the user
    * @return the modifier: -1 if the user is invalid, 0 if the user has a debt, a positive integer otherwise
    */
    public int getModifier(String personalCode) {
        // The modfiers are mocked for the assignement
        return switch (personalCode) {
            case "49002010965" -> -1; // debt
            case "49002010976" -> 100;
            case "49002010987" -> 300;
            case "49002010998" -> 1000;
            default -> 0; // unknown
        };
    }

    /**
    * Returns the decision on wether to approve the loan and the amount 
    * 
    * @return true if the loan is approved, false otherwise
    * @return the amount of the loan if approved, a suggested amount otherwise or 0 if no alternative found
    * @return the period of the loan if approved, a suggested period otherwise or the requested period if no alternative found
    * @return a message explaining the decision
    */
    public DecisionResponse scoreCalculation(String personalCode, int loanAmount, int loanPeriod) {
        int suggestedPeriod = loanPeriod;        
        int creditModifier = getModifier(personalCode);

        if (creditModifier == -1) {
            return new DecisionResponse(false, 0, suggestedPeriod, "Invalid user");
        }

        if (creditModifier == 0) {
            return new DecisionResponse(false, 0, suggestedPeriod, "Debt found");
        }

        double maxAmountForRequestedPeriod = calculateMaxAmount(creditModifier, suggestedPeriod);
        double score = maxAmountForRequestedPeriod / (double) loanAmount;

        if (score >= 1) {
            // We approve the loan, but we need to check if the amount is within the limits
            int finalAmount = Math.min((int) maxAmountForRequestedPeriod, MAX_AMOUNT);
            return new DecisionResponse(true, finalAmount, suggestedPeriod, "Approved");
        }

        // Case where the score is less than 1

        if (maxAmountForRequestedPeriod < MIN_AMOUNT) {
            maxAmountForRequestedPeriod = 0; // min output sum is 2000
            for (int i = MIN_PERIOD; i <= MAX_PERIOD; i++) { // we check for loan periods from 12 months to 60 months
                int tempAmount = creditModifier * i;
                if (tempAmount >= MIN_AMOUNT) {
                    maxAmountForRequestedPeriod = tempAmount;
                    suggestedPeriod = i;
                    return new DecisionResponse(false,  (int) maxAmountForRequestedPeriod, suggestedPeriod, "Alternative found with period returned");
                }
            }
            return new DecisionResponse(false, 0, suggestedPeriod, "No alternative found"); // no alternative found
        }

        if (maxAmountForRequestedPeriod > MAX_AMOUNT) {
            maxAmountForRequestedPeriod = MAX_AMOUNT; // max output sum is 10000
        }
        return new DecisionResponse(false,  (int) maxAmountForRequestedPeriod, suggestedPeriod, "New amount proposed with period given");
    }

    /**
     * Calculate the maximum amount that can be approved for a given modifier and period
     * 
     * @param modifier
     * @param period
     * @return the maximum amount that can be approved
     */
    private double calculateMaxAmount(double modifier, double period) {
        return modifier * period;
    }
}