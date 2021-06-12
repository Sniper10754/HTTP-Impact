package org.impact.http;

import java.util.ArrayList;

public class HTTPImage extends HTTPPage {
    public HTTPImage(ArrayList<byte[]> correspondentContent, String contentType) {
        super(correspondentContent, contentType);
    }

    @Override
    public String getContentType() {
        return super.getContentType();
    }

    @Override
    public void setContentType(String contentType) {
        super.setContentType(contentType);
    }

    @Override
    public ArrayList<byte[]> getCorrespondentContent() {
        return super.getCorrespondentContent();
    }

    @Override
    public void setCorrespondentContent(ArrayList<byte[]> correspondentContent) {
        super.setCorrespondentContent(correspondentContent);
    }
}
