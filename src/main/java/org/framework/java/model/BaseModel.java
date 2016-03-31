package org.framework.java.model;

import java.io.Serializable;

public class BaseModel implements Serializable {

    private static final long serialVersionUID = -1200544816545968938L;

    protected long id;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

}
