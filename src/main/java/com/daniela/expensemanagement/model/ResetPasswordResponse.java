package com.daniela.expensemanagement.model;

import lombok.Builder;
import lombok.Data;

public record ResetPasswordResponse(Status status, String message) {
}
