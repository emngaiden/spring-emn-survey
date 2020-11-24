package com.emnsoft.emnsurvey.utils;

import java.util.Optional;
import org.springframework.http.ResponseEntity;

public class ResponseEntityUtils {
    public static <T extends Object> ResponseEntity<T> wrapOrNotFound(Optional<T> optional) {
        if(optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
