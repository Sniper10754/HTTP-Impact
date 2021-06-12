package org.impact.route;


import org.impact.http.HTTPPage;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class Route {
    private final HTTPPage page;
    private ArrayList<byte[]> correspondentArr;
    private final ArrayList<byte[]> originalCorrespondentArr;
    private String requestedURL;
    private final Thread updateThread = new Thread(this::updateFunction);
    private Runnable updateAction;

    public void updateFunction() {
        if (getUpdateAction() == null)
            this.getUpdateThread().interrupt();
        else {
            while (!this.getUpdateThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                    getUpdateAction().run();
                } catch (Exception e) {
                    System.err.println("In UpdateThread action an error raised: \n" + e);
                    this.getUpdateThread().interrupt();
                }
            }
        }
    }

    public Route(String requestedURL, HTTPPage page) {
        this(requestedURL, page, page.getContentType(), null);
    }

    public Route(String requestedURL, HTTPPage page, String contentType, Runnable target) {
        this.page = page;
        InputMismatchException ex = new InputMismatchException("Route doesn't start with \"/\"");
        this.setUpdateAction(target);
        this.setRequestedURL(requestedURL);
        if (! requestedURL.startsWith("/")) {
            throw ex;
        }

        if (! (contentType.trim().isEmpty())) {
            this.setContentType(contentType);
            this.setCorrespondentArr(page.getCorrespondentContent());
            originalCorrespondentArr = page.getCorrespondentContent();
        } else
            throw new IllegalArgumentException("Route content type can't be empty/blank.");
        this.getUpdateThread().start();
    }

    public void resetCorrespondentArr() {
        this.setCorrespondentArr(this.getOriginalCorrespondentArr());
    }

    @Deprecated
    public void stopUpdateThread() {
        this.getUpdateThread().interrupt();
    }

    public void reset() {
        resetCorrespondentArr();
    }

    public String getContentType() {
        return getPage().getContentType();
    }

    public String getRequestedURL() {
        return requestedURL;
    }

    public void setContentType(String contentType) {
        this.getPage().setContentType(contentType);
    }

    public void setRequestedURL(String requestedURL) {
        this.requestedURL = requestedURL;
    }

    public ArrayList<byte[]> getCorrespondentArr() {
        return getPage().getCorrespondentContent();
    }

    public void setCorrespondentArr(ArrayList<byte[]> correspondentArr) {
        this.getPage().setCorrespondentContent(correspondentArr);
    }

    public Runnable getUpdateAction() {
        return updateAction;
    }

    public void setUpdateAction(Runnable updateAction) {
        this.updateAction = updateAction;
    }

    public HTTPPage getPage() {
        return page;
    }

    public ArrayList<byte[]> getOriginalCorrespondentArr() {
        return originalCorrespondentArr;
    }

    public Thread getUpdateThread() {
        return updateThread;
    }
}
