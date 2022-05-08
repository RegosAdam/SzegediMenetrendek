package com.example.szegedi_menetrendek.model;

public class PublicTransport {

    private String number;
    private final int imageResource;

    public PublicTransport(String number, int imageResource) {
        this.number = number;
        this.imageResource = imageResource;
    }

    public String getNumber() {
        return number;
    }

    public int getImageResource() {
        return imageResource;
    }
}
