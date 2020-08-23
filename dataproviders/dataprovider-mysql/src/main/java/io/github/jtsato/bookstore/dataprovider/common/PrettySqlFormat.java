package io.github.jtsato.bookstore.dataprovider.common;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.jdbc.internal.FormatStyle;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

/**
 * @author Jorge Takeshi Sato
 */

public class PrettySqlFormat implements MessageFormattingStrategy {

    @Override
    public String formatMessage(final int connectionId,
                                final String now,
                                final long elapsed,
                                final String category,
                                final String prepared,
                                final String sql,
                                final String url) {
        return String.format("%s|%sms|%s|connection %s|%s", now, elapsed, category, connectionId, formatSql(category, sql));
    }

    private String formatSql(final String category, final String sql) {

        if (StringUtils.isBlank(sql)) {
            return StringUtils.EMPTY;
        }

        if (!Category.STATEMENT.getName().equals(category)) {
            return sql;
        }

        final String rawSQL = StringUtils.stripToEmpty(sql).toLowerCase(Locale.ROOT);
        final boolean shouldUseDDL = StringUtils.startsWithAny(rawSQL, "create", "alter", "comment");
        final String formated = shouldUseDDL ? FormatStyle.DDL.getFormatter().format(sql) : FormatStyle.BASIC.getFormatter().format(sql);
        return "\nPrettySqlFormat(P6Spy SQL,Hibernate format):" + formated;
    }
}
