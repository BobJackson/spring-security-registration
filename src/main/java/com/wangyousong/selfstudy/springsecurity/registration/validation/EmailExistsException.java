package com.wangyousong.selfstudy.springsecurity.registration.validation;

/**
 * @author Administrator
 */
@SuppressWarnings("serial")
public class EmailExistsException extends Throwable {

    public EmailExistsException(final String message) {
        super(message);
    }

}
