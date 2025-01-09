package opsnow.framework.core.exception;

import java.util.Map;

/**
 * 1. Create Date: 2025-01-04
 * 2. Creator: hyunmin.park@opsnow.com
 * 3. Description:
 * ExceptionBuilder which is helper class for building an Exception.
 * /// throw new ExceptionBuilder(ex)
 * ///     .SetContext("db", db)
 * ///     .SetContext("cmd", cmd)
 * ///     .Build();
 */
public class ExceptionBuilder {
    private ErrorException exception;

    public ExceptionBuilder() {
        this.exception = new ErrorException();
    }

    public ExceptionBuilder(String msg) {
        this.exception = new ErrorException(msg);
    }

    public ExceptionBuilder(Exception ex) {
        this.exception = new ErrorException(ex);
    }

    public ExceptionBuilder setSummary(String summary) {
        this.exception.addDetail("SummaryMessage", summary);
        return this;
    }

    public ExceptionBuilder setDetail(String detail) {
        this.exception.addDetail("DetailMessage", detail);
        return this;
    }

    public ExceptionBuilder setTitle(String title) {
        this.exception.addDetail("Title", title);
        return this;
    }

    public ExceptionBuilder setData(String key, Object value) {
        this.exception.addDetail(key, value);
        return this;
    }

    public ExceptionBuilder setContext(String itemName, Object value) {
        this.exception.addDetail(itemName, value);
        return this;
    }

    public ExceptionBuilder addMessage(String title, Map<String, Object> datas) {
        StringBuilder msgBuilder = new StringBuilder();
        datas.forEach((key, value) -> msgBuilder.append(key).append(": ").append(value).append("\n"));
        this.exception.addDetail(title, msgBuilder.toString());
        return this;
    }

    public ErrorException build() {
        return this.exception;
    }
}
