package com.demo.pocnema.repository;

public interface ResourceNotFoundExceptionRepository {
    String getResourceName();

    String getFieldName();

    Object getFieldValue();
}
