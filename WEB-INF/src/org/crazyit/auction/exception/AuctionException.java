package org.crazyit.auction.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;

public class AuctionException extends Exception {
    private static final long serialVersionUID = 1L;

    public AuctionException(){}

    public AuctionException(String message) {
        super(message);
    }
}
