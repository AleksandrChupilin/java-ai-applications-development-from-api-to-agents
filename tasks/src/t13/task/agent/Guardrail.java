package t13.task.agent;

import commons.exceptions.TaskNotImplementedException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Guardrail {

    private static final String REDACTED = "***";

    //TODO:
    // - Define PATTERNS as a Pattern array containing compiled regex patterns to redact PII:
    //   - Credit card num — Python-dict format: 'num': 'xxxx-xxxx-xxxx-xxxx'
    //   - Credit card num — JSON format: "num": "xxxx-xxxx-xxxx-xxxx"
    //   - Credit card num — standalone: 16 digits in 4 groups separated by '-' or space
    //   - CVV — Python-dict format: 'cvv': 'ddd'
    //   - CVV — JSON format: "cvv": "ddd"
    //   - Expiry date — Python-dict format: 'exp_date': 'MM/YYYY'
    //   - Expiry date — JSON format: "exp_date": "MM/YYYY"
    //   - Salary — YAML/plain format: salary: 12345
    //   - Salary — JSON format: "salary": 12345
    //   - Salary — Python-dict format: 'salary': 12345
    private static final Pattern[] PATTERNS = {};

    public String redact(String text) {
        //TODO:
        // - If text is null, return null
        // - Apply each Pattern in PATTERNS via replaceAll() substituting matches with REDACTED
        // - Return the fully redacted text
        throw new TaskNotImplementedException();
    }
}
