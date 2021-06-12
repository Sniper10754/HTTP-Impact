package org.impact.http;

import java.util.ArrayList;

public class HTTPPage {
    private String contentType;
    private ArrayList<byte[]> correspondentContent;

    public HTTPPage(ArrayList<byte[]> correspondentContent, String contentType) {
        this.setCorrespondentContent(correspondentContent);
        this.setContentType(contentType);
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public ArrayList<byte[]> getCorrespondentContent() {
        return correspondentContent;
    }

    public void setCorrespondentContent(ArrayList<byte[]> correspondentContent) {
        this.correspondentContent = correspondentContent;
    }
}
