package com.raidstack.restmanager.dtos.exceptions;

import java.util.List;

public record ValidationErrorDTO(List<String> errors, int status) {}
