package com.example.demo.model.req;

import java.io.Serializable;

/**
 * Push請求物件
 */
public class PushReqModelBean implements Serializable {
	//private static final Logger log = LoggerFactory.getLogger(PushReqModelBean.class);
	private static final long serialVersionUID = 1L;
	private String text;
	private String packageId;
    private String stickerId;
    /**
     * @return the text
     */
    public String getText() {
        return text;
    }
    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    /**
     * @return the packageId
     */
    public String getPackageId() {
        return packageId;
    }
    /**
     * @param packageId the packageId to set
     */
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
    /**
     * @return the stickerId
     */
    public String getStickerId() {
        return stickerId;
    }
    /**
     * @param stickerId the stickerId to set
     */
    public void setStickerId(String stickerId) {
        this.stickerId = stickerId;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PushReqModelBean [text=" + text + ", packageId=" + packageId + ", stickerId=" + stickerId + "]";
    }



}
