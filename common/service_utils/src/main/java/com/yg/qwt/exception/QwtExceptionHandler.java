package com.yg.qwt.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QwtExceptionHandler extends RuntimeException{
    private Integer code;
    private String message;
}
