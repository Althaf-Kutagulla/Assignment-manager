package org.althaf.assignmentmanager.records;

import java.util.List;

public record LoginResponse(
        String userId,
        List<String> roles,
        String token
) {
}
