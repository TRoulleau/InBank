**API Responses**

The requests to the API should follow this structure:
```json
{
  "personalCode": "49002010976",
  "loanAmount": 5000,
  "loanPeriod": 24
}
```


The API will return JSON responses with the following structure:
```json
{
  "approved": true,
  "amount": 5000,
  "period": 24,
  "reason": "Approved."
}
```

There are multiple possible responses:
- **Approved**: The loan is approved with the requested amount and period.
- **New amount proposed with period given"**: If the requested amount is not available for the selected period, it automatically searches for an alternative amount (2000-10000 euros) for the period that is the maximum that the bank can offer.
- **Alternative found with period returned**: If the requested amount is not available, it automatically searches for an alternative amount (2000-10000 euros, but closer to 2000 as it looks for alternatives >= 2000) and returns the period for which this amount is available.
- **No alternative found**: If the requested amount is not available and no alternative amount or period is found, it returns a response indicating that the loan cannot be approved.
- **Debt found**: If the user has debt, it returns a response indicating that the loan cannot be approved.